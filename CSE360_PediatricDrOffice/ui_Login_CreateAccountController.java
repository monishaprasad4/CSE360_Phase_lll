import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class ui_Login_CreateAccountController {

	private Stage primaryStage;
	
	@FXML private Button createAccountButton;
	@FXML private ComboBox<String> accountType;
	@FXML private TextField firstNameTextField;
	@FXML private TextField lastNameTextField;
	@FXML private PasswordField passwordTextField;
	@FXML private DatePicker birthdayDatePicker;
	@FXML private Label labelNotification;

	private ITService currentITService;
	private User currentUser;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Login_CreateAccountController() {
    	
    }    
    
    @FXML
    private void initialize() {
    	
    }   	
    
    public void initializeController(Stage stage, ITService currentITService) {
    	this.primaryStage = stage;    	
    	this.currentITService = currentITService;
    	
    	accountType.getItems().addAll(
    			"Patient",
    			"Nurse",
    			"Doctor"
    			);
    }

    @FXML
    private void ButtonHandlerCreateAccount(ActionEvent event) throws Exception {
    	System.out.println("Create Account - create account button action handler");
    
	    // clear previous error messages
		labelNotification.setText("");
		
		// if any field is empty, set isEmptyFields flag to true
        Boolean emptyFieldCheck = false;
        if (firstNameTextField.getText() == null || firstNameTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (lastNameTextField.getText() == null || lastNameTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (passwordTextField.getText() == null || passwordTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (birthdayDatePicker.getValue() == null) {
        	emptyFieldCheck = true;
        }
        if (accountType.getValue() == null) {
        	emptyFieldCheck = true;        	
        }

        if (emptyFieldCheck) {
            System.out.println("Login - ERROR - at least one field is empty");
            // display error message if there are empty fields
        	labelNotification.setText("Please fill out all fields");
        } else {
            // all fields are input, check login attempt 
        	String firstName = firstNameTextField.getText();
			String lastName = lastNameTextField.getText();
			String password = passwordTextField.getText();
			LocalDate birthday = birthdayDatePicker.getValue();
			String uniqueID = firstName + lastName + birthday.toString();

			// create a credential for the user
			Credentials tempCredential = new Credentials();
			tempCredential.setUniqueID(uniqueID);
			tempCredential.setPassword(password);
			
			// create current user
			if (accountType.getValue().toString().equals("Patient")) {
	            System.out.println("Account Type Patient Selected");
				currentUser = new Patient();
				currentUser.setUserType(UserType.PATIENT);
		    } else if (accountType.getValue().toString().equals("Nurse")) {
	            System.out.println("Account Type Nurse Selected");
		    	currentUser = new Nurse();
				currentUser.setUserType(UserType.NURSE);
		    } else if (accountType.getValue().toString().equals("Doctor")) {
	            System.out.println("Account Type Doctor Selected");
		    	currentUser = new Doctor();
				currentUser.setUserType(UserType.DOCTOR);
		    }
			
			currentUser.setDOB(birthday);
			currentUser.setFirstName(firstName);
			currentUser.setLastName(lastName);
			currentUser.setCredentials(tempCredential);
                
            // create account within database
			Boolean createUserCheck = currentITService.createUser(currentUser);
			
			if (!createUserCheck) {
	            System.out.println("Login - ERROR - user already exists");
	            // display error message 
	        	labelNotification.setText("User already exists, try again");
			} else {
				// successful user creation
				
			    // determine which screen to switch to based on Patient, Nurse, Doctor
			    if (currentUser.getUserType() == UserType.PATIENT) {
			    	// Patient
			    	// switch to Patient create account UI
					FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(ApplicationDriver.class.getResource("ui_Patient_Account.fxml"));
			        BorderPane borderPane = loader.<BorderPane>load();
			        
			        ui_Patient_AccountController controller = (ui_Patient_AccountController)loader.getController();
			        controller.initializeController(primaryStage, currentUser, currentITService);
			        
			        Scene scene = new Scene(borderPane);
			        primaryStage.setScene(scene);
			        primaryStage.show();
			    } else {
		            // Nurse or Doctor
		            // switch to Nurse Appts
					FXMLLoader loader = new FXMLLoader();
			        loader.setLocation(ApplicationDriver.class.getResource("ui_Nurse_Appts.fxml"));
			        BorderPane borderPane = loader.<BorderPane>load();
			        
			        ui_Nurse_ApptsController controller = (ui_Nurse_ApptsController)loader.getController();
			        controller.initializeController(primaryStage, currentUser, currentITService);
			        
			        Scene scene = new Scene(borderPane);
			        primaryStage.setScene(scene);
			        primaryStage.show();		    	
			    }
			}
        }
    }
}
