package snipsnok.donation.content;

import snipsnok.user.User;

public interface DonationContentService {
    DonationContent addDonation(User user, int contentId, double amount);

    double getTotalContentDonationsForUser(int creatorId);
}
