package snipsnok.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import snipsnok.content.Content;
import snipsnok.likes.Likes;
import snipsnok.user.User;
import snipsnok.user.UserRepository;
import snipsnok.user.Follow;
import snipsnok.user.FollowRepository;

@ExtendWith(MockitoExtension.class)
public class FollowServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowServiceImpl followService;

    // Test a user can follow another user
    @Test
    public void testCreateFollow() throws Exception {

        User u = new User();
        u.setId(1);

        User followedUser = new User();
        followedUser.setId(2);
        followedUser.setUsername("username");

        Follow expected = new Follow();
        expected.setUser(u);
        expected.setFollowerId(2);

        User u4 = new User();
        u4.setId(4);

        List<User> someUser = new ArrayList<User>();
        someUser.add(followedUser);


        when(userRepository.findByUsername("username")).thenReturn(someUser);
        when(followRepository.save(Mockito.any(Follow.class))).thenReturn(expected);


        Follow actual = followService.createFollow(u, "username");

        assertThat(actual.getUser().getId()).isSameAs(expected.getUser().getId());
        assertThat(actual.getFollowerId()).isSameAs(expected.getFollowerId());
        assertThat(expected.getFollowerId()).isSameAs(2);


        Exception exception2 = assertThrows(UserNotFoundException.class, () ->{
            followService.createFollow(u, "usernameNotExisting");
        });
        assertEquals("Could not find user usernameNotExisting", exception2.getMessage());

    }

    @Test
    public void testCreateFollowUserFollowingThemselves() throws Exception {

        User u = new User();
        u.setId(1);
        u.setUsername("username");

        Follow expected = new Follow();
        expected.setUser(u);
        expected.setFollowerId(1);

        List<User> someUser = new ArrayList<User>();
        someUser.add(u);

        when(userRepository.findByUsername("username")).thenReturn(someUser);

        Exception exception = assertThrows(UserFollowingThemselvesException.class, () ->{
            followService.createFollow(u, "username");
                });

        assertEquals("User 1 cannot follow themselves", exception.getMessage());


    }

    // Test a follow object can be deleted
    @Test
    public void testDeleteFollow() throws Exception {
        // given
        User u = new User();
        u.setId(1);

        User followedUser = new User();
        followedUser.setId(2);
        followedUser.setUsername("username");

        Follow expected = new Follow();
        expected.setUser(u);
        expected.setFollowerId(2);


        List<Follow> l = new ArrayList<Follow>();
        l.add(expected);
        List<User> someUser = new ArrayList<User>();
        someUser.add(followedUser);

        when(userRepository.findByUsername("username")).thenReturn(someUser);
        when(followRepository.findByUserIdAndFollowId(1, 2)).thenReturn(l);


        followService.deleteFollow(u, "username");

        verify(followRepository, times(1)).delete(expected);

        User u4 = new User();
        u4.setId(4);

        List<Follow> l1 = new ArrayList<Follow>();
        when(followRepository.findByUserIdAndFollowId(4, 2)).thenReturn(l1);

       Exception exception = assertThrows(UserNotFoundException.class, () ->{
            followService.deleteFollow(u4, "username");
        });
        assertEquals("Could not find user 4", exception.getMessage());


        Exception exception1 = assertThrows(UserFollowingThemselvesException.class, () ->{
            followService.deleteFollow(followedUser, "username");
        });
        assertEquals("User 2 cannot follow themselves", exception1.getMessage());

        Exception exception2 = assertThrows(UserNotFoundException.class, () ->{
            followService.deleteFollow(u, "usernameNotExisting");
        });
        assertEquals("Could not find user usernameNotExisting", exception2.getMessage());


    }

    @Test
    public void testGetFollow() throws Exception{

        User u1 = new User();
        u1.setUsername("username1");
        u1.setId(1);

        User u2 = new User();
        u2.setUsername("username2");
        u2.setId(2);

        Follow expectedFollow = new Follow();
        expectedFollow.setUser(u1);
        expectedFollow.setFollowerId(2);

        List<Follow> expected = new ArrayList<Follow>();
        expected.add(expectedFollow);

        List<User> someUser = new ArrayList<User>();
        someUser.add(u2);

        when(userRepository.findByUsername("username2")).thenReturn(someUser);
        when(followRepository.findByUserIdAndFollowId(1, 2)).thenReturn(expected);

        List<Follow> actual = followService.getFollow(u1, "username2");
        assertThat(actual.get(0).getUser().getId() == 1);
        assertThat(actual.get(0).getFollowerId() == 2);


    }

    @Test
    public void testGetFollowEmpty() throws Exception{

        User u1 = new User();
        u1.setUsername("username1");
        u1.setId(1);

        User u2 = new User();
        u2.setUsername("username2");
        u2.setId(2);


        List<Follow> expected = new ArrayList<Follow>();

        List<User> someUser = new ArrayList<User>();
        someUser.add(u2);

        when(userRepository.findByUsername("username2")).thenReturn(someUser);
        when(followRepository.findByUserIdAndFollowId(1, 2)).thenReturn(expected);

        List<Follow> actual = followService.getFollow(u1, "username2");
        assertThat(actual.isEmpty());
       // assertThat(actual.get(0).getFollowerId() == 2);


    }



    @Test
    public void testAccountsFollowing() throws Exception{

        // given
        User u = new User();
        u.setUsername("username");
        u.setId(1);

        Optional<User> user2 = Optional.of(new User());
        User u2 = user2.get();
        u2.setUsername("username2");
        u2.setId(2);

        Optional<User> user3 = Optional.of(new User());
        User u3 = user3.get();
        u3.setUsername("username3");
        u3.setId(3);

        Follow f1 = new Follow();
        f1.setUser(u2);
        f1.setFollowerId(2);

        Follow f2 = new Follow();
        f2.setUser(u3);
        f2.setFollowerId(3);


        List<Follow> expected = new ArrayList<Follow>();
        expected.add(f1);
        expected.add(f2);

        List<User> expectedUser = new ArrayList<User>();
        expectedUser.add(u2);
        expectedUser.add(u3);

        List<User> someUser = new ArrayList<User>();
        someUser.add(u);


        when(userRepository.findByUsername("username")).thenReturn(someUser);
        when(followRepository.findByUserId(1)).thenReturn(expected);
        when(userRepository.findById(expected.get(0).getFollowerId())).thenReturn(user2);
        when(userRepository.findById(expected.get(1).getFollowerId())).thenReturn(user3);

        List<User> actual = followService.accountsFollowing("username");

        assertThat(actual.get(0).getId()).isSameAs(expectedUser.get(0).getId());

        assertThat(expected.get(0).getFollowerId()).isSameAs(2);

        assertThat(actual.get(0)).isSameAs(expectedUser.get(0));
        assertThat(actual.get(1).getId()).isSameAs(expectedUser.get(1).getId());
        assertThat(actual.get(1)).isSameAs(u3);


        Exception exception = assertThrows(UserNotFoundException.class, () ->{
            followService.accountsFollowing("nonExistingUsername");
        });
        assertEquals("Could not find user nonExistingUsername", exception.getMessage());

    }

    @Test
    public void testAccountsFollowed() throws Exception{

        // given
        User u1 = new User();
        u1.setUsername("username");
        u1.setId(1);

        User u2 = new User();
        u2.setId(2);

        User u3 = new User();
        u3.setId(3);

        Follow f1 = new Follow();
        f1.setUser(u2);
        f1.setFollowerId(1);

        Follow f2 = new Follow();
        f2.setUser(u3);
        f2.setFollowerId(1);


        List<Follow> expected = new ArrayList<Follow>();
        expected.add(f1);
        expected.add(f2);

        List<User> expectedUser = new ArrayList<User>();
        expectedUser.add(u2);
        expectedUser.add(u3);

        List<User> someUser = new ArrayList<User>();
        someUser.add(u1);

        when(userRepository.findByUsername("username")).thenReturn(someUser);
        when(followRepository.findByFollowId(1)).thenReturn(expected);
        when(userRepository.getOne(2)).thenReturn(u2);
        when(userRepository.getOne(3)).thenReturn(u3);

        List<User> actual = followService.accountsFollowed("username");

        assertThat(actual.get(0).getId()).isSameAs(expectedUser.get(0).getId());
        assertThat(actual.get(1).getId()).isSameAs(expectedUser.get(1).getId());
        assertThat(actual.get(1)).isSameAs(u3);


        Exception exception = assertThrows(UserNotFoundException.class, () ->{
            followService.accountsFollowed("nonExistingUsername");
        });
        assertEquals("Could not find user nonExistingUsername", exception.getMessage());


    }

    @Test
    public void testAccountFollowingReturnListEmpty() throws Exception{

        // Username exists but is not followed by anyone
        User u = new User();
        u.setUsername("username");
        u.setId(4);

        List<Follow> expected = new ArrayList<Follow>();

        List<User> someUser = new ArrayList<User>();
        someUser.add(u);

        when(userRepository.findByUsername("username")).thenReturn(someUser);
        when(followRepository.findByUserId(4)).thenReturn(expected);

        List<User> actual = followService.accountsFollowing("username");
        assertThat(actual.size() == 0);
    }


    @Test
    public void testAccountFollowedReturnListEmpty() throws Exception{

        // Username exists but is not followed by anyone
        User u = new User();
        u.setUsername("username");
        u.setId(4);

        List<Follow> expected = new ArrayList<Follow>();

        List<User> someUser = new ArrayList<User>();
        someUser.add(u);

        when(userRepository.findByUsername("username")).thenReturn(someUser);
        when(followRepository.findByFollowId(4)).thenReturn(expected);

        List<User> actual = followService.accountsFollowed("username");
        assertThat(actual.size() == 0);
    }



}