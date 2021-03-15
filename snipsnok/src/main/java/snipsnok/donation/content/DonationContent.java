package snipsnok.donation.content;

import snipsnok.content.Content;
import snipsnok.user.User;

import javax.persistence.*;

@Entity
public class DonationContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "donationc_generator")
    @SequenceGenerator(name = "donationc_generator", sequenceName = "donationc_seq")
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User donator;

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    private Double amount;

    public Integer getId() {
        return id;
    }

    public User getDonator() {
        return donator;
    }

    public void setDonator(User donator) {
        this.donator = donator;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
