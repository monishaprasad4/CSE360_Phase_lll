import java.util.Comparator;

public class MessageComparator implements Comparator<Message>
{	
	public int compare(Message first, Message second)
	{
		// descending order
		return second.getSentDate().compareTo(first.getSentDate());
	}
}
