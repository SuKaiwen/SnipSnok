package snipsnok.donation.content.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import snipsnok.donation.content.DonationContent;
import snipsnok.donation.content.DonationContentController;
import snipsnok.donation.content.DonationContentService;
import snipsnok.security.CurrentUser;
import snipsnok.user.ApplicationUserDetailsService;
import snipsnok.user.User;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(DonationContentController.class)
public class DonationContentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DonationContentService donationContentService;
    @MockBean
    private ApplicationUserDetailsService ads;
    @MockBean
    private CurrentUser currentUser;
    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        when(currentUser.getUser()).thenReturn(user);
    }

    /**
     * Testing for HTTP 200 OK when adding new donation to creator
     *
     * @throws Exception
     */
    @Test
    public void testDonateToCreator() throws Exception {
        Object randomObj = new Object() {
            public final double amount = 50.0;
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(randomObj);

        DonationContent donationContent = new DonationContent();
        when(donationContentService.addDonation(eq(user), anyInt(), anyDouble())).thenReturn(donationContent);
        mockMvc.perform(post("/api/donate/content/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    /**
     * Testing for HTTP 200 OK when getting amount of all direct donations to a user
     *
     * @throws Exception
     */
    @Test
    public void testGetTotalDonations() throws Exception {
        mockMvc.perform(get("/api/donate/content/totals/10")).andExpect(status().isOk());
    }
}
