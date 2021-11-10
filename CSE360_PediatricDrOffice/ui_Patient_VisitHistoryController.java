import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ui_Patient_VisitHistoryController {

	private Stage primaryStage;
	
	@FXML private TextField weightTextField;
	@FXML private TextField temperatureTextField;
	@FXML private TextField heightTextField;
	@FXML private TextField bloodPressureTextField;
	@FXML private TextArea knownAllergiesTextArea;
	@FXML private TextArea generalHealthConcernsTextArea;
	@FXML private TextArea previousHealthIssuesTextArea;
	@FXML private TextArea prescribedMedicationsTextArea;
	@FXML private TextArea historyOfImmunizationsTextArea;
	@FXML private TextArea physicalTestFindingsTextArea;
	@FXML private TextArea PrescriptionsTextArea;
	@FXML private ListView<String> apptsListView;
	@FXML private MenuBar menuBar;
	@FXML private Menu menuBar_Account;
	@FXML private Menu menuBar_VisitHistory;
	@FXML private Menu menuBar_Messages;
	@FXML private Menu menuBar_ScheduleAppt;
	@FXML private Menu menuBar_Doctors;
	private ITService currentITService;
	private User currentUser;
	private ArrayList<Appointment> appointments;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Patient_VisitHistoryController() {
    	
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
    	
    	// get appointments and display visit details for latest appointment
    	appointments = currentITService.getAppointmentsForUser(currentUser);
    	
    	// create an observable list with date only as string
    	ArrayList<String> appointmentDates = new ArrayList<String>();
    	for (int i = 0; i < appointments.size(); i++) {
    		// appointmentDates.add(appointments.get(i).getApptDate_String_Date());
    		appointmentDates.add(appointments.get(i).getApptDate().toString());
		}
    	
    	apptsListView.setItems(FXCollections.observableArrayList(appointmentDates));
    }
    
    private void updateDisplayedVisitDetails(int index) {
    	// wipe out previous data to start
    	weightTextField.setText("");
		heightTextField.setText("");
		temperatureTextField.setText("");
		bloodPressureTextField.setText("");
		knownAllergiesTextArea.setText("");
		generalHealthConcernsTextArea.setText("");
		previousHealthIssuesTextArea.setText("");
		prescribedMedicationsTextArea.setText("");
		historyOfImmunizationsTextArea.setText("");
		physicalTestFindingsTextArea.setText("");
		PrescriptionsTextArea.setText("");
		
    	VisitDetails visitDetails = appointments.get(index).getVisitDetails();
    	if (visitDetails == null) {
    		// nothing to display
    	} else {
    		// VisitDetails is not null, display
    		
	    	Vitals vitals = visitDetails.getVitals();
	    	
	    	if (vitals.getWeight() != 0) {
	    		weightTextField.setText(String.valueOf(vitals.getWeight()));
	    	}
	    	if (vitals.getHeight() != 0) {
	    		heightTextField.setText(String.valueOf(vitals.getHeight()));
	    	}
	    	if (vitals.getTemperature() != 0) {
	    		temperatureTextField.setText(String.valueOf(vitals.getTemperature()));
	    	}
	    	if (vitals.getBPDiastolic() != 0 && vitals.getBPSystolic() != 0) {
	    		bloodPressureTextField.setText(String.valueOf(vitals.getBPSystolic()) + "/" + String.valueOf(vitals.getBPDiastolic()));
	    	}
	    	if (((Patient)currentUser).getKnownAllergies() != null) {
	    		knownAllergiesTextArea.setText(((Patient)currentUser).getKnownAllergies());
	    	}
	    	if (appointments.get(index).getReason() != null) {
	    		generalHealthConcernsTextArea.setText(appointments.get(index).getReason());
	    	}
	    	if (((Patient)currentUser).getPreviousHealthIssues() != null) {
	    		previousHealthIssuesTextArea.setText(((Patient)currentUser).getPreviousHealthIssues());
	    	}
	    	if (((Patient)currentUser).getPrescribedMedications() != null) {
	    		prescribedMedicationsTextArea.setText(((Patient)currentUser).getPrescribedMedications());
	    	}
	    	if (((Patient)currentUser).getImmunizationHistory() != null) {
	    		historyOfImmunizationsTextArea.setText(((Patient)currentUser).getImmunizationHistory());
	    	}
	    	if (visitDetails.getPhysicalHealthFindings() != null) {
	    		physicalTestFindingsTextArea.setText(visitDetails.getPhysicalHealthFindings());
	    	}
	    	if (visitDetails.getPrescriptions() != null) {
	    		PrescriptionsTextArea.setText(visitDetails.getPrescriptions());
	    	}
    	}
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

    	// do nothing, already here
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
    private void onMouseClickListViewHandler(MouseEvent event) throws Exception {
    	System.out.println("Patient Visit History - list view click action handler");
    	
    	// get list selection 
    	int currentSelectionIndex = apptsListView.getSelectionModel().getSelectedIndex();
    	System.out.println("Patient Visit History - list view click action handler - currentSelectionIndex: " + currentSelectionIndex);
    	
    	if (currentSelectionIndex < 0) {
    		// no selection 
    	} else {
    		// selection made 
    		
        	updateDisplayedVisitDetails(currentSelectionIndex);
    	}
    }
}
