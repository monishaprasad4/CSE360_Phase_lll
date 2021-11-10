import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ui_Patient_AccountController {

	private Stage primaryStage;
	
	@FXML private Button updateInformation;
	@FXML private MenuBar menuBar;
	@FXML private TextField firstNameTextField;
	@FXML private TextField lastNameTextField;
	@FXML private DatePicker birthdayDatePicker;
	@FXML private Label labelNotification;
	@FXML private TextField phoneNumberTextField;
	@FXML private TextField emailTextField;
	@FXML private TextField insuranceCompanyNameTextField;
	@FXML private TextField insurancePolicyIDTextField;
	@FXML private TextField insurancePhoneNumberTextField;
	@FXML private TextField pharmacyNameTextField;
	@FXML private TextField pharmacyStreetAddressTextField;
	@FXML private TextField pharmacyCityTextField;
	@FXML private TextField pharmacyStateTextField;
	@FXML private TextField pharmacyZipTextField;
	@FXML private Menu menuBar_Account;
	@FXML private Menu menuBar_VisitHistory;
	@FXML private Menu menuBar_Messages;
	@FXML private Menu menuBar_ScheduleAppt;
	@FXML private Menu menuBar_Doctors;

	private ITService currentITService;
	private User currentUser;
	private String originalUniqueID;
	private ColorPicker labelNotificationColor;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Patient_AccountController() {
    	
    }    
    
    @SuppressWarnings("deprecation")
	@FXML
    private void initialize() {
    	
    	Label label = new Label("Account");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_Account();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_Account.setGraphic(label);
    	
    	label = new Label("Visit History");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_VisitHistory();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_VisitHistory.setGraphic(label);
    	
    	label = new Label("Messages");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_Messages();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});

    	menuBar_Messages.setGraphic(label);    	

    	label = new Label("Schedule Appt");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
					menuBarClick_ScheduleAppt();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_ScheduleAppt.setGraphic(label);

    	label = new Label("Doctors");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
					menuBarClick_Doctors();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_Doctors.setGraphic(label);
   }   	
    
    public void initializeController(Stage stage, User currentUser, ITService currentITService) {
    	this.primaryStage = stage;
    	this.currentUser = currentUser;
    	this.currentITService = currentITService;
    	
    	primaryStage.setOnCloseRequest(event -> {
    	    System.out.println("Closing, print updated information to database (file)");
    	    currentITService.printToFile();
    	});
    	
    	// store original unique ID in case any of those fields change, need to be able to locate the user
    	originalUniqueID = currentUser.getUniqueID();
    	    	
    	// prefill patient information text fields that are already filled out
    	if (currentUser.getFirstName() != null) {
    		firstNameTextField.setText(currentUser.getFirstName());
    	}
    	if (currentUser.getLastName() != null) {
    		lastNameTextField.setText(currentUser.getLastName());
    	}
    	if (currentUser.getDOB() != null) {
    		birthdayDatePicker.setValue(currentUser.getDOB());
    	}
    	if (currentUser.getPhoneNumber() != null) {
    		phoneNumberTextField.setText(currentUser.getPhoneNumber());
    	}
    	if (currentUser.getEmail() != null) {
    		emailTextField.setText(currentUser.getEmail());
    	}
    	if (((Patient)currentUser).getInsurance_Name() != null) {
    		insuranceCompanyNameTextField.setText(((Patient)currentUser).getInsurance_Name());
    	}
    	if (((Patient)currentUser).getInsurance_InsurancePolicyId() != null) {
    		insurancePolicyIDTextField.setText(((Patient)currentUser).getInsurance_InsurancePolicyId());
    	}
    	if (((Patient)currentUser).getInsurance_PhoneNumber() != null) {
    		insurancePhoneNumberTextField.setText(((Patient)currentUser).getInsurance_PhoneNumber());
    	}
    	if (((Patient)currentUser).getPharmacy_Name() != null) {
    		pharmacyNameTextField.setText(((Patient)currentUser).getPharmacy_Name());
    	}
    	if (((Patient)currentUser).getPharmacy_StreetAddress() != null) {
    		pharmacyStreetAddressTextField.setText(((Patient)currentUser).getPharmacy_StreetAddress());
    	}
    	if (((Patient)currentUser).getPharmacy_City() != null) {
    		pharmacyCityTextField.setText(((Patient)currentUser).getPharmacy_City());
    	}
    	if (((Patient)currentUser).getPharmacy_State() != null) {
    		pharmacyStateTextField.setText(((Patient)currentUser).getPharmacy_State());
    	}
    	if (((Patient)currentUser).getPharmacy_Zip_String() != null && ((Patient)currentUser).getPharmacy_Zip() != 0) {
    		pharmacyZipTextField.setText(((Patient)currentUser).getPharmacy_Zip_String());
    	}
    }
    
    @FXML
    private void menuBarClick_Account() throws Exception {
    	System.out.println("Patient Account - Menu Bar Click - Account");
    	
    	// do nothing, already here
    }
    
    @FXML
    private void menuBarClick_VisitHistory() throws Exception {
    	System.out.println("Patient Account - Menu Bar Click - Visit History");

    	// switch UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Patient_Visit History.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Patient_VisitHistoryController controller = (ui_Patient_VisitHistoryController)loader.getController();
        controller.initializeController(primaryStage, currentUser, currentITService);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @FXML
    private void menuBarClick_Messages() throws Exception {
    	System.out.println("Patient Account - Menu Bar Click - Messages");

    	// switch UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Patient_Messages.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Patient_MessagesController controller = (ui_Patient_MessagesController)loader.getController();
        controller.initializeController(primaryStage, currentUser, currentITService);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @FXML
    private void menuBarClick_ScheduleAppt() throws Exception {
    	System.out.println("Patient Account - Menu Bar Click - Schedule Appt");

    	// switch UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Patient_Schedule Appt.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Patient_ScheduleApptController controller = (ui_Patient_ScheduleApptController)loader.getController();
        controller.initializeController(primaryStage, currentUser, currentITService);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @FXML
    private void menuBarClick_Doctors() throws Exception {
    	System.out.println("Patient Account - Menu Bar Click - Doctors");

    	// switch UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Patient_Doctors.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Patient_DoctorsController controller = (ui_Patient_DoctorsController)loader.getController();
        controller.initializeController(primaryStage, currentUser, currentITService);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    @FXML
    private void ButtonHandlerUpdateInformation(ActionEvent event) throws Exception {
    	System.out.println("Patient Account - update information button action handler");
    
	    // clear previous error messages
		labelNotification.setText("");
		// default color to red for error messages (will switch to green when necessary)
		labelNotificationColor = new ColorPicker(Color.RED);
    	labelNotification.setTextFill(labelNotificationColor.getValue());
		
		// if any field is empty, set isEmptyFields flag to true
        Boolean emptyFieldCheck = false;
        if (firstNameTextField.getText() == null || firstNameTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (lastNameTextField.getText() == null || lastNameTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (birthdayDatePicker.getValue() == null) {
        	emptyFieldCheck = true;
        }
        if (phoneNumberTextField.getText() == null || phoneNumberTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (emailTextField.getText() == null || emailTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (insuranceCompanyNameTextField.getText() == null || insuranceCompanyNameTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (insurancePolicyIDTextField.getText() == null || insurancePolicyIDTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (pharmacyNameTextField.getText() == null || pharmacyNameTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (pharmacyStreetAddressTextField.getText() == null || pharmacyStreetAddressTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (pharmacyCityTextField.getText() == null || pharmacyCityTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (pharmacyStateTextField.getText() == null || pharmacyStateTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (pharmacyZipTextField.getText() == null || pharmacyZipTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }

        if (emptyFieldCheck) {
            System.out.println("Patient Account - ERROR - at least one field is empty");
            // display error message if there are empty fields
        	labelNotification.setText("Please fill out all fields");
        } else {
            // all fields are input, check login attempt 
        	String firstName = firstNameTextField.getText();
			String lastName = lastNameTextField.getText();
			LocalDate birthday = birthdayDatePicker.getValue();
			String uniqueID = firstName + lastName + birthday.toString();
			String phoneNumber = phoneNumberTextField.getText();
			String email = emailTextField.getText();
			
			String insuranceCompanyName = insuranceCompanyNameTextField.getText();
			String insurancePolicyID = insurancePolicyIDTextField.getText();
			String insurancePhoneNumber = insurancePhoneNumberTextField.getText();
			
			String pharmacyName = pharmacyNameTextField.getText();
			String pharmacyStreetAddress = pharmacyStreetAddressTextField.getText();
			String pharmacyCity = pharmacyCityTextField.getText();
			String pharmacyState = pharmacyStateTextField.getText();
			Integer pharmacyZip = Integer.parseInt(pharmacyZipTextField.getText());
                
            // update account within database
			currentITService.updateUserInfo(originalUniqueID, firstName, lastName, birthday, uniqueID, phoneNumber, email);

			// also make the changes locally
			currentUser.setFirstName(firstName);
			currentUser.setLastName(lastName);
			currentUser.setDOB(birthday);
			currentUser.setPhoneNumber(phoneNumber);
			currentUser.setEmail(email);
			currentUser.setUniqueID(uniqueID);
			
			// update insurance information within database
			currentITService.updateInsuranceInfo(originalUniqueID, insuranceCompanyName, insurancePolicyID, insurancePhoneNumber);

			// also make the changes locally
			((Patient)currentUser).setInsurance(insuranceCompanyName, insurancePolicyID, insurancePhoneNumber);
			
			// update Pharmacy information within database
			currentITService.updatePharmacyInfo(originalUniqueID, pharmacyName, pharmacyStreetAddress, pharmacyCity, pharmacyState, (int) pharmacyZip);

			// also make the changes locally
			((Patient)currentUser).setPharmacy(pharmacyName, pharmacyStreetAddress, pharmacyCity, pharmacyState, pharmacyZip);				

            System.out.println("Patient Account - SUCCESS - updated account information");
            // display error message if there are empty fields
        	labelNotification.setText("SUCCESS!");
        	// make the text green 
        	labelNotificationColor = new ColorPicker(Color.GREEN);
        	labelNotification.setTextFill(labelNotificationColor.getValue());
        }
    }
}
