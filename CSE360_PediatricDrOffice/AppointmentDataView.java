import java.text.SimpleDateFormat;

public class AppointmentDataView {
	private String date;
	private String time;
	private String patientFullName;
	private String reason;
	private String doctorFullName;
	private Appointment appointment;
	
    @SuppressWarnings("deprecation")
	public AppointmentDataView(Appointment appointment, ITService itService)
	{
		
		//date = (appointment.getApptDate().getMonth()+1) + "/" + appointment.getApptDate().getDate() + "/" + (appointment.getApptDate().getYear()+1900);
		date = new SimpleDateFormat("MM/dd/yyyy").format(appointment.getApptDate());
		//time = appointment.getApptDate().getHours() + ":" + appointment.getApptDate().getMinutes() + ":" + appointment.getApptDate().getMinutes();
		time = new SimpleDateFormat("h:mm aa").format(appointment.getApptDate());
		patientFullName = itService.getUserFullNameFromUniqueID(appointment.getPatientUniqueID());
		reason = appointment.getReason();
		doctorFullName = itService.getUserFullNameFromUniqueID(appointment.getDoctorUniqueID());
		this.appointment = appointment;
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
	
	public Appointment getAppointment()
	{
		return appointment;
	}
}
