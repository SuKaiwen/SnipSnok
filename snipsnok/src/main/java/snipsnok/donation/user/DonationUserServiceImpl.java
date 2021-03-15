package snipsnok.donation.user;

import org.springframework.stereotype.Service;
import snipsnok.donation.exception.InvalidDonationException;
import snipsnok.user.User;
import snipsnok.user.UserNotCreatorException;
import snipsnok.user.UserNotFoundException;
import snipsnok.user.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DonationUserServiceImpl implements DonationUserService {

    private DonationUserRepository donationUserRepository;
    private UserRepository userRepository;

    public DonationUserServiceImpl(DonationUserRepository donationUserRepository, UserRepository userRepository) {
        this.donationUserRepository = donationUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public DonationUser addDonation(User user, int creatorId, double amount) {
        Optional<User> tempCreator = userRepository.findById(creatorId);
        if (tempCreator.isEmpty()) {
            throw new UserNotFoundException(creatorId);
        }
        User creator = tempCreator.get();
        if (!creator.isCreator()) {
            throw new UserNotCreatorException(creatorId);
        }

        if (amount < 0) {
            throw new InvalidDonationException(amount);
        }

        DonationUser donationUser = new DonationUser();
        donationUser.setAmount(amount);
        donationUser.setReceiverId(creatorId);
        donationUser.setDonator(user);
        return donationUserRepository.save(donationUser);
    }

    @Override
    public double getTotalDonationsForUser(int creatorId) {
        Optional<User> tempCreator = userRepository.findById(creatorId);
        if (tempCreator.isEmpty()) {
            throw new UserNotFoundException(creatorId);
        }

        double total = 0;
        List<DonationUser> donationUsers = donationUserRepository.findByReceiverId(creatorId);
        if (donationUsers != null) {
            for (DonationUser donationUser : donationUsers) {
                total += donationUser.getAmount();
            }
        }
        return total;
    }
}
