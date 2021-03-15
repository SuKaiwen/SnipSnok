package snipsnok.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.query.Param;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FollowRepositoryTest {


    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;

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


    private Follow createFollow(User followUser, int id) {
        Follow follow = new Follow();
        follow.setUser(followUser);
        follow.setFollowerId(id);
        return followRepository.save(follow);
    }


    /**
     * Test repository implemented
     */
    @Test
    public void testFindByUserId() {
        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();

        //User1 following User 2
        Follow follow11 = createFollow(user1, user2.getId());

        Follow follow21 = createFollow(user2, user1.getId());

        Follow follow23 = createFollow(user2, user3.getId());

        //List with one user
        List<Follow> follows = followRepository.findByUserId(user1.getId());
        assertThat(follows.size()).isEqualTo(1);

        //Check the userId and followerId are correct
        assertThat(follows.get(0).getUser().getId()).isEqualTo(user1.getId());
        assertThat(follows.get(0).getUser()).isEqualTo(user1);
        assertThat(follows.get(0).getFollowerId()).isEqualTo(user2.getId());


        //List with two users
        List<Follow> follows2 = followRepository.findByUserId(user2.getId());
        assertThat(follows2.size()).isEqualTo(2);


        //Check the userId and followerId are correct
        assertThat(follows2.get(0).getUser().getId()).isEqualTo(user2.getId());
        assertThat(follows2.get(0).getFollowerId()).isEqualTo(user1.getId());

        assertThat(follows2.get(1).getUser().getId()).isEqualTo(user2.getId());
        assertThat(follows2.get(1).getFollowerId()).isEqualTo(user3.getId());


        //List should by empty
        List<Follow> follows3 = followRepository.findByUserId(user4.getId());
        assertThat(follows3.size()).isEqualTo(0);

    }

    /**
     * Test repository implemented
     */
    @Test
    public void testFindByFollowId() {
        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();

        //User1 following User 3
        Follow follow11 = createFollow(user1, user3.getId());

        Follow follow21 = createFollow(user2, user1.getId());

        Follow follow23 = createFollow(user2, user3.getId());

        //List with one user
        List<Follow> follows = followRepository.findByFollowId(user1.getId());
        assertThat(follows.size()).isEqualTo(1);

        //Check the userId and followerId are correct
        assertThat(follows.get(0).getUser().getId()).isEqualTo(user2.getId());
        assertThat(follows.get(0).getFollowerId()).isEqualTo(user1.getId());


        //List with two users
        List<Follow> follows2 = followRepository.findByFollowId(user3.getId());
        assertThat(follows2.size()).isEqualTo(2);


        //Check the userId and followerId are correct
        assertThat(follows2.get(0).getUser().getId()).isEqualTo(user1.getId());
        assertThat(follows2.get(0).getFollowerId()).isEqualTo(user3.getId());

        assertThat(follows2.get(1).getUser().getId()).isEqualTo(user2.getId());
        assertThat(follows2.get(1).getFollowerId()).isEqualTo(user3.getId());


        //List should by empty
        List<Follow> follows3 = followRepository.findByFollowId(user4.getId());
        assertThat(follows3.size()).isEqualTo(0);

    }

    /**
     * Test repository implemented
     */
    @Test
    public void testFindByUserIdAndFollowId() {
        User user1 = createUser();
        User user2 = createUser();
        User user3 = createUser();
        User user4 = createUser();

        //User1 following User 3
        Follow follow11 = createFollow(user1, user3.getId());

        Follow follow21 = createFollow(user2, user1.getId());

        Follow follow23 = createFollow(user2, user3.getId());

        Follow follow24 = createFollow(user2, user3.getId());

        //List with one user
        List<Follow> follows = followRepository.findByUserIdAndFollowId(user1.getId(), user3.getId());
        assertThat(follows.size()).isEqualTo(1);

        //Check the userId and followerId are correct
        assertThat(follows.get(0).getUser().getId()).isEqualTo(user1.getId());
        assertThat(follows.get(0).getFollowerId()).isEqualTo(user3.getId());
        assertThat(follows.get(0).getFollowerId()).isEqualTo(follow11.getFollowerId());
        assertThat(follows.get(0).getUser().getId()).isEqualTo(follow11.getUser().getId());


        //List with two users
        List<Follow> follows2 = followRepository.findByUserIdAndFollowId(user2.getId(), user3.getId());
        assertThat(follows2.size()).isEqualTo(2);


        //Check the userId and followerId are correct
        assertThat(follows2.get(0).getUser().getId()).isEqualTo(user2.getId());
        assertThat(follows2.get(0).getFollowerId()).isEqualTo(user3.getId());

        assertThat(follows2.get(1).getUser().getId()).isEqualTo(user2.getId());
        assertThat(follows2.get(1).getFollowerId()).isEqualTo(user3.getId());


        //List should by empty
        List<Follow> follows3 = followRepository.findByUserIdAndFollowId(user1.getId(), user2.getId());
        assertThat(follows3.size()).isEqualTo(0);

        //List should by empty
        List<Follow> follows4 = followRepository.findByUserIdAndFollowId(user1.getId(), user1.getId());
        assertThat(follows4.size()).isEqualTo(0);

        //List should by empty
        List<Follow> follows5 = followRepository.findByUserIdAndFollowId(user4.getId(), user3.getId());
        assertThat(follows5.size()).isEqualTo(0);


    }


}
