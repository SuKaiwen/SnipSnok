package snipsnok.content.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ContentNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(ContentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String contentNotFoundHandler(ContentNotFoundException ex) {
		return ex.getMessage();
	}
}
