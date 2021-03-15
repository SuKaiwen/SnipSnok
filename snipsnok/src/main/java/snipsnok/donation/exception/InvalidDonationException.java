package snipsnok.donation.exception;

public class InvalidDonationException extends RuntimeException {
    public InvalidDonationException(double amount) {
        super(amount + "is an invalid Donation");
    }
}
