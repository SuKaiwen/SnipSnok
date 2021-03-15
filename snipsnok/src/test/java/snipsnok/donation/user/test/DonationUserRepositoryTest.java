package snipsnok.donation.user.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import snipsnok.donation.user.DonationUser;
import snipsnok.donation.user.DonationUserRepository;
import snipsnok.user.User;
import snipsnok.user.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class DonationUserRepositoryTest {

    @Autowired
    private DonationUserRepository donationUserRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Testing to see if dependency injection works
     */
    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(donationUserRepository).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    private User createUser(boolean creator)  {
        User user = new User();
        user.setCreator(creator);
        return userRepository.save(user);
    }

    private DonationUser donate(User donator, int receiverId, double amount) {
        DonationUser donationUser = new DonationUser();
        donationUser.setDonator(donator);
        donationUser.setReceiverId(receiverId);
        donationUser.setAmount(amount);
        return donationUserRepository.save(donationUser);
    }

    /**
     * Test repository auto implemented findByReceiverId(int receiverId)
     *
     * @throws Exception
     * @given a user "userId"
     * @when repository called to try to find direct donations to a creator with creator as "userId"
     * @then check if correct total
     */
    @Test
    public void testFindByReceiverId() {
        User user = createUser(false);
        User creator1 = createUser(true);
        User creator2 = createUser(true);

        donate(user, creator1.getId(), 50);
        donate(user, creator2.getId(), 70);

        List<DonationUser> donations = donationUserRepository.findByReceiverId(creator1.getId());

        assertThat(donations.size()).isEqualTo(1);
        assertThat(donations.get(0).getAmount()).isEqualTo(50);

        donate(user, creator2.getId(), 100);

        donations = donationUserRepository.findByReceiverId(creator2.getId());

        assertThat(donations.size()).isEqualTo(2);
    }
}
