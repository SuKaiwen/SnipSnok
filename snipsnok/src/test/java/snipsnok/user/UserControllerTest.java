package snipsnok.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import snipsnok.security.CurrentUser;

@WithMockUser
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApplicationUserDetailsService ads;

    @MockBean
    private UserService userService;

    @MockBean
    private CurrentUser currentUser;

    private User u;

    @BeforeEach
    public void setup() {
        u = new User();
        u.setPassword("123456");
        when(currentUser.getUser()).thenReturn(u);
    }

    /**
     * Test whether the controller successfully adds a new user
     * Note: new users must have set a password
     * @throws Exception
     */
    @Test
    public void addUserTest() throws Exception {
        User expected = new User();
        expected.setPassword("123456");
        when(userService.addUser(expected)).thenReturn(expected);
        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isOk());
    }

    /**
     * Test whether the controller sucessfully finds all users
     * @throws Exception
     */
    @Test
    public void findAllUserTest() throws Exception{
        User expected = new User();
        expected.setPassword("123456");
        when(userService.addUser(expected)).thenReturn(expected);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    /**
     * Test whether the controller sucessfully finds all users
     * @throws Exception
     */
    @Test
    public void findUserByUsernameTest() throws Exception{
        User expected = new User();
        expected.setPassword("123456");
        expected.setUsername("Smith");
        when(userService.addUser(expected)).thenReturn(expected);
        mockMvc.perform(get("/api/users/{userId}", "Smith"))
                .andExpect(status().isOk());
    }

    /**
     * Test whether the controller edits the user sucessfully
     * @throws Exception
     */
    @Test
    public void editUserByUsernameTest() throws Exception{
        User newUser = new User();
        newUser.setFirstName("James");
        newUser.setLastName("Smithern");
        newUser.setEmail("Smith@gmail.com");

        when(userService.editUser(currentUser, newUser)).thenReturn(newUser);
        mockMvc.perform(post("/api/users/edit", newUser)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());
    }
}
