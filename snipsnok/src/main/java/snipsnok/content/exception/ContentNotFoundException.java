package snipsnok.content.exception;

public class ContentNotFoundException extends RuntimeException {
	public ContentNotFoundException(int id){
		super("Could not find content "+ id);
	}
}
