package snipsnok.donation.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationUserRepository extends JpaRepository<DonationUser, Integer> {

    /**
     * DAO API for finding all donations to a user
     *
     * @param receiverId Creator to lookup
     * @return List donations to a creator
     */
    List<DonationUser> findByReceiverId(int receiverId);
}
