
public class PatientDataView 
{
	private String fullName;
	private String doctor;
	private String lastVisit;
	private String nextVisit;
	private Patient patient;
	
	public PatientDataView(Patient patient, ITService currentITService)
	{
		fullName = patient.getLastName() + ", " + patient.getFirstName();
		doctor = currentITService.getUserFullNameFromUniqueID(patient.getDoctorUniqueID());
		lastVisit = "1/1/1970";
		nextVisit = "1/1/1980";
		this.patient = patient;
	}
	
	public String getFullName()
	{
		return fullName;
	}

	public String getDoctor()
	{
		return doctor;
	}

	public String getLastVisit()
	{
		return lastVisit;
	}
	
	public String getNextVisit()
	{
		return nextVisit;
	}
	
	public Patient getPatient()
	{
		return patient;
	}
}
