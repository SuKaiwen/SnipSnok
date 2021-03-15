package snipsnok.likes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import snipsnok.content.Content;
import snipsnok.user.User;

@Entity
public class Likes {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="likes_generator")
	@SequenceGenerator(name="likes_generator", sequenceName = "likes_seq")
	@Column(updatable = false,  nullable=false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="content_id", nullable=false)
	private Content content;
	
	private Boolean dislike;

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Boolean getDislike() {
		return dislike;
	}

	public void setDislike(Boolean dislike) {
		this.dislike = dislike;
	}
	
	
}
