package snipsnok.user;
import java.util.List;

public interface FollowService {

    Follow createFollow(User user, String username);

    void deleteFollow(User user, String username);

    List<Follow> getFollow(User user, String username);

    List<User> accountsFollowing(String username);

    List<User> accountsFollowed(String username);
}
