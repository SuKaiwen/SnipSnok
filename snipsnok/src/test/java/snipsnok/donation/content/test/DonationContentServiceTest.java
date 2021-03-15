package snipsnok.donation.content.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.content.exception.ContentNotFoundException;
import snipsnok.donation.content.DonationContent;
import snipsnok.donation.content.DonationContentRepository;
import snipsnok.donation.content.DonationContentServiceImpl;
import snipsnok.donation.exception.InvalidDonationException;
import snipsnok.user.User;
import snipsnok.user.UserNotCreatorException;
import snipsnok.user.UserNotFoundException;
import snipsnok.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DonationContentServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ContentRepository contentRepository;
    @Mock
    private DonationContentRepository donationContentRepository;
    @InjectMocks
    private DonationContentServiceImpl donationContentServiceImpl;

    /**
     * Test if service is able to add a donation correctly into the repository
     */
    @Test
    public void testAddDonationContent() {
        User user = mock(User.class);

        User creator = mock(User.class);
        when(creator.isCreator()).thenReturn(false);

        Content content = mock(Content.class);
        when(content.getCreator()).thenReturn(creator);
        when(contentRepository.findById(eq(1))).thenReturn(Optional.empty());

        // Verify if there is a check to see if the given creatorId is a valid user
        assertThatExceptionOfType(ContentNotFoundException.class).isThrownBy(() -> {
            donationContentServiceImpl.addDonation(user, 1, 50);
        });

        when(contentRepository.findById(eq(1))).thenReturn(Optional.of(content));

        // Verify if there is a check to see if the given creatorId is a valid creator
        assertThatExceptionOfType(UserNotCreatorException.class).isThrownBy(() -> {
            donationContentServiceImpl.addDonation(user, 1, 50);
        });

        when(creator.isCreator()).thenReturn(true);

        // Verify if there is a check to see if negative amounts are rejected
        assertThatExceptionOfType(InvalidDonationException.class).isThrownBy(() -> {
            donationContentServiceImpl.addDonation(user, 1, -50);
        });

        DonationContent expected = new DonationContent();
        when(donationContentRepository.save(any())).thenReturn(expected);

        // Verify that a donation is made if all checks pass
        assertThat(donationContentServiceImpl.addDonation(user, 1, 50)).isSameAs(expected);
    }

    /**
     * Test if service is able to retrieve the total amount of donations to a creator's content correctly from the
     * repository
     */
    @Test
    public void testGetTotalContentDonationsForUser() {
        when(userRepository.findById(eq(10))).thenReturn(Optional.empty());

        // Verify if there is a check to see if the given userId is a valid user
        assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> {
            donationContentServiceImpl.getTotalContentDonationsForUser(10);
        });

        when(userRepository.findById(eq(10))).thenReturn(Optional.of(mock(User.class)));

        DonationContent donation1 = mock(DonationContent.class);
        DonationContent donation2 = mock(DonationContent.class);
        when(donation1.getAmount()).thenReturn(50.0);
        when(donation2.getAmount()).thenReturn(100.0);

        when(donationContentRepository.findDonationsToCreatorId(eq(10)))
                .thenReturn(null).thenReturn(List.of(donation1, donation2));

        // Verify if the correct amounts are returned if the user is valid
        double amount = donationContentServiceImpl.getTotalContentDonationsForUser(10);
        assertThat(amount).isEqualTo(0);
        amount = donationContentServiceImpl.getTotalContentDonationsForUser(10);
        assertThat(amount).isEqualTo(150);
    }
}
