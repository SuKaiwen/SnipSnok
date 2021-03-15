package snipsnok.user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import snipsnok.user.FollowController;
import snipsnok.user.FollowRepository;
import snipsnok.user.FollowService;
import snipsnok.user.UserController;
import snipsnok.user.UserRepository;
import org.springframework.test.web.servlet.MvcResult;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import snipsnok.security.CurrentUser;

import java.util.Optional;

@WithMockUser
@WebMvcTest(FollowController.class)
public class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationUserDetailsService ads;

    @MockBean
    private FollowService followService;
    @MockBean
    private CurrentUser currentUser;
    @MockBean
    private User user;

    @BeforeEach
    public void setup(){
        user = new User();
        when(currentUser.getUser()).thenReturn(user);
    }
    /**
     * Testing for HTTP 200 OK when adding new donation to creator
     *
     * @throws Exception
     */
    @Test
    public void testAddNewFollower() throws Exception {
        Follow expected = new Follow();
        when(followService.createFollow(eq(user), anyString())).thenReturn(expected);
        mockMvc.perform(post("/api/follow/something"))
                .andExpect(status().isOk());
    }


    /**
     * Testing for HTTP 200 OK when getting amount of all direct donations to a user
     *
     * @throws Exception
     */
    @Test
    public void testDeleteExistingFollower() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/follow/someusername")
        ).andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testGetFollow() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/follow/someusername/")).andExpect(status().isOk())
                .andReturn();
    }

    /**
     * Testing for HTTP 200 OK when getting amount of all direct donations to a user
     *
     * @throws Exception
     */
    @Test
    public void testAccountsUserIsFollowing() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/someusername/following")).andExpect(status().isOk())
                .andReturn();

    }

    /**
     * Testing for HTTP 200 OK when getting amount of all direct donations to a user
     *
     * @throws Exception
     */
    @Test
    public void testNumberAccountsUserIsFollowing() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/someusername/following/total")).andExpect(status().isOk())
                .andReturn();

    }

    /**
     * Testing for HTTP 200 OK when getting amount of all direct donations to a user
     *
     * @throws Exception
     */
    @Test
    public void testAccountsFollowingUser() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/someusername/followers")).andExpect(status().isOk())
                .andReturn();

    }

    /**
     * Testing for HTTP 200 OK when getting amount of all direct donations to a user
     *
     * @throws Exception
     */
    @Test
    public void testNumberAccountsFollowingUser() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/someusername/followers/total")).andExpect(status().isOk())
                .andReturn();

    }
}
