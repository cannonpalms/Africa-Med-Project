package hibernate.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="users")
@org.hibernate.annotations.Table(appliesTo="users")
public class User {

	private long ID;
	private String username;
	private String password;
	
	public User() {
		//do nothing. A no-args constructor is required by hibernate.
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Column(name="username")
	public String getUsername() {
		return username;
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}
	
	@Id
	@GeneratedValue
	@Column(name="user_id")
	public long getID() {
		return ID;
	}
	
	public void setID(long ID) {
		this.ID = ID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "id: " + ID
				+ " username: " + username
				+ " password: " + password;
	}
	
}
