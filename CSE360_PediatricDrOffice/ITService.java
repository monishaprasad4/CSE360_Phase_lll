import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.io.File;  
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class ITService {

	private ArrayList<User> users;
	private ArrayList<Message> messages;
	private ArrayList<Appointment> appointments;
	String systemDataFileName = "systemdata.txt";
	
	public ITService() {
		// default constructor 
		this.users = new ArrayList<User>();
		this.messages = new ArrayList<Message>();
		this.appointments = new ArrayList<Appointment>();
	}
	
	void readFromFile() {
		System.out.println("Reading from File");
		try { 
			File myObj = new File(systemDataFileName);
			Scanner myReader = new Scanner(myObj);
			// read users
			Patient tempPatient; Nurse tempNurse; Doctor tempDoctor;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				if (data.equalsIgnoreCase("*NewUser")) {
					// new user, read next line to determine User Type
					data = myReader.nextLine();
					if (data.equalsIgnoreCase("PATIENT")) {
						System.out.println("Reading New User - Patient");
						tempPatient = new Patient();
						tempPatient.setUserType(UserType.PATIENT);
						tempPatient.setFirstName(myReader.nextLine());
						tempPatient.setLastName(myReader.nextLine());
						tempPatient.setPhoneNumber(myReader.nextLine());
						tempPatient.setDOB(LocalDate.parse(myReader.nextLine(), formatter));
						tempPatient.setEmail(myReader.nextLine());						
						tempPatient.setCredentials(myReader.nextLine(), myReader.nextLine());
						tempPatient.setKnownAllergies(myReader.nextLine());						
						tempPatient.setPreviousHealthIssues(myReader.nextLine());						
						tempPatient.setImmunizationHistory(myReader.nextLine());						
						tempPatient.changeDoctorUniqueID(myReader.nextLine());	
						tempPatient.setPharmacy(myReader.nextLine(), myReader.nextLine(), myReader.nextLine(), myReader.nextLine(), Integer.parseInt(myReader.nextLine()));
						tempPatient.setInsurance(myReader.nextLine(), myReader.nextLine(), myReader.nextLine());
						users.add(tempPatient);
					} else if (data.equalsIgnoreCase("NURSE")) {
						System.out.println("Reading New User - Nurse");
						tempNurse = new Nurse();
						tempNurse.setUserType(UserType.NURSE);
						tempNurse.setFirstName(myReader.nextLine());
						tempNurse.setLastName(myReader.nextLine());
						tempNurse.setPhoneNumber(myReader.nextLine());
						tempNurse.setDOB(LocalDate.parse(myReader.nextLine(), formatter));
						tempNurse.setEmail(myReader.nextLine());			
						tempNurse.setCredentials(myReader.nextLine(), myReader.nextLine());
						users.add(tempNurse);
					} else if (data.equalsIgnoreCase("DOCTOR")) {
						System.out.println("Reading New User - Doctor");
						tempDoctor = new Doctor();
						tempDoctor.setUserType(UserType.DOCTOR);
						tempDoctor.setFirstName(myReader.nextLine());
						tempDoctor.setLastName(myReader.nextLine());
						tempDoctor.setPhoneNumber(myReader.nextLine());
						tempDoctor.setDOB(LocalDate.parse(myReader.nextLine(), formatter));
						tempDoctor.setEmail(myReader.nextLine());			
						tempDoctor.setCredentials(myReader.nextLine(), myReader.nextLine());
						tempDoctor.setSpecialty(myReader.nextLine());
						users.add(tempDoctor);
					}
				} else if (data.equalsIgnoreCase("*Messages")) {
					break;
				} else if (data.equalsIgnoreCase("*Appointments")) {
					break;
				}
			}
			
			// read messages
			Message tempMessage;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				if (data.equalsIgnoreCase("*NewMessage")) {
					// new appointment, read details 
					System.out.println("Reading New Message");
					tempMessage = new Message();
					tempMessage.setSenderUniqueID(myReader.nextLine());
					tempMessage.setReceiverUniqueID(myReader.nextLine());
					data = myReader.nextLine();
					if (data.equalsIgnoreCase("NURSE")) {
						tempMessage.setRecipient(UserType.NURSE);
					} else if (data.equalsIgnoreCase("DOCTOR")) {
						tempMessage.setRecipient(UserType.DOCTOR);
					}					
					tempMessage.setSubject(myReader.nextLine());
					tempMessage.setBody(myReader.nextLine());
					tempMessage.setReply(myReader.nextLine());
					tempMessage.setSentDate_String(myReader.nextLine());
					tempMessage.setReplyDate_String(myReader.nextLine());	
					tempMessage.setMessageUniqueID(tempMessage.getSenderUniqueID() + " " + tempMessage.getReceiverUniqueID() + " " + tempMessage.getSentDate_String());
					messages.add(tempMessage);
				} else if (data.equalsIgnoreCase("*Appointments")) {
					break;
				} else if (data.equalsIgnoreCase("*Users")) {
					break;
				}
			}
			
			// read appointments
			Appointment tempAppointment; VisitDetails tempVisitDetails; Vitals tempVitals;
			String intData;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				if (data.equalsIgnoreCase("*NewAppointment")) {
					// new appointment, read details 
					System.out.println("Reading New Appointment");
					tempAppointment = new Appointment();
					tempVisitDetails = new VisitDetails();
					tempVitals = new Vitals();
					tempAppointment.setUniqueID(myReader.nextLine());
					tempAppointment.setPatientUniqueID(myReader.nextLine());
					tempAppointment.setDoctorUniqueID(myReader.nextLine());
					tempAppointment.setReason(myReader.nextLine());
					tempAppointment.setApptDate_String(myReader.nextLine());
					tempVisitDetails.setHealthConcerns(myReader.nextLine());
					tempVisitDetails.setPhysicalHealthFindings(myReader.nextLine());
					tempVisitDetails.setPrescriptions(myReader.nextLine());
					intData = myReader.nextLine();
					if (intData != null && !intData.equalsIgnoreCase("null")) {
						tempVitals.setBPDiastolic(Integer.parseInt(intData));
					}
					intData = myReader.nextLine();
					if (intData != null && !intData.equalsIgnoreCase("null")) {
						tempVitals.setBPSystolic(Integer.parseInt(intData));
					}
					intData = myReader.nextLine();
					if (intData != null && !intData.equalsIgnoreCase("null")) {
						tempVitals.setHeight(Integer.parseInt(intData));
					}
					intData = myReader.nextLine();
					if (intData != null && !intData.equalsIgnoreCase("null")) {
						tempVitals.setTemperature(Integer.parseInt(intData));
					}
					intData = myReader.nextLine();
					if (intData != null && !intData.equalsIgnoreCase("null")) {
						tempVitals.setWeight(Integer.parseInt(intData));
					}
					tempVisitDetails.setVitals(tempVitals);
					tempAppointment.setVisitDetails(tempVisitDetails);
					appointments.add(tempAppointment);
				} else if (data.equalsIgnoreCase("*Messages")) {
					break;
				} else if (data.equalsIgnoreCase("*Users")) {
					break;
				}
			}
			
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		System.out.println("Completed reading from File");
	}
	
	void printToFile() {
		System.out.println("Printing to File");
		try {
			FileWriter myWriter = new FileWriter(systemDataFileName);
			
			// print all users
			myWriter.write("*Users\n");
			for (int i = 0; i < users.size(); i++) { 		      
				myWriter.write("*NewUser\n"); 
				if (users.get(i).getUserType() == UserType.PATIENT) {
					myWriter.write("PATIENT\n"); 
				} else if (users.get(i).getUserType() == UserType.NURSE) {
					myWriter.write("NURSE\n"); 
				} else if (users.get(i).getUserType() == UserType.DOCTOR) {
					myWriter.write("DOCTOR\n"); 
				} 
				myWriter.write(users.get(i).getFirstName() + "\n"); 
				myWriter.write(users.get(i).getLastName() + "\n");
				myWriter.write(users.get(i).getPhoneNumber() + "\n");
				myWriter.write(users.get(i).getDOB() + "\n");
				myWriter.write(users.get(i).getEmail() + "\n");
				myWriter.write(users.get(i).getUniqueID() + "\n");
				myWriter.write(users.get(i).getPassword() + "\n");
				if (users.get(i).getUserType() == UserType.PATIENT) {
					myWriter.write(((Patient)users.get(i)).getKnownAllergies() + "\n"); 
					myWriter.write(((Patient)users.get(i)).getPreviousHealthIssues() + "\n"); 
					myWriter.write(((Patient)users.get(i)).getImmunizationHistory() + "\n"); 
					myWriter.write(((Patient)users.get(i)).getDoctorUniqueID() + "\n"); 
					myWriter.write(((Patient)users.get(i)).getPharmacy().getName() + "\n"); 
					myWriter.write(((Patient)users.get(i)).getPharmacy().getStreetAddress() + "\n");
					myWriter.write(((Patient)users.get(i)).getPharmacy().getCity() + "\n");
					myWriter.write(((Patient)users.get(i)).getPharmacy().getState() + "\n");
					myWriter.write(((Patient)users.get(i)).getPharmacy().getZip() + "\n");
					myWriter.write(((Patient)users.get(i)).getInsurance().getName() + "\n");
					myWriter.write(((Patient)users.get(i)).getInsurance().getInsurancePolicyId() + "\n");
					myWriter.write(((Patient)users.get(i)).getInsurance().getPhoneNumber() + "\n");
				} else if (users.get(i).getUserType() == UserType.NURSE) {
					// no additional fields
				} else if (users.get(i).getUserType() == UserType.DOCTOR) {
					myWriter.write(((Doctor)users.get(i)).getSpecialty() + "\n"); 
				} 				
			}  
			
			// print all messages
			myWriter.write("*Messages\n");
			for (int i = 0; i < messages.size(); i++) { 		      
				myWriter.write("*NewMessage\n"); 				
				myWriter.write(messages.get(i).getSenderUniqueID() + "\n"); 	
				myWriter.write(messages.get(i).getReceiverUniqueID() + "\n"); 
				if (messages.get(i).getRecipient() == UserType.NURSE) {
					myWriter.write("NURSE\n"); 
				} else if (messages.get(i).getRecipient() == UserType.DOCTOR) {
					myWriter.write("DOCTOR\n"); 
				} 
				myWriter.write(messages.get(i).getSubject() + "\n"); 	
				myWriter.write(messages.get(i).getBody() + "\n"); 	
				if (messages.get(i).getReply() == null) {
					myWriter.write("null" + "\n");
				} else {
					myWriter.write(messages.get(i).getReply() + "\n"); 	
				}
				myWriter.write(messages.get(i).getSentDate_String() + "\n"); 	
				if (messages.get(i).getReply() == null) {
					myWriter.write("null" + "\n");
				} else {
					myWriter.write(messages.get(i).getReplyDate_String() + "\n"); 
				}
			}
			
			// print all appointments
			myWriter.write("*Appointments\n");
			VisitDetails visitDetails; Vitals vitals;
			for (int i = 0; i < appointments.size(); i++) { 		      
				myWriter.write("*NewAppointment\n"); 				
				myWriter.write(appointments.get(i).getUniqueID() + "\n"); 	
				myWriter.write(appointments.get(i).getPatientUniqueID() + "\n"); 	
				myWriter.write(appointments.get(i).getDoctorUniqueID() + "\n"); 	
				myWriter.write(appointments.get(i).getReason() + "\n"); 	
				myWriter.write(appointments.get(i).getApptDate_String() + "\n"); 	
				visitDetails = appointments.get(i).getVisitDetails();
				if (visitDetails == null) {
					myWriter.write("null" + "\n"); 	
					myWriter.write("null" + "\n"); 	
					myWriter.write("null" + "\n"); 	
					myWriter.write("null" + "\n"); 	
					myWriter.write("null" + "\n"); 	
					myWriter.write("null" + "\n"); 	
					myWriter.write("null" + "\n"); 	
					myWriter.write("null" + "\n"); 
				} else {
					myWriter.write(visitDetails.getHealthConcerns() + "\n"); 	
					myWriter.write(visitDetails.getPhysicalHealthFindings() + "\n"); 	
					myWriter.write(visitDetails.getPrescriptions() + "\n"); 	
					vitals = visitDetails.getVitals();
					myWriter.write(vitals.getBPDiastolic() + "\n"); 	
					myWriter.write(vitals.getBPSystolic() + "\n"); 	
					myWriter.write(vitals.getHeight() + "\n"); 	
					myWriter.write(vitals.getTemperature() + "\n"); 	
					myWriter.write(vitals.getWeight() + "\n"); 
				}
			}  
			
			myWriter.close();
			System.out.println("File closed, print complete");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}
	
	private int getMessageIndex(String uniqueID) {
		System.out.println("ITService - getMessageIndex - input Message UID: " + uniqueID);
		
		// loop thro' all messages to check for match on unique ID
		for (int i = 0; i < messages.size(); i++) {
			System.out.println("ITService - getMessageIndex - index = " + i + " , UID = " + messages.get(i).getMessageUniqueID());			
			if (messages.get(i).getMessageUniqueID().equalsIgnoreCase(uniqueID)) {
				// message found, return index
				return i;
			}
		}
		
		// user not found
		return -1;
	}
	
	private int getAppointmentIndex(String uniqueID) {
		System.out.println("ITService - getAppointmentIndex - input Appt UID: " + uniqueID);
		
		// loop thro' all appointments to check for match on unique ID
		for (int i = 0; i < appointments.size(); i++) {
			System.out.println("ITService - getAppointmentIndex - index = " + i + " , UID = " + appointments.get(i).getUniqueID());			
			if (appointments.get(i).getUniqueID().equalsIgnoreCase(uniqueID)) {
				// appointment found, return index
				return i;
			}
		}
		
		// user not found
		return -1;
	}

	private int getUserIndex(String uniqueID) {
		// loop thro' all users to check for match on unique ID
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUniqueID().equalsIgnoreCase(uniqueID)) {
				// user found, return index
				return i;
			}
		}
		
		// user not found
		return -1;
	}

	public Boolean createUser(User user) {
		// check if user already exists, return false if so
		if (getUserIndex(user.getUniqueID()) != -1) {
			// user already exists
			return false;
		}
		
		// user does not already exist, add now and return true
		users.add(user);
		return true;
	}
	
	public String getUserFullNameFromUniqueID(String uniqueID) {
		int i = getUserIndex(uniqueID);
		if (i == -1) {
			// can't find user
			return null;
		} else {
			return users.get(i).getFirstName() + " " + users.get(i).getLastName();
		}
	}
	
	public String getDoctorSpecialtyFromUniqueID(String uniqueID) {
		int i = getUserIndex(uniqueID);
		if (i == -1) {
			// can't find user
			return null;
		} else {
			return ((Doctor)users.get(i)).getSpecialty();
		}
	}	
	
	public User signIn(Credentials credentials) {
		int i = getUserIndex(credentials.getUniqueID());
		if (i == -1) {
			// can't find user
			return null;
		} else {
			if (users.get(i).getPassword().equals(credentials.getPassword())) {
				return users.get(i);
			} else {
				return null;
			}
		}
	}
	
	public Appointment checkForUpcomingAppt(String userUniqueID) {
		int i = getUserIndex(userUniqueID);
		if (i == -1) {
			// can't find user
			return null;
		} else {
			// loop thro' all appointments for user, check if any are in the future
			ArrayList<Appointment> userAppointments = getAppointmentsForUser(users.get(i));
			
			if (userAppointments == null) {
				return null;
			} else {
				for (int j = 0; j < userAppointments.size(); j++) {
					if (userAppointments.get(j).checkIfUpcoming()) {
						// upcoming appointment, return appointment unique identifier
						return userAppointments.get(j);
					}
				}
			}
		}
		
		return null;
	}
	
	 
	
	public ArrayList<String> getDoctorsForListView(ArrayList<User> doctors) {
		ArrayList<String> doctorsForListView = new ArrayList<String>();
		
		// loop thro' all doctors in list, add first name, last name, and specialty to array list
		for (int i = 0; i < doctors.size(); i++) {
			// doctorsForListView.add(doctors.get(i).getFirstName() + " " + doctors.get(i).getLastName() + " (" + ((Doctor)doctors.get(i)).getSpecialty() + ")");
			doctorsForListView.add(doctors.get(i).toString());
		}
		
		return doctorsForListView;
	}
	
	public void changeDoctor(String userUniqueID, String newDoctorUniqueID) {
		int i = getUserIndex(userUniqueID);
		if (i == -1) {
			// can't find user
		} else {
			((Patient)users.get(i)).changeDoctorUniqueID(newDoctorUniqueID);
		}
	}
	
	public void updateUserInfo(String originalUniqueID, String firstName, String lastName, LocalDate birthday, String uniqueID, String phoneNumber, String email) {
		// use originalUniqueID in case any of the first name, last name, or birthdate were changed
		int i = getUserIndex(originalUniqueID);
		if (i == -1) {
			// can't find user
		} else {
			// found user, update info
			users.get(i).setFirstName(firstName);
			users.get(i).setLastName(lastName);
			users.get(i).setDOB(birthday);
			users.get(i).setPhoneNumber(phoneNumber);
			users.get(i).setEmail(email);
			users.get(i).setUniqueID(uniqueID);
		}
		
		// check if Unique ID was updated, if so, need to propagate that change throughout the database
		if (originalUniqueID.equals(uniqueID)) {
			// unique ID not updated, do nothing
		} else {
			System.out.println("ITService - updateUserInfo - unique ID changed");
			System.out.println("Original Unique ID: " + originalUniqueID);
			System.out.println("Updated  Unique ID: " + uniqueID);
			System.out.println("ITService - updateUserInfo - updating the database with new unique ID");
			
			// update appointments
			for (i = 0; i < appointments.size(); i++) {
				if (appointments.get(i).getDoctorUniqueID().equals(originalUniqueID)) {
					System.out.println("ITService - updateUserInfo - updating appointment with new unique ID");
					appointments.get(i).setDoctorUniqueID(uniqueID);
				} else if (appointments.get(i).getPatientUniqueID().equals(originalUniqueID)) {
					System.out.println("ITService - updateUserInfo - updating appointment with new unique ID");
					appointments.get(i).setPatientUniqueID(uniqueID);
				}
			}
			
			// update messages
			for (i = 0; i < messages.size(); i++) {
				if (messages.get(i).getReceiverUniqueID().equals(originalUniqueID)) {
					System.out.println("ITService - updateUserInfo - updating message with new unique ID");
					messages.get(i).setReceiverUniqueID(uniqueID);
				} else if (messages.get(i).getSenderUniqueID().equals(originalUniqueID)) {
					System.out.println("ITService - updateUserInfo - updating message with new unique ID");
					messages.get(i).setSenderUniqueID(uniqueID);
				}
			}
		}
	}
	
	public void updatePharmacyInfo(String originalUniqueID, String pharmacyName, String pharmacyStreetAddress, String pharmacyCity, String pharmacyState, int pharmacyZip) {
		// use originalUniqueID in case any of the first name, last name, or birthdate were changed
		int i = getUserIndex(originalUniqueID);
		if (i == -1) {
			// can't find user
		} else {
			// found user, update info
			((Patient)users.get(i)).setPharmacy(pharmacyName, pharmacyStreetAddress, pharmacyCity, pharmacyState, pharmacyZip);
		}
	}
	
	public void updateInsuranceInfo(String originalUniqueID, String insuranceCompanyName, String insurancePolicyID, String insurancePhoneNumber) {
		// use originalUniqueID in case any of the first name, last name, or birthdate were changed
		int i = getUserIndex(originalUniqueID);
		if (i == -1) {
			// can't find user
		} else {
			// found user, update info
			((Patient)users.get(i)).setInsurance(insuranceCompanyName, insurancePolicyID, insurancePhoneNumber);
		}
	}
	
	public ArrayList<User> getDoctors() {
		ArrayList<User> userList = new ArrayList<User>();

		// loop thro' all users, check for doctors, add to array list
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserType() == UserType.DOCTOR) {
				// doctor, add to array list
				userList.add(users.get(i));
			}
		}
		
		Sorts.sortUsers(userList, new UserComparator());
		
		return userList;
	}

	public ArrayList<User> getNurses() {
		ArrayList<User> userList = new ArrayList<User>();

		// loop thro' all users, check for nurses, add to array list
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserType() == UserType.NURSE) {
				// nurse, add to array list
				userList.add(users.get(i));
			}
		}
		
		Sorts.sortUsers(userList, new UserComparator());
		
		return userList;
	}

	public ArrayList<User> getPatients() {
		ArrayList<User> userList = new ArrayList<User>();

		// loop thro' all users, check for patients, add to array list
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserType() == UserType.PATIENT) {
				// patient, add to array list
				userList.add(users.get(i));
			}
		}
		
		Sorts.sortUsers(userList, new UserComparator());
		
		return userList;
	}

	public void newMessage(Message message) {
		messages.add(message);
	}

	public Boolean deleteMessage(String messageUniqueID) {
		System.out.println("ITService - Delete Message - input Message UID: " + messageUniqueID); 
		int i = getMessageIndex(messageUniqueID);
		if (i == -1) {
			// can't find message
			return false;
		} else {
			// found message, delete
			messages.remove(i);
			return true;
		}
		
	}
	
	public ArrayList<Message> getMessages() 
	{
		return messages;
	}

	public ArrayList<Appointment> getAppointmentsForUser(String userUniqueID) {
		int i = getUserIndex(userUniqueID);
		if (i == -1) {
			// can't find user
			return null;
		} else {
			return getAppointmentsForUser(users.get(i));
		}
	}
	
	public ArrayList<Message> getMessagesForUser(User inputUser) {
		ArrayList<Message> messagesForUser = new ArrayList<Message>();
		Boolean addMsg;
		
		// loop thro' all messages check for match on unique ID
		for (int i = 0; i < messages.size(); i++) {
			addMsg = false;
			if (inputUser.getUserType() == UserType.PATIENT) {
				// return messages that match patient unique ID of user
				if (messages.get(i).getSenderUniqueID().equalsIgnoreCase(inputUser.getUniqueID())) {
					addMsg = true;
				}
			} else if (inputUser.getUserType() == UserType.DOCTOR) {
				// return messages that match recipient for this Doctor (user) if the recipient user type is Doctor
				if (messages.get(i).getRecipient() == UserType.DOCTOR) {
					if (messages.get(i).getReceiverUniqueID().equalsIgnoreCase(inputUser.getUniqueID())) {
						addMsg = true;
					}
				}
			} else if (inputUser.getUserType() == UserType.NURSE) {
				// return all messages for nurses
				if (messages.get(i).getRecipient() == UserType.NURSE) {
					addMsg = true;
				}
			}
			
			if (addMsg) {
				messagesForUser.add(messages.get(i));
			}
		}
		
		// sort by/order by date, descending
		Sorts.sortMessages(messagesForUser, new MessageComparator());
		
		return messagesForUser;
	}
	
	public ArrayList<Appointment> getAppointmentsForUser(User inputUser) {
		ArrayList<Appointment> appointmentsForUser = new ArrayList<Appointment>();
		Boolean addAppt;
		
		// loop thro' all appointments check for match on unique ID
		for (int i = 0; i < appointments.size(); i++) {
			addAppt = false;
			if (inputUser.getUserType() == UserType.PATIENT) {
				// return appointments that match patient unique ID of user
				if (appointments.get(i).getPatientUniqueID().equalsIgnoreCase(inputUser.getUniqueID())) {
					addAppt = true;
				}
			} else if (inputUser.getUserType() == UserType.DOCTOR) {
				// return appointments that match Doctor unique ID of user
				if (appointments.get(i).getDoctorUniqueID().equalsIgnoreCase(inputUser.getUniqueID())) {
					addAppt = true;
				}
			} else if (inputUser.getUserType() == UserType.NURSE) {
				// return all appointments for nurses
				addAppt = true;
			}
			
			if (addAppt) {
				appointmentsForUser.add(appointments.get(i));
			}
		}
		
		// sort by/order by date, descending
		Sorts.sortAppointments(appointmentsForUser, new AppointmentComparator());
		
		return appointmentsForUser;
	}

	public boolean scheduleAppointment(Appointment appointment) {
		appointments.add(appointment);
		
		return true;
	}

	public boolean cancelAppointment(Appointment appointment) {
		System.out.println("ITService - Cancel Appointment - input Appt UID: " + appointment.getUniqueID()); 
		int i = getAppointmentIndex(appointment.getUniqueID());
		if (i == -1) {
			// can't find appointment
			return false;
		} else {
			// found appointment, delete
			appointments.remove(i);
			return true;
		}
	}
	
	private Boolean checkForAppointmentsOnDate(String doctorUniqueID, Date date) {
		ArrayList<Appointment> appointmentsForUser = getAppointmentsForUser(doctorUniqueID);
		
		if (appointmentsForUser == null) {
			// no appointments found, none on this date
			return true;
		} else {
			// loop thro' appointments to ensure none on this date
			for (int i = 0; i < appointmentsForUser.size(); i++) {
				if (isSameDay(appointmentsForUser.get(i).getApptDate(), date)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private static boolean isSameDay(Date date1, Date date2) {
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
	    return fmt.format(date1).equals(fmt.format(date2));
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<String> getUpcomingAvailableAppointmentsForDoctor(String doctorUniqueID) {
		System.out.println("ITService - getUpcomingAvailableAppointmentsForDoctor - input Doctor UID: " + doctorUniqueID); 
		
		ArrayList<String> upcomingAvailableAppointmentsForDoctor = new ArrayList<String>();
		
		// assumption - only 1 appointment can be scheduled per day for a doctor
		
		// display hours 9:00 - 15:00 for possible selections
		// on days where the doctor does not already
		// have an appointment in the next 3 weeks
		Boolean includeDay;
		Date startDate = new Date();
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar cal = Calendar.getInstance();
        int count = 0; int hour; int day;
		Date date = start.getTime();
		while (count < 21) {
			count = count + 1;
			start.add(Calendar.DATE, 1);
			date = start.getTime();
			// System.out.println("ITService - getUpcomingAvailableAppointmentsForDoctor - date: " + date.toString());
		    
			// check to see if this day should be included 
			// do not include days where the doctor has other appointments
			includeDay = checkForAppointmentsOnDate(doctorUniqueID, date);
			
			// do not include Saturday and Sunday
			cal.setTime(date); 
	        day = cal.get(Calendar.DAY_OF_WEEK);
	        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
	        	includeDay = false;
	        }
	        
			if (includeDay) {
			    // loop thro' 9 to 15 hours
			    hour = 8;
			    while (hour < 15) {
			    	hour = hour + 1;
			    	// System.out.println("ITService - getUpcomingAvailableAppointmentsForDoctor - hour: " + hour);
			    	date.setHours(hour);
			    	date.setMinutes(0);
			    	date.setSeconds(0);
			    	// System.out.println("ITService - getUpcomingAvailableAppointmentsForDoctor - date: " + date.toString());
	
			    	upcomingAvailableAppointmentsForDoctor.add(date.toString());
			    }
			}
		}
		
		return upcomingAvailableAppointmentsForDoctor;
	}
	
	public ArrayList<Appointment> getAppointments()
	{
		return appointments;
	}
	
	void createDataAndPrintToFile() {

		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		// users
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		
		// ----------------------------------------------------------------
		// Nurses
		// ----------------------------------------------------------------
		Nurse nurse1 = new Nurse();
		nurse1.setUserType(UserType.NURSE);
		nurse1.setFirstName("Jane");
		nurse1.setLastName("Doe");
		nurse1.setPhoneNumber("5555555551");
		nurse1.setDOB(LocalDate.of(1990, 1, 1));
		nurse1.setEmail("jane@doe.com");
		Credentials nurse1Credentials = new Credentials();
		nurse1Credentials.setUniqueID(nurse1.getFirstName() + nurse1.getLastName() + nurse1.getDOB().toString());
		nurse1Credentials.setPassword("password1");
		nurse1.setCredentials(nurse1Credentials);
		users.add(nurse1);
		
		Nurse nurse2 = new Nurse();
		nurse2.setUserType(UserType.NURSE);
		nurse2.setFirstName("Gail");
		nurse2.setLastName("Smith");
		nurse2.setPhoneNumber("5555555552");
		nurse2.setDOB(LocalDate.of(1990, 1, 2));
		nurse2.setEmail("gail@smith.com");
		Credentials nurse2Credentials = new Credentials();
		nurse2Credentials.setUniqueID(nurse2.getFirstName() + nurse2.getLastName() + nurse2.getDOB().toString());
		nurse2Credentials.setPassword("password2");
		nurse2.setCredentials(nurse2Credentials);
		users.add(nurse2);
		
		Nurse nurse3 = new Nurse();
		nurse3.setUserType(UserType.NURSE);
		nurse3.setFirstName("Tara");
		nurse3.setLastName("Carter");
		nurse3.setPhoneNumber("5555555553");
		nurse3.setDOB(LocalDate.of(1990, 1, 3));
		nurse3.setEmail("tara@carter.com");
		Credentials nurse3Credentials = new Credentials();
		nurse3Credentials.setUniqueID(nurse3.getFirstName() + nurse3.getLastName() + nurse3.getDOB().toString());
		nurse3Credentials.setPassword("password3");
		nurse3.setCredentials(nurse3Credentials);
		users.add(nurse3);
		
		// ----------------------------------------------------------------
		// Doctors
		// ----------------------------------------------------------------
		Doctor doctor1 = new Doctor();
		doctor1.setUserType(UserType.DOCTOR);
		doctor1.setFirstName("Jim");
		doctor1.setLastName("Fitzgerald");
		doctor1.setPhoneNumber("5555555554");
		doctor1.setDOB(LocalDate.of(1990, 1, 4));
		doctor1.setEmail("jim@fitzgerald.com");
		Credentials doctor1Credentials = new Credentials();
		doctor1Credentials.setUniqueID(doctor1.getFirstName() + doctor1.getLastName() + doctor1.getDOB().toString());
		doctor1Credentials.setPassword("password4");
		doctor1.setCredentials(doctor1Credentials);
		doctor1.setSpecialty("Anesthesiology");
		users.add(doctor1);
		
		Doctor doctor2 = new Doctor();
		doctor2.setUserType(UserType.DOCTOR);
		doctor2.setFirstName("Eloisa");
		doctor2.setLastName("Germaine");
		doctor2.setPhoneNumber("5555555555");
		doctor2.setDOB(LocalDate.of(1990, 1, 5));
		doctor2.setEmail("eloisa@germaine.com");
		Credentials doctor2Credentials = new Credentials();
		doctor2Credentials.setUniqueID(doctor2.getFirstName() + doctor2.getLastName() + doctor2.getDOB().toString());
		doctor2Credentials.setPassword("password5");
		doctor2.setCredentials(doctor2Credentials);
		doctor2.setSpecialty("Dermatology");
		users.add(doctor2);

		Doctor doctor3 = new Doctor();
		doctor3.setUserType(UserType.DOCTOR);
		doctor3.setFirstName("Lisa");
		doctor3.setLastName("Murray");
		doctor3.setPhoneNumber("5555555556");
		doctor3.setDOB(LocalDate.of(1990, 1, 6));
		doctor3.setEmail("lisa@murray.com");
		Credentials doctor3Credentials = new Credentials();
		doctor3Credentials.setUniqueID(doctor3.getFirstName() + doctor3.getLastName() + doctor3.getDOB().toString());
		doctor3Credentials.setPassword("password6");
		doctor3.setCredentials(doctor3Credentials);
		doctor3.setSpecialty("Family Medicine");
		users.add(doctor3);

		Doctor doctor4 = new Doctor();
		doctor4.setUserType(UserType.DOCTOR);
		doctor4.setFirstName("Jeremy");
		doctor4.setLastName("Garfield");
		doctor4.setPhoneNumber("5555555557");
		doctor4.setDOB(LocalDate.of(1990, 1, 7));
		doctor4.setEmail("jeremy@garfield.com");
		Credentials doctor4Credentials = new Credentials();
		doctor4Credentials.setUniqueID(doctor4.getFirstName() + doctor4.getLastName() + doctor4.getDOB().toString());
		doctor4Credentials.setPassword("password7");
		doctor4.setCredentials(doctor4Credentials);
		doctor4.setSpecialty("Internal Medicine");
		users.add(doctor4);		
		

		// ----------------------------------------------------------------
		// Patients
		// ----------------------------------------------------------------
		Patient patient1 = new Patient();
		patient1.setUserType(UserType.PATIENT);
		patient1.setFirstName("Bob");
		patient1.setLastName("Fischer");
		patient1.setPhoneNumber("5555555558");
		patient1.setDOB(LocalDate.of(1990, 1, 8));
		patient1.setEmail("bob@fischer.com");
		Credentials patient1Credentials = new Credentials();
		patient1Credentials.setUniqueID(patient1.getFirstName() + patient1.getLastName() + patient1.getDOB().toString());
		patient1Credentials.setPassword("password8");
		patient1.setCredentials(patient1Credentials);
		patient1.setKnownAllergies("Peanuts");
		patient1.setPreviousHealthIssues("Asthma");
		patient1.setImmunizationHistory("Polio Vaccine");
		patient1.changeDoctorUniqueID(doctor4.getUniqueID());
		Pharmacy patient1Pharmacy = new Pharmacy();
		patient1Pharmacy.setName("Walgreens");
		patient1Pharmacy.setStreetAddress("2000 S Mill Ave");
		patient1Pharmacy.setCity("Tempe");
		patient1Pharmacy.setState("AZ");
		patient1Pharmacy.setZip(85282);
		patient1.setPharmacy(patient1Pharmacy);
		Insurance patient1Insurance = new Insurance();
		patient1Insurance.setName("Blue Cross Blue Shield");
		patient1Insurance.setInsurancePolicyId("1234");
		patient1Insurance.setPhoneNumber("18886302583");
		patient1.setInsurance(patient1Insurance);		
		users.add(patient1);

		Patient patient2 = new Patient();
		patient2.setUserType(UserType.PATIENT);
		patient2.setFirstName("Jerry");
		patient2.setLastName("Lewis");
		patient2.setPhoneNumber("5555555559");
		patient2.setDOB(LocalDate.of(1990, 1, 9));
		patient2.setEmail("jerry@lewis.com");
		Credentials patient2Credentials = new Credentials();
		patient2Credentials.setUniqueID(patient2.getFirstName() + patient2.getLastName() + patient2.getDOB().toString());
		patient2Credentials.setPassword("password9");
		patient2.setCredentials(patient2Credentials);
		patient2.setKnownAllergies("Wheat");
		patient2.setPreviousHealthIssues("Diabetes");
		patient2.setImmunizationHistory("Flu Vaccine");
		patient2.changeDoctorUniqueID(doctor3.getUniqueID());
		Pharmacy patient2Pharmacy = new Pharmacy();
		patient2Pharmacy.setName("CVS");
		patient2Pharmacy.setStreetAddress("3303 S Rural Rd");
		patient2Pharmacy.setCity("Tempe");
		patient2Pharmacy.setState("AZ");
		patient2Pharmacy.setZip(85282);
		patient2.setPharmacy(patient2Pharmacy);
		Insurance patient2Insurance = new Insurance();
		patient2Insurance.setName("Aetna");
		patient2Insurance.setInsurancePolicyId("12345");
		patient2Insurance.setPhoneNumber("18008723862");
		patient2.setInsurance(patient2Insurance);		
		users.add(patient2);

		Patient patient3 = new Patient();
		patient3.setUserType(UserType.PATIENT);
		patient3.setFirstName("Carrie");
		patient3.setLastName("Rodriguez");
		patient3.setPhoneNumber("5555555510");
		patient3.setDOB(LocalDate.of(1990, 1, 10));
		patient3.setEmail("carrie@rodriguez.com");
		Credentials patient3Credentials = new Credentials();
		patient3Credentials.setUniqueID(patient3.getFirstName() + patient3.getLastName() + patient3.getDOB().toString());
		patient3Credentials.setPassword("password10");
		patient3.setCredentials(patient3Credentials);
		patient3.setKnownAllergies("Eggs");
		patient3.setPreviousHealthIssues("Substance Abuse");
		patient3.setImmunizationHistory("COVID-19 Vaccine");
		patient3.changeDoctorUniqueID(doctor2.getUniqueID());
		Pharmacy patient3Pharmacy = new Pharmacy();
		patient3Pharmacy.setName("Fry's");
		patient3Pharmacy.setStreetAddress("3232 S Mill Ave");
		patient3Pharmacy.setCity("Tempe");
		patient3Pharmacy.setState("AZ");
		patient3Pharmacy.setZip(85282);
		patient3.setPharmacy(patient3Pharmacy);
		Insurance patient3Insurance = new Insurance();
		patient3Insurance.setName("Cigna");
		patient3Insurance.setInsurancePolicyId("123456");
		patient3Insurance.setPhoneNumber("18009971654");
		patient3.setInsurance(patient3Insurance);		
		users.add(patient3);

		Patient patient4 = new Patient();
		patient4.setUserType(UserType.PATIENT);
		patient4.setFirstName("Alexander");
		patient4.setLastName("Winter");
		patient4.setPhoneNumber("4805555556");
		patient4.setDOB(LocalDate.of(1990, 1, 26));
		patient4.setEmail("alex@winter.com");
		Credentials patient4Credentials = new Credentials();
		patient4Credentials.setUniqueID(patient4.getFirstName() + patient4.getLastName() + patient4.getDOB().toString());
		patient4Credentials.setPassword("password101");
		patient4.setCredentials(patient4Credentials);
		patient4.setKnownAllergies("Grass");
		patient4.setPreviousHealthIssues("Weight");
		patient4.setImmunizationHistory("None");
		patient4.changeDoctorUniqueID(doctor2.getUniqueID());
		Pharmacy patient4Pharmacy = new Pharmacy();
		patient4Pharmacy.setName("Safeway");
		patient4Pharmacy.setStreetAddress("926 E Broadway Rd");
		patient4Pharmacy.setCity("Tempe");
		patient4Pharmacy.setState("AZ");
		patient4Pharmacy.setZip(85282);
		patient4.setPharmacy(patient4Pharmacy);
		Insurance patient4Insurance = new Insurance();
		patient4Insurance.setName("GEICO");
		patient4Insurance.setInsurancePolicyId("123456789");
		patient4Insurance.setPhoneNumber("4808278500");
		patient4.setInsurance(patient4Insurance);		
		users.add(patient4);
		
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		// appointments
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		Appointment appointment1 = new Appointment();
		VisitDetails visitDetails1 = new VisitDetails();
		Vitals vitals1 = new Vitals();
		Date d;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-10-29 08:00:00");
			appointment1.setApptDate(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		appointment1.setPatientUniqueID(patient4.getUniqueID());
		appointment1.setDoctorUniqueID(doctor4.getUniqueID());
		appointment1.setReason("Flu like symptoms");
		visitDetails1.setPhysicalHealthFindings("Congestion");
		visitDetails1.setPrescriptions("Z-pac");
		vitals1.setBPDiastolic(100);
		vitals1.setBPSystolic(70);
		vitals1.setHeight(68);
		vitals1.setTemperature(98);
		vitals1.setWeight(150);
		appointment1.setUniqueID(appointment1.getApptDate().toString() + " " + appointment1.getPatientUniqueID() + " " + appointment1.getDoctorUniqueID());
		visitDetails1.setHealthConcerns(appointment1.getReason());
		visitDetails1.setVitals(vitals1);
		appointment1.setVisitDetails(visitDetails1);
		appointments.add(appointment1);
		
		Appointment appointment2 = new Appointment();
		VisitDetails visitDetails2 = new VisitDetails();
		Vitals vitals2 = new Vitals();
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-10-28 15:00:00");
			appointment2.setApptDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		appointment2.setPatientUniqueID(patient4.getUniqueID());
		appointment2.setDoctorUniqueID(doctor3.getUniqueID());
		appointment2.setReason("Leg pain");
		visitDetails2.setPhysicalHealthFindings("Back nerve damage");
		visitDetails2.setPrescriptions("None");
		vitals2.setBPDiastolic(101);
		vitals2.setBPSystolic(71);
		vitals2.setHeight(68);
		vitals2.setTemperature(99);
		vitals2.setWeight(152);
		appointment2.setUniqueID(appointment2.getApptDate().toString() + " " + appointment2.getPatientUniqueID() + " " + appointment2.getDoctorUniqueID());
		visitDetails2.setHealthConcerns(appointment2.getReason());
		visitDetails2.setVitals(vitals2);
		appointment2.setVisitDetails(visitDetails2);
		appointments.add(appointment2);

		Appointment appointment3 = new Appointment();
		VisitDetails visitDetails3 = new VisitDetails();
		Vitals vitals3 = new Vitals();
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-11-05 15:00:00");
			appointment3.setApptDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		appointment3.setPatientUniqueID(patient2.getUniqueID());
		appointment3.setDoctorUniqueID(doctor2.getUniqueID());
		appointment3.setReason("Sore throat");
		visitDetails3.setPhysicalHealthFindings("Flu");
		visitDetails3.setPrescriptions("Steroid");
		vitals3.setBPDiastolic(110);
		vitals3.setBPSystolic(75);
		vitals3.setHeight(72);
		vitals3.setTemperature(100);
		vitals3.setWeight(165);
		appointment3.setUniqueID(appointment3.getApptDate().toString() + " " + appointment3.getPatientUniqueID() + " " + appointment3.getDoctorUniqueID());
		visitDetails3.setHealthConcerns(appointment3.getReason());
		visitDetails3.setVitals(vitals3);
		appointment3.setVisitDetails(visitDetails3);
		appointments.add(appointment3);
		
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		// messages
		// ----------------------------------------------------------------
		// ----------------------------------------------------------------
		Message message1 = new Message();
		message1.setSenderUniqueID(patient3.getUniqueID());
		// message1.setReceiverUniqueID(null);
		message1.setRecipient(UserType.NURSE);
		message1.setSubject("Sore Throat Question");
		message1.setBody("My throat is sore, should I make an appointment to come in and see the doctor?");
		try {
			Date sentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-10-28 15:30:00");
			message1.setSentDate(sentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		message1.setReply("Not if that is your only symptom, get rest and reach back out if the symptoms worsen.");
		try {
			Date replyDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-10-29 09:45:00");
			message1.setReplyDate(replyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		messages.add(message1);		

		Message message2 = new Message();
		message2.setSenderUniqueID(patient4.getUniqueID());
		// message2.setReceiverUniqueID(null);
		message2.setRecipient(UserType.NURSE);
		message2.setSubject("Temperature Question");
		message2.setBody("How high does my temperature need to be to consider coming in for an appointment?");
		try {
			Date sentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-10-28 15:45:00");
			message2.setSentDate(sentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		message2.setReply("Generally if it is above 100F and Tylenol does not help, we'd like for you to come in.");
		try {
			Date replyDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-10-29 10:45:00");
			message2.setReplyDate(replyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		messages.add(message2);

		Message message3 = new Message();
		message3.setSenderUniqueID(patient4.getUniqueID());
		message3.setReceiverUniqueID(doctor2.getUniqueID());
		message3.setRecipient(UserType.DOCTOR);
		message3.setSubject("Follow-up Question");
		message3.setBody("I forgot to ask about pain in my left arm, should I see a specialist?");
		try {
			Date sentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-10-29 15:45:00");
			message3.setSentDate(sentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		message3.setReply("If the pain is severe, you should see a specialist.");
		try {
			Date replyDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-10-31 10:45:00");
			message3.setReplyDate(replyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		messages.add(message3);
		
		printToFile();
	}
}