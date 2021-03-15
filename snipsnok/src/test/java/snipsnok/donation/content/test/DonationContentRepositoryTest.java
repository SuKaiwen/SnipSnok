package snipsnok.donation.content.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.donation.content.DonationContent;
import snipsnok.donation.content.DonationContentRepository;
import snipsnok.user.User;
import snipsnok.user.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DonationContentRepositoryTest {

    @Autowired
    private DonationContentRepository donationContentRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Testing to see if dependency injection works
     */
    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(donationContentRepository).isNotNull();
        assertThat(contentRepository).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    private User createUser(boolean creator) {
        User user = new User();
        user.setCreator(creator);
        return userRepository.save(user);
    }

    private Content createContent(User creator) {
        Content content = new Content();
        content.setCreator(creator);
        return contentRepository.save(content);
    }

    private DonationContent donate(User donator, Content content, double amount) {
        DonationContent donationContent = new DonationContent();
        donationContent.setDonator(donator);
        donationContent.setContent(content);
        donationContent.setAmount(amount);
        return donationContentRepository.save(donationContent);
    }

    /**
     * Test repository implemented findDonationsToCreatorId(@Param("userId") int userId)
     *
     * @throws Exception
     * @given a user "userId"
     * @when repository called to try to find donation contents with creator as "userId"
     * @then check if correct total
     */
    @Test
    public void testFindDonationsToCreatorId() {
        User user = createUser(false);
        User creator1 = createUser(true);
        User creator2 = createUser(true);

        Content content1 = createContent(user);
        Content content2 = createContent(creator1);
        Content content3 = createContent(creator2);

        donate(user, content2, 50);
        donate(user, content3, 60);
        donate(user, content3, 70);

        List<DonationContent> donations = donationContentRepository.findDonationsToCreatorId(creator1.getId());
        assertThat(donations.size()).isEqualTo(1);
        assertThat(donations.get(0).getAmount()).isEqualTo(50);

        donations = donationContentRepository.findDonationsToCreatorId(creator2.getId());
        assertThat(donations.size()).isEqualTo(2);
    }
}
