package snipsnok.donation.content;

import org.springframework.stereotype.Service;
import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.content.exception.ContentNotFoundException;
import snipsnok.donation.exception.InvalidDonationException;
import snipsnok.user.User;
import snipsnok.user.UserNotCreatorException;
import snipsnok.user.UserNotFoundException;
import snipsnok.user.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DonationContentServiceImpl implements DonationContentService {
    private DonationContentRepository donationContentRepository;
    private UserRepository userRepository;
    private ContentRepository contentRepository;

    public DonationContentServiceImpl(
            DonationContentRepository donationContentRepository,
            UserRepository userRepository,
            ContentRepository contentRepository) {
        this.donationContentRepository = donationContentRepository;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    @Transactional
    public DonationContent addDonation(User user, int contentId, double amount) {
        Optional<Content> tempContent = contentRepository.findById(contentId);
        if (tempContent.isEmpty()) {
            throw new ContentNotFoundException(contentId);
        }
        Content content = tempContent.get();
        if (!content.getCreator().isCreator()) {
            throw new UserNotCreatorException(content.getCreator().getUsername());
        }

        if (amount < 0) {
            throw new InvalidDonationException(amount);
        }

        DonationContent donationContent = new DonationContent();
        donationContent.setContent(content);
        donationContent.setDonator(user);
        donationContent.setAmount(amount);
        return donationContentRepository.save(donationContent);
    }

    @Override
    public double getTotalContentDonationsForUser(int creatorId) {
        Optional<User> tempCreator = userRepository.findById(creatorId);
        if (tempCreator.isEmpty()) {
            throw new UserNotFoundException(creatorId);
        }

        double total = 0;
        List<DonationContent> donationContents = donationContentRepository.findDonationsToCreatorId(creatorId);
        if (donationContents != null) {
            for (DonationContent donationContent : donationContents) {
                total += donationContent.getAmount();
            }
        }
        return total;
    }
}
