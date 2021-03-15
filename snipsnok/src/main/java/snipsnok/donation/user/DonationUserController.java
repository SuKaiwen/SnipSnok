package snipsnok.donation.user;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snipsnok.security.CurrentUser;

@Controller
@RequestMapping(path = "/api")
public class DonationUserController {
    @Autowired
    private DonationUserService donationUserService;

    @Autowired
    private CurrentUser currentUser;

    /**
     * Endpoint for donating to a creator
     * This controller will check if both the user and creators are valid members. Furthermore a check is also made to
     * see if the creator is a "creator"
     *
     * @param amount    Amount to donate
     * @param creatorId Creator to donate to
     * @return DonationUser saved
     */
    @PostMapping(path = "/donate/creator/{creatorId}")
    public @ResponseBody
    DonationUser donateToCreator(@RequestBody JSONObject amount, @PathVariable int creatorId) {
        return donationUserService.addDonation(currentUser.getUser(), creatorId, amount.getAsNumber("amount").doubleValue());
    }

    /**
     * Endpoint for retrieving total donations directly to a user.
     * This controller will check if the user is a valid member.
     *
     * @param creatorId User to look up
     * @return Total amount
     * donated to the user.
     */
    @GetMapping(path = "/donate/creator/totals/{creatorId}")
    public @ResponseBody
    double getTotalDonations(@PathVariable int creatorId) {
        return donationUserService.getTotalDonationsForUser(creatorId);
    }
}
