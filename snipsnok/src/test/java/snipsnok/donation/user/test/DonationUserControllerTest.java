package snipsnok.donation.user.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import snipsnok.donation.user.DonationUser;
import snipsnok.donation.user.DonationUserController;
import snipsnok.donation.user.DonationUserService;
import snipsnok.security.CurrentUser;
import snipsnok.user.ApplicationUserDetailsService;
import snipsnok.user.User;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(DonationUserController.class)
public class DonationUserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DonationUserService donationUserService;
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
     * Testing for HTTP 200 OK when donating directly to a creators content
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

        DonationUser donationUser = new DonationUser();
        when(donationUserService.addDonation(eq(user), anyInt(), anyDouble())).thenReturn(donationUser);
        mockMvc.perform(post("/api/donate/creator/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    /**
     * Testing for HTTP 200 OK when getting amount of all donations to a user's content
     *
     * @throws Exception
     */
    @Test
    public void testGetTotalDonations() throws Exception {
        mockMvc.perform(get("/api/donate/creator/totals/10")).andExpect(status().isOk());
    }
}
