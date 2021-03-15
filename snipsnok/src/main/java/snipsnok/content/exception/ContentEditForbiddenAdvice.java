package snipsnok.content.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ContentEditForbiddenAdvice {
	@ResponseBody
	@ExceptionHandler(ContentEditForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String contentEditForbiddenHandler(ContentEditForbiddenException ex) {
		return ex.getMessage();
	}
}
