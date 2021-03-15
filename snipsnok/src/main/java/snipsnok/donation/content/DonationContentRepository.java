package snipsnok.donation.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationContentRepository extends JpaRepository<DonationContent, Integer> {

    /**
     * DAO API for finding all donations to a user content
     *
     * @param userId User to lookup
     * @return List donations to a creators content
     */
    @Query("SELECT dc FROM DonationContent dc JOIN Content c ON c.id = dc.content.id WHERE c.creator.id = :userId")
    List<DonationContent> findDonationsToCreatorId(@Param("userId") int userId);
}
