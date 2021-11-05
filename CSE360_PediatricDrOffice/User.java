import java.time.LocalDate;
import java.util.ArrayList;

public abstract class User {

	private String firstName;
	private String lastName;
	private LocalDate DOB;
	private String phoneNumber;
	private String email;
	private Credentials credentials;
	private UserType userType;
	
	public User() {
		credentials = new Credentials();
	}

	public String getUniqueID() {
		return credentials.getUniqueID();
	}

	public void setUniqueID(String uniqueID) {
		credentials.setUniqueID(uniqueID);
	}

	public String getPassword() {
		return credentials.getPassword();
	}

	public Credentials getCredentials() {
		return credentials;
	}
	
	public void setCredentials(String uniqueID, String password) {
		this.credentials.setUniqueID(uniqueID);
		this.credentials.setPassword(password);
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fName) {
		if (fName.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.firstName = fName;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lName) {
		if (lName.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.lastName = lName;
		}
		
	}

	public LocalDate getDOB() {
		return DOB;
	}

	public void setDOB(LocalDate date) {
		this.DOB = date;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phone) {
		if (phone.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.phoneNumber = phone;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.email = email;
		}
	}
	
	public abstract String toString();
}
