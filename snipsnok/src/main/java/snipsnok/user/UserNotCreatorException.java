package snipsnok.user;

public class UserNotCreatorException extends RuntimeException {
    public UserNotCreatorException(String userName) {
        super(userName + " Not a Creator");
    }

    public UserNotCreatorException(int userId) {
        super(userId + "Not a Creator");
    }
}
