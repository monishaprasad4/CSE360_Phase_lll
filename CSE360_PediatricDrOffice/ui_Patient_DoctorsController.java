import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ui_Patient_DoctorsController {

	private Stage primaryStage;
	
	@FXML private Button chooseDoctor;
	@FXML private MenuBar menuBar;
	@FXML private TextField doctorNameTextField;
	@FXML private TextField doctorSpecialtyTextField;
	@FXML private ListView<String> doctorsListView;
	@FXML private Menu menuBar_Account;
	@FXML private Menu menuBar_VisitHistory;
	@FXML private Menu menuBar_Messages;
	@FXML private Menu menuBar_ScheduleAppt;
	@FXML private Menu menuBar_Doctors;
	private ITService currentITService;
	private User currentUser;
	private ArrayList<User> doctors;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Patient_DoctorsController() {
    	
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
    	
    	// prefill text fields and list view
    	String doctorName = currentITService.getUserFullNameFromUniqueID(((Patient)currentUser).getDoctorUniqueID());
    	if (doctorName != null) {
    		doctorNameTextField.setText(doctorName);
    	}
    	
    	String doctorSpecialty = currentITService.getDoctorSpecialtyFromUniqueID(((Patient)currentUser).getDoctorUniqueID());
    	if (doctorSpecialty != null) {
    		doctorSpecialtyTextField.setText(doctorSpecialty);
    	}
    	
    	doctors = currentITService.getDoctors();
    	ArrayList<String> doctorsForListView = currentITService.getDoctorsForListView(doctors);
        doctorsListView.setItems(FXCollections.observableArrayList(doctorsForListView));
    }
    
    @FXML
    private void menuBarClick_Account() throws Exception {
    	System.out.println("Patient Account - Menu Bar Click - Account");

    	// switch UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Patient_Account.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Patient_AccountController controller = (ui_Patient_AccountController)loader.getController();
        controller.initializeController(primaryStage, currentUser, currentITService);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
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

    	// do nothing, already here
    }
    

    @FXML
    private void ButtonHandlerChooseDoctor(ActionEvent event) throws Exception {
    	System.out.println("Patient Doctor - choose doctor button action handler");
    
    	// get list selection 
    	int currentSelectionIndex = doctorsListView.getSelectionModel().getSelectedIndex();
    	System.out.println("Patient Doctor - choose doctor button action handler - currentSelectionIndex: " + currentSelectionIndex);
    	
    	if (currentSelectionIndex < 0) {
    		// no selection 
    	} else {
    		// selection made 
    		
	    	// update the front-end display
	   		doctorNameTextField.setText(doctors.get(currentSelectionIndex).getFirstName() + " " + doctors.get(currentSelectionIndex).getLastName());
	    	doctorSpecialtyTextField.setText(((Doctor)doctors.get(currentSelectionIndex)).getSpecialty());
	    	
	    	// update the current User
	    	((Patient)currentUser).changeDoctorUniqueID(doctors.get(currentSelectionIndex).getUniqueID());
	    	
	    	// update the database
	    	currentITService.changeDoctor(currentUser.getUniqueID(), ((Patient)currentUser).getDoctorUniqueID());
    	}
    }
}
