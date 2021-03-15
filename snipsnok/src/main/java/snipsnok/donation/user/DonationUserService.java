package snipsnok.donation.user;

import snipsnok.user.User;

public interface DonationUserService {
    DonationUser addDonation(User user, int creatorId, double amount);

    double getTotalDonationsForUser(int creatorId);
}
