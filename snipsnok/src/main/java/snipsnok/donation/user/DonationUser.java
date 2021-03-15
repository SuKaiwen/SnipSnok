package snipsnok.donation.user;

import snipsnok.user.User;

import javax.persistence.*;

@Entity
public class DonationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "donationu_generator")
    @SequenceGenerator(name = "donationu_generator", sequenceName = "donationu_seq")
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User donator;

    private Integer receiverId;

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

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


}
