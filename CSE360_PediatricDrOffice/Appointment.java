import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class Appointment {

	private Date when;
	private String reason;
	private String doctorUniqueID;
	public VisitDetails visitDetails;
	private String patientUniqueID;
	private String uniqueID;
	
	public String getApptDate_String() {
		return when.toString();		
	}

	public void setApptDate_String(String inputApptDate) {
		if (!inputApptDate.equalsIgnoreCase("null")) {
			try {
				this.when = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(inputApptDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Boolean checkIfUpcoming() {
		if (when.before(new Date())) {
			return false;
		} else {
			return true;
		}
	}
	
	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	
	public Date getApptDate() {
		return when;
	}

	public void setApptDate(Date date) {
		this.when = date;
	}

	public String getReason() {
		return (reason == null || reason.length() == 0) ? "<Unspecified reason>" : reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDoctorUniqueID() {
		return doctorUniqueID;
	}

	public void setDoctorUniqueID(String doctorUniqueID) {
		this.doctorUniqueID = doctorUniqueID;
	}

	public String getPatientUniqueID() {
		return patientUniqueID;
	}

	public void setPatientUniqueID(String patientUniqueID) {
		this.patientUniqueID = patientUniqueID;
	}
	
	public void setVisitDetails(VisitDetails visitDetails) {
		this.visitDetails = visitDetails;
	}
	
	public VisitDetails getVisitDetails() {
		return visitDetails;
	}
}
