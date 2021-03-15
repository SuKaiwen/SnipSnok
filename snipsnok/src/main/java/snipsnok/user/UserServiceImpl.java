package snipsnok.user;

import org.springframework.stereotype.Service;
import snipsnok.security.CurrentUser;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    private CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    /**
     * Function getUser
     * @param username
     * @return an user object with the corresponding Id
     * OR throws the user not found exception if no
     * user with that Id.
     */
    public User getUser(String username){
        List<User> u = userRepository.findByUsername(username);

        if(u.isEmpty()){
            throw new UserNotFoundException(username);
        }

        User user = u.get(0);

        return user;
    }

    /**
     *
     * @param user
     * @param newDetails
     * Takes the user ID and a new User object as parameters
     * User ID is the ID of the user we want to edit
     * New Details is the changes we want to make
     * to the current user's account
     * Uses setters to change the update the details
     * of the user
     * @return
     */
    public User editUser(CurrentUser user, User newUser){
        
        // Change the details of the current user to
        // the details of the new user (aka the edits
        // made by the user on the front end)

        User tempUser = user.getUser();

        tempUser.setFirstName(newUser.getFirstName());
        tempUser.setLastName(newUser.getLastName());
        tempUser.setTop3Creators(newUser.getTop3Creators());
        tempUser.setDescription(newUser.getDescription());

        return userRepository.save(tempUser);
    }

    public User addUser(User user){
    	List<User> u = userRepository.findByUsername(user.getUsername());
    	if (u.size() >= 1) {
    		throw new UsernameClashException(user.getUsername());
    	}
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        if(currentUser.isAdmin()){
           return userRepository.findAll();
        }
        return null;
    }
}
