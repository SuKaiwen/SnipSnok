package snipsnok.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;

import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.content.ContentServiceImpl;
import snipsnok.content.exception.ContentNotFoundException;
import snipsnok.user.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    /**
	 * This test aims to cover if the service can correctly add a new user to the repository
	 * @given a user registers an account
	 * @when UserService tries to add the new account
	 * @then the added user should be equal to the one given
	 */
    @Test
    public void addUserTest(){
        // Given that
        User u = new User();
        u.setFirstName("John");
        u.setLastName("Smith");
        u.setUsername("JS");

        // When
        when(userRepository.save(u)).thenReturn(u);
        User x = userService.addUser(u);

        // Then
        assert(x.getFirstName()).equals(u.getFirstName());
    }

    /**
     * This test aims to cover if the service can correctly get the user details
     * given that the username is valid
     * @given a user views a profile
     * @when UserService tries to get the details of the user
     * @then the details of the user should be equal to the given one
     */
    @Test
    public void getUserValidTest(){
        // Given that
        User u = new User();
        u.setFirstName("John");
        u.setLastName("Smith");
        u.setUsername("JS");

        List<User> user = new ArrayList<User>();
        user.add(u);

        // When
        when(userRepository.findByUsername("JS")).thenReturn(user);
        User v = userService.getUser("JS");

        // Then
        assert(v.getFirstName()).equals(u.getFirstName());
    }

    /**
     * This test aims to cover if the service can correctly get the user details
     * given that the username is invalid
     * @given a user views an invalid profile
     * @when UserService tries to get the details of the invalid user
     * @then exception is thrown
     */
    @Test
    public void getUserInvalidTest(){
        // When
        when(userRepository.findByUsername("INVALID")).thenReturn(new ArrayList<User>());

        // then
        assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> {
            userService.getUser("INVALID");
        });
    }
}
