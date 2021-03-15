package snipsnok.user;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.content.exception.ContentNotFoundException;
import snipsnok.donation.exception.InvalidDonationException;
import snipsnok.user.User;
import snipsnok.user.UserNotCreatorException;
import snipsnok.user.UserNotFoundException;
import snipsnok.user.UserRepository;
import snipsnok.user.FollowService;

import java.util.List;
import java.util.ArrayList;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService{
    private UserRepository userRepository;
    private FollowRepository followRepository;

    public FollowServiceImpl(
            UserRepository userRepository,
            FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }


    @Override
    @Transactional
    public Follow createFollow(User user, String username){
    //@PathVariable int id
        Follow n = new Follow();

        // Find the user we want to follow by username
        // Assuming username is unique and the list will either have 1 or 0 elements
        List<User> userToFollow = userRepository.findByUsername(username);

        // If no account exists with the username return an exception
        if (userToFollow.isEmpty()){
            throw new UserNotFoundException(username);
        }

        // User cannot follow themselves
        if (userToFollow.get(0).getId() == user.getId()){
           throw new UserFollowingThemselvesException(user.getId());
        }

        // Set the user as the current userId
        n.setUser(user);

        // Set the user we are wanting to follow by retrieving their user ID
        n.setFollowerId(userToFollow.get(0).getId());

        return followRepository.save(n);

    }

    @Override
    @Transactional
    public void deleteFollow(User user, String username){
        // Find the user ID of the account we want to unfollow by username
        // Assuming username is unique
        List<User> userToUnfollow = userRepository.findByUsername(username);

        // If no account exists with the username return an exception
        if (userToUnfollow.isEmpty()){
            throw new UserNotFoundException(username);
        }

        if (userToUnfollow.get(0).getId() == user.getId()){
            throw new UserFollowingThemselvesException(user.getId());
        }

        // Get the ID of this user we are unfollowing
        int id = userToUnfollow.get(0).getId();


        // Want to find the Follow object with userId = userId and followId = id
        List<Follow> n = followRepository.findByUserIdAndFollowId(user.getId(), id);


        if (n.isEmpty()){
            throw new UserNotFoundException(user.getId());
        }
        followRepository.delete(n.get(0));

        return;
    }

    @Override
    @Transactional
    public List<Follow> getFollow(User user, String username){


        // Find the user we want to follow by username
        // Assuming username is unique and the list will either have 1 or 0 elements
        List<User> userToFollow = userRepository.findByUsername(username);

        // If no account exists with the username return an exception
        if (userToFollow.isEmpty()){
            throw new UserNotFoundException(username);
        }


        List<Follow> returnFollow = followRepository.findByUserIdAndFollowId(user.getId(), userToFollow.get(0).getId());


        return returnFollow;

    }


    // Find all users that the user as given by the username is following
    @Override
    @Transactional
    public List<User> accountsFollowing(String username){
        //Find the user corresponding to the username

        // Find the user ID of the user as specified by the username
        // Assuming username is unique
        List<User> userOfUsername = userRepository.findByUsername(username);

        // If no account exists with the username return an exception
        if (userOfUsername.isEmpty()){
            throw new UserNotFoundException(username);
        }

        // Get the ID of this user
        int id = userOfUsername.get(0).getId();

        // All follow objects where this user is following another user
         List<Follow> followedAccounts = followRepository.findByUserId(id);

        //Return a list of users for each followId in followingAccounts
        List<User> userAccounts = new ArrayList<User>();
        for (int i = 0; i < followedAccounts.size(); i++){

            Integer thisFollowerId = followedAccounts.get(i).getFollowerId();

            Optional<User> someUser = userRepository.findById(thisFollowerId);

            User thisUser = someUser.get();

            userAccounts.add(thisUser);

        }

        return userAccounts;

    }

    // Find the users who are following the user as specified by the username
    @Override
    @Transactional
    public List<User> accountsFollowed(String username) {

        // Find the user ID of the user as specified by the username
        // Assuming username is unique
        List<User> userOfUsername = userRepository.findByUsername(username);

        // If no account exists with the username return an exception
        if (userOfUsername.isEmpty()) {
            throw new UserNotFoundException(username);
        }

        // Get the ID of this user
        int id = userOfUsername.get(0).getId();

        //User u = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Follow> followingAccounts = followRepository.findByFollowId(id);

        //Return a list of users for each followId in followingAccounts
        List<User> userAccounts = new ArrayList<User>();
        for (int i = 0; i < followingAccounts.size(); i++){

            User thisUser = userRepository.getOne(followingAccounts.get(i).getUser().getId());
            userAccounts.add(thisUser);

        }
        return userAccounts;
       // return followingAccounts;
    }
}
