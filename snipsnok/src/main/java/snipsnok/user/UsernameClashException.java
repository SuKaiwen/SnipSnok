package snipsnok.user;

public class UsernameClashException extends RuntimeException{
	public UsernameClashException(String username) {
	    super("Username already exist of " + username);
	  }
}
