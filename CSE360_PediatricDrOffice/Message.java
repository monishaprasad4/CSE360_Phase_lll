import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

	private String messageUniqueID;
	private String senderUniqueID;
	private UserType recipient;
	private String receiverUniqueID;
	private String subject;
	private String body;
	private String reply;
	private Date sentDate;
	private Date replyDate;
	
	public String toString() {
		String replySection = "";
		if (replyDate == null || reply == null) {
			// nothing to add to reply section
		} else {
			replySection = "\nReply: " + "\n" 
					+ getReplyDate_String() + "\n" 
					+ "Reply: " + reply + "\n\n";
		}

		User sender = ITService.Instance.getUserFromUniqueID(senderUniqueID);
		User recipient = ITService.Instance.getUserFromUniqueID(receiverUniqueID);
		String recipientName = (recipient != null) ? recipient.getFirstName() + " " + recipient.getLastName() : "";
		
		return "Sender: " + sender.getFirstName() + " " + sender.getLastName() + "\n" 
				+ "Recipient: " + recipientName + "\n"
				+ "Recipient Type: " + getRecipient_String() + "\n"
				+ getSentDate_String() + "\n" 
				+ "Subject: " + subject + "\n\n" 
				+ body + "\n" 
				+ replySection;
	}
	
	public String toString_MessageHistory() {
		return subject; 
	}

	public String getMessageUniqueID() {
		return messageUniqueID;
	}

	public void setMessageUniqueID(String messageUniqueID) {
		this.messageUniqueID = messageUniqueID;
	}

	public String getSenderUniqueID() {
		return senderUniqueID;
	}

	public void setSenderUniqueID(String senderUniqueID) {
		this.senderUniqueID = senderUniqueID;
	}

	public UserType getRecipient() {
		return this.recipient;
	}

	public String getRecipient_String() {
		if (this.recipient == UserType.NURSE) {
			return "NURSE";
		} else if (this.recipient == UserType.DOCTOR) {
			return "DOCTOR";
		} else {
			return null;
		}
	}

	public void setRecipient(UserType recipient) {
		this.recipient = recipient;
	}

	public String getReceiverUniqueID() {
		return this.receiverUniqueID;
	}

	public void setReceiverUniqueID(String receiverUniqueID) {
		this.receiverUniqueID = receiverUniqueID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getSentDate() {
		return sentDate;
	}
	
	public String getSentDate_String() {
		if (sentDate != null)
			return sentDate.toString();
		else
			return "";
	}
	
	public void setSentDate_String(String inputDate) {
		if (!inputDate.equalsIgnoreCase("null")) {
			try {
				this.sentDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(inputDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public String getReplyDate_String() {
		if (replyDate == null)
		{
			return "";
		}
		return replyDate.toString();
	}
	
	public void setReplyDate_String(String inputDate) {
		if (!inputDate.equalsIgnoreCase("null") && !inputDate.equalsIgnoreCase("")) {
			try {
				this.replyDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(inputDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}


	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getReply() {
		return this.reply;
	}
	
	public void setReply(String reply) {
		this.reply = reply;
	}
}
