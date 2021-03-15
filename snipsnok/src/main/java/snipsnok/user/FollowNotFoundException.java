package snipsnok.user;

public class FollowNotFoundException extends RuntimeException{

    FollowNotFoundException(Integer id) {
        super("Could not find follow from user " + id);
    }
}
