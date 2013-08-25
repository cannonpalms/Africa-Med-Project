package hibernate.demo;

public class User {

	private Integer ID;
	private String username;
	private String password;
	
	public User() {
		//do nothing. A no-args constructor is required by hibernate.
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer ID) {
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
