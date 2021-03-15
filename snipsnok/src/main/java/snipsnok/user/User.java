package snipsnok.user;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class User {
	@Id
	@Column(name = "user_id", updatable = false,  nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_generator")
	@SequenceGenerator(name="user_generator", sequenceName = "user_seq")
	private Integer id;
	
	private String firstName;

	private String lastName;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private String description;
	
	private String top3Creators;
	
	private String creditCardNumber;
	
	private String creditCardName;
	
	private Date creditCardExp;
	
	private Boolean isCreator;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTop3Creators() {
		return top3Creators;
	}

	public void setTop3Creators(String top3Creators) {
		this.top3Creators = top3Creators;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}

	public Date getCreditCardExp() {
		return creditCardExp;
	}

	public void setCreditCardExp(Date creditCardExp) {
		this.creditCardExp = creditCardExp;
	}

	public Boolean isCreator() {
		return isCreator;
	}

	public void setCreator(Boolean isCreator) {
		this.isCreator = isCreator;
	}
	
	
}
