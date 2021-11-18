import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

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
		lastVisit = "";
		nextVisit = "11/18/2021";
		
		Date today = new Date();
		today.setHours(0);
		
		ArrayList<Appointment> apps = currentITService.getAppointmentsForUser(patient.getUniqueID());
		for(int i = 0; i < apps.size(); i++)
		{
			if (apps.get(i).getApptDate().after(today))
			{
				nextVisit = apps.get(i).getApptDate().getMonth()+1 + "/" + apps.get(i).getApptDate().getDate() + "/" + (apps.get(i).getApptDate().getYear()+1900); 
			}
			if (apps.get(i).getApptDate().before(today))
			{
				lastVisit = apps.get(i).getApptDate().getMonth()+1 + "/" + apps.get(i).getApptDate().getDate() + "/" + (apps.get(i).getApptDate().getYear()+1900); 
			} 
		}
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
