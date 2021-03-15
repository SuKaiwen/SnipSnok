package snipsnok.donation.user.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snipsnok.donation.exception.InvalidDonationException;
import snipsnok.donation.user.DonationUser;
import snipsnok.donation.user.DonationUserRepository;
import snipsnok.donation.user.DonationUserServiceImpl;
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
public class DonationUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DonationUserRepository donationUserRepository;
    @InjectMocks
    private DonationUserServiceImpl donationUserServiceImpl;

    /**
     * Test if service is able to add a donation correctly into the repository
     */
    @Test
    public void testAddDonationUser() {
        User user = mock(User.class);
        when(userRepository.findById(eq(0))).thenReturn(Optional.of(user));

        User creator = mock(User.class);
        when(creator.isCreator()).thenReturn(false);
        when(userRepository.findById(eq(1))).thenReturn(Optional.empty());

        // Verify if there is a check to see if the given creatorId is a valid user
        assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> {
            donationUserServiceImpl.addDonation(user, 1, 50);
        });

        when(userRepository.findById(eq(1))).thenReturn(Optional.of(creator));

        // Verify if there is a check to see if the given creatorId is a valid creator
        assertThatExceptionOfType(UserNotCreatorException.class).isThrownBy(() -> {
            donationUserServiceImpl.addDonation(user, 1, 50);
        });

        when(creator.isCreator()).thenReturn(true);

        // Verify if there is a check to see if negative amounts are rejected
        assertThatExceptionOfType(InvalidDonationException.class).isThrownBy(() -> {
            donationUserServiceImpl.addDonation(user, 1, -50);
        });

        DonationUser expected = new DonationUser();
        when(donationUserRepository.save(any())).thenReturn(expected);

        // Verify that a donation is made if all checks pass
        assertThat(donationUserServiceImpl.addDonation(user, 1, 50)).isSameAs(expected);
    }

    /**
     * Test if service is able to retrieve the total amount of direct donations to a creator correctly from the
     * repository
     */
    @Test
    public void testGetTotalDonationsForUser() {
        when(userRepository.findById(eq(10))).thenReturn(Optional.empty());

        // Verify if there is a check to see if the given userId is a valid user
        assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> {
            donationUserServiceImpl.getTotalDonationsForUser(10);
        });

        when(userRepository.findById(eq(10))).thenReturn(Optional.of(mock(User.class)));

        DonationUser donation1 = mock(DonationUser.class);
        DonationUser donation2 = mock(DonationUser.class);
        when(donation1.getAmount()).thenReturn(50.0);
        when(donation2.getAmount()).thenReturn(100.0);

        when(donationUserRepository.findByReceiverId(eq(10)))
                .thenReturn(null).thenReturn(List.of(donation1, donation2));

        // Verify if the correct amounts are returned if the user is valid
        double amount = donationUserServiceImpl.getTotalDonationsForUser(10);
        assertThat(amount).isEqualTo(0);
        amount = donationUserServiceImpl.getTotalDonationsForUser(10);
        assertThat(amount).isEqualTo(150);
    }
}
