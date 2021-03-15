package snipsnok.donation.content;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snipsnok.security.CurrentUser;

@Controller
@RequestMapping(path = "/api")
public class DonationContentController {
    @Autowired
    private DonationContentService donationContentService;

    @Autowired
    private CurrentUser currentUser;

    /**
     * Endpoint for donating to content posted by a creator
     * This controller will check if the user is a valid member. Furthermore a check is also made to see if the content
     * is available and was created by a creator.
     *
     * @param amount    Amount to donate
     * @param contentId Content produced by a creator to donate to
     * @return DonationContent saved
     */
    @PostMapping(path = "/donate/content/{contentId}")
    public @ResponseBody
    DonationContent donateToContent(@RequestBody JSONObject amount, @PathVariable int contentId) {
        return donationContentService.addDonation(currentUser.getUser(), contentId, amount.getAsNumber("amount").doubleValue());
    }

    /**
     * Endpoint for retrieving total donations to all of a users to content.
     * This controller will check if the user is a valid member.
     *
     * @param creatorId User to look up
     * @return Total amount
     * donated to the user.
     */
    @GetMapping(path = "/donate/content/totals/{creatorId}")
    public @ResponseBody
    double getTotalDonations(@PathVariable int creatorId) {
        return donationContentService.getTotalContentDonationsForUser(creatorId);
    }
}
