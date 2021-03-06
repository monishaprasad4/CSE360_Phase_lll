
public class MessageDataView {
	private String from;
	private String to;
	private String subject;
	private String date;
	private Message message;
	
	public MessageDataView(Message message, ITService itService)
	{
		from = itService.getUserFullNameFromUniqueID(message.getSenderUniqueID());
		to = itService.getUserFullNameFromUniqueID(message.getReceiverUniqueID());
		subject = message.getSubject();
		date = (message.getSentDate().getMonth()+1) + "/" + 
				message.getSentDate().getDay() + "/" + (message.getSentDate().getYear()+1900);
		
		this.message = message;
	}
	
	public String getFrom()
	{
		return from;
	}
	
	public String getTo()
	{
		return to;
	}
	
	public String getSubject()
	{
		return subject;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public Message getMessage()
	{
		return message;
	}
}
