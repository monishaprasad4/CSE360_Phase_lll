import java.time.LocalDate;
import java.util.ArrayList;

public class Patient extends User {

	private String knownAllergies;
	private String previousHealthIssues;
	private String prescribedMedications;
	private String immunizationHistory;
	private Pharmacy pharmacy;
	private Insurance insurance;
	private String doctorUniqueID;

	public Patient () {
		super();
		
		pharmacy = new Pharmacy();
		insurance = new Insurance();
	}
	
	public void setKnownAllergies(String allergies) {
		this.knownAllergies = allergies;
	}

	public String getKnownAllergies() {
		return knownAllergies;
	}

	public void setPreviousHealthIssues(String issues) {
		if (issues.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.previousHealthIssues = issues;
		}
	}

	public String getPreviousHealthIssues() {
		return previousHealthIssues;
	}

	public void setPrescribedMedications(String medications) {
		if (medications.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.prescribedMedications = medications;
		}
	}

	public String getPrescribedMedications() {
		return prescribedMedications;
	}

	public void setImmunizationHistory(String history) {
		if (history.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.immunizationHistory = history;
		}
	}

	public String getImmunizationHistory() {
		return immunizationHistory;
	}
	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public void setPharmacy(String namePharmacy, String streetAddress, String city, String state, int zip) {
		if (namePharmacy.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.pharmacy.setName(namePharmacy); 
		}
		if (streetAddress.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.pharmacy.setStreetAddress(streetAddress); 
		}
		if (city.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.pharmacy.setCity(city); 
		}
		if (state.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.pharmacy.setState(state); 
		}
		if (zip == 0) {
			// don't set
		} else {
			this.pharmacy.setZip(zip); 
		}
	}
	
	public String getPharmacy_Name() {
		return this.pharmacy.getName(); 
	}
	
	public String getPharmacy_StreetAddress() {
		return this.pharmacy.getStreetAddress(); 
	}
	
	public String getPharmacy_City() {
		return this.pharmacy.getCity();
	}
	
	public String getPharmacy_State() {
		return this.pharmacy.getState();
	}
	
	public int getPharmacy_Zip() { 
		return this.pharmacy.getZip(); 
	}
	
	public String getPharmacy_Zip_String() { 
		return String.valueOf(this.pharmacy.getZip()); 
	}
	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public void setInsurance(String nameInsurance, String insurancePolicyId, String phoneNumber) {
		if (nameInsurance.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.insurance.setName(nameInsurance);
		}
		if (insurancePolicyId.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.insurance.setInsurancePolicyId(insurancePolicyId);
		}
		if (phoneNumber.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.insurance.setPhoneNumber(phoneNumber); 
		}
	}

	public String getInsurance_Name() {
		return this.insurance.getName();
	}

	public String getInsurance_InsurancePolicyId() {
		return this.insurance.getInsurancePolicyId();
	}

	public String getInsurance_PhoneNumber() {
		return this.insurance.getPhoneNumber(); 
	}
	
	public String getDoctorUniqueID() {
		return doctorUniqueID;
	}
	
	public void setDoctorUniqueID(String doctorUniqueID) {
		changeDoctorUniqueID(doctorUniqueID);
	}

	public void changeDoctorUniqueID(String doctorUniqueID) {
		if (doctorUniqueID.equalsIgnoreCase("null")) {
			// don't set
		} else {
			this.doctorUniqueID = doctorUniqueID;
		}
	}
	
	public  String toString() {
		return "Name: " + getFirstName() + " " + getLastName() + "\n" +
				"DOB: " + super.getDOB() + "\n" +
				"Phone: " + super.getPhoneNumber() + "\n" +
				"Email: " + super.getEmail() + "\n\n" + 
				"Known Allergies: " + knownAllergies + "\n" +
				"Pharmacy: " + pharmacy.getName();
	}
}
