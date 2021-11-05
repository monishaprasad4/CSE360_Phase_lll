import java.util.Comparator;

public class AppointmentComparator implements Comparator<Appointment>
{	
	public int compare(Appointment first, Appointment second)
	{
		// descending order
		return second.getApptDate().compareTo(first.getApptDate());
	}
}
