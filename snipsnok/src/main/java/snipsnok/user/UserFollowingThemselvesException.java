package snipsnok.user;

public class UserFollowingThemselvesException extends RuntimeException {

    public UserFollowingThemselvesException(Integer id) {
        super("User " + id + " cannot follow themselves");
    }

    public UserFollowingThemselvesException(String username) {

        super("User " + username + " cannot follow themselves");
    }
}
