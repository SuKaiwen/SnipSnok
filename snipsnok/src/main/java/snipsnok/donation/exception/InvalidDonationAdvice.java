package snipsnok.donation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidDonationAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidDonationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidDonationHandler(InvalidDonationException ex) {
        return ex.getMessage();
    }
}