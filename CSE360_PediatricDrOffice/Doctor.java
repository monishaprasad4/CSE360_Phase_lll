import java.util.ArrayList;

public class Doctor extends User {

	private String specialty;

	public Doctor () {
		super();
	}
	
	public void setSpecialty(String specialty) {
		if (specialty.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.specialty = specialty;
		}
	}

	public String getSpecialty() {
		return specialty;
	}

	public String toString() {
		return super.getFirstName() + " " + super.getLastName() + " (" + specialty + ")";
	}
}
