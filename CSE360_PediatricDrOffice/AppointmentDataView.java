
public class AppointmentDataView {
	private String date;
	private String time;
	private String patientFullName;
	private String reason;
	private String doctorFullName;
	
    @SuppressWarnings("deprecation")
	public AppointmentDataView(Appointment appointment, ITService itService)
	{
		
		date = (appointment.getApptDate().getMonth()+1) + "/" + appointment.getApptDate().getDate() + "/" + (appointment.getApptDate().getYear()+1900);  
		time = appointment.getApptDate().getHours() + ":" + appointment.getApptDate().getMinutes() + ":" + appointment.getApptDate().getMinutes();   
		patientFullName = itService.getUserFullNameFromUniqueID(appointment.getPatientUniqueID());
		reason = appointment.getReason();
		doctorFullName = itService.getUserFullNameFromUniqueID(appointment.getDoctorUniqueID());
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public String getPatientFullName()
	{
		return patientFullName;
	}
	
	public String getReason()
	{
		return reason;
	}
	
	public String getDoctorFullName()
	{
		return doctorFullName;
	}
}
