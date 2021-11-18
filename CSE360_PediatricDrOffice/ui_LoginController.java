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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class ui_LoginController {

	private Stage primaryStage;
	
	@FXML private Button signInButton;
	@FXML private Button createAccountButton;
	@FXML private TextField firstNameTextField;
	@FXML private TextField lastNameTextField;
	@FXML private PasswordField passwordTextField;
	@FXML private DatePicker birthdayDatePicker;
	@FXML private Label labelNotification;
	
	private ITService currentITService;
	private User currentUser = null;
	
			
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_LoginController() {
    	
    }    
    
    @FXML
    private void initialize() {
    	currentITService = new ITService();
    	ITService.Instance = currentITService;
    	currentITService.readFromFile();    	
    }   	
    
    public void initializeController(Stage stage) {
    	this.primaryStage = stage;
    }

    @FXML
    private void ButtonHandlerCreateAccount(ActionEvent event) throws Exception {
    	System.out.println("Login - create account button action handler");
    
	    // clear previous error messages
		labelNotification.setText("");
		
		// switch to create account UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Login_CreateAccount.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Login_CreateAccountController controller = (ui_Login_CreateAccountController)loader.getController();
        controller.initializeController(primaryStage, currentITService);
		
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @FXML
    private void ButtonHandlerSignIn(ActionEvent event) throws IOException {
        System.out.println("Login - sign in button action handler");
        
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
        //if (birthdayDatePicker.getValue() == null) {
//        	emptyFieldCheck = true;
        //}
        
        if (birthdayDatePicker.getEditor().getText() == null || birthdayDatePicker.getEditor().getText().trim().isEmpty()) {
        		emptyFieldCheck = true;
        }
        
        
        if (emptyFieldCheck) {
            System.out.println("Login - ERROR - at least one field is empty");
            // display error message if there are empty fields
        	labelNotification.setText("Please fill out all fields");
        } else {
            // all fields are input, check login attempt 
        	birthdayDatePicker.setValue(birthdayDatePicker.getConverter()
        		    .fromString(birthdayDatePicker.getEditor().getText()));
        	String firstName = firstNameTextField.getText();
			String lastName = lastNameTextField.getText();
			String password = passwordTextField.getText();
			LocalDate birthday = birthdayDatePicker.getValue();
			String uniqueID = firstName + lastName + birthday.toString();
			
			// create a credential for the user
			Credentials tempCredential = new Credentials();
			tempCredential.setUniqueID(uniqueID);
			tempCredential.setPassword(password);
			    
            // check login and get user associated with credentials (if there is one)
	        System.out.println("check login for unique ID: " + uniqueID);
	        currentUser = currentITService.signIn(tempCredential);
	        if (currentUser == null) {
		        System.out.println("login unsuccessful");
	            // display error message 
	        	labelNotification.setText("Login failed, please try again");
	        } else {
    			System.out.println("login successful");
		        
		        // determine which screen to switch to based on Patient, Nurse, Doctor
		        if (currentUser.getUserType() == UserType.PATIENT) {
		        	// patient
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
		        	// nurse or doctor
		        	// switch to Appts
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
