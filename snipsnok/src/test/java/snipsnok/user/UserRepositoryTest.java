package snipsnok.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {


    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;

//    List<User> findByFirstNameLikeIgnoreCase(String name);
//    List<User> findByLastNameLikeIgnoreCase(String name);
//    List<User> findByUsernameLikeIgnoreCase(String name);
//    List<User> findByUsername(@Param("username") String username);
//    List<User> findByUsernameIgnoreCaseContaining(String username);

    /**
     * Testing to see if dependency injection works
     */
    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(followRepository).isNotNull();
        assertThat(userRepository).isNotNull();
    }


    private User createUser() {
        User user = new User();
        return userRepository.save(user);
    }

    /**
     * Testing if the system correctly returns all users found by a username
     */
    @Test
    public void testFindByUserName(){
        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();

        user1.setUsername("Su123");
        user2.setUsername("Su1234");
        user3.setUsername("Smith123");
        user4.setUsername("Smith321");

        user1.setLastName("Su");

        List<User> users = userRepository.findByUsername("Su123");

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getLastName()).isEqualTo("Su");
        assertThat(users.get(0).getUsername()).isEqualTo("Su123");
    }

    /**
     * Testing if the system correctly returns null if the user
     * with that username isnt found.
     */
    @Test
    public void testFindByUserNameNull(){
        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();

        user1.setUsername("Su123");
        user2.setUsername("Su1234");
        user3.setUsername("Smith123");
        user4.setUsername("Smith321");

        user1.setLastName("Su");

        List<User> users = userRepository.findByUsername("Su123456");

        assertThat(users.size()).isEqualTo(0);
    }

    /**
     * Testing if the system correctly returns all users with similar
     * usernames with the given
     */
    @Test
    public void testFindByLikeUserName(){
        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();

        user1.setUsername("Su123");
        user2.setUsername("Su1234");
        user3.setUsername("Smith123");
        user4.setUsername("Smith321");

        List<User> users = userRepository.findByUsernameIgnoreCaseContaining("Su");

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getUsername()).isEqualTo("Su123");
        assertThat(users.get(1).getUsername()).isEqualTo("Su1234");
    }
}
