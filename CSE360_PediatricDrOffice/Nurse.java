public class Nurse extends User {

	public Nurse () {
		super();
	}
	
	public String toString() {
		return super.getFirstName() + " " + super.getLastName();
	}
}
