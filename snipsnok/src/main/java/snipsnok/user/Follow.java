package snipsnok.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Follow {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="follow_generator")
	@SequenceGenerator(name="follow_generator", sequenceName = "follow_seq")
	@Column(updatable = false,  nullable=false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFollowed;
	
	private Integer followId;

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDateFollowed() {
		return dateFollowed;
	}

	public void setDateFollowed(Date dateFollowed) {
		this.dateFollowed = dateFollowed;
	}

	public Integer getFollowerId() {
		return followId;
	}

	public void setFollowerId(Integer followerId) {
		this.followId = followerId;
	}
	
	
}
