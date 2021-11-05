public class Credentials {

	private String password;
	private String uniqueID;
	
	public void setPassword(String password) {
		this.password = password; 
	}
	
	public String getPassword() {
		return password;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	
	public boolean validate(String password) {
		return this.password.equals(password);
	}
}
