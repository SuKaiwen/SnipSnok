package snipsnok.content.exception;

public class ContentEditForbiddenException extends RuntimeException {

	public ContentEditForbiddenException(int id) {
		super("You are not allow to edit content "+ id);
	}
}
