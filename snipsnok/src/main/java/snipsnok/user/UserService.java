package snipsnok.user;

import snipsnok.security.CurrentUser;

import java.util.List;

public interface UserService {
    public List<User> findAllUsers();

    public User getUser(String username);

    public User editUser(CurrentUser curr, User newUser);

    public User addUser(User user);
}
