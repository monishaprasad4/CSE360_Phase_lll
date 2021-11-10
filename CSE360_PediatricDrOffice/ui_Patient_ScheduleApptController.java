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

public class ui_Patient_ScheduleApptController {

	private Stage primaryStage;
	
	@FXML private Button cancelAppointment;
	@FXML private Button scheduleAppointment;
	@FXML private TextField upcomingApptDateTextField;
	@FXML private ListView<String> availableApptListView;
	@FXML private Label labelScheduleAppt;
	@FXML private Label labelUpcomingAppt;
	@FXML private Label labelUpcomingApptDate;
	@FXML private MenuBar menuBar;
	@FXML private Menu menuBar_Account;
	@FXML private Menu menuBar_VisitHistory;
	@FXML private Menu menuBar_Messages;
	@FXML private Menu menuBar_ScheduleAppt;
	@FXML private Menu menuBar_Doctors;
	private ITService currentITService;
	private User currentUser;
	private Appointment upcomingAppointment = null;
	private ArrayList<String> upcomingAvailableAppointmentsForDoctor;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Patient_ScheduleApptController() {
    	
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
    	
    	// prefill text fields or list view
    	upcomingAppointment = currentITService.checkForUpcomingAppt(currentUser.getUniqueID());
    	if (upcomingAppointment == null) {
        	showSection(null);
    	} else {
        	showSection(upcomingAppointment.getApptDate().toString());    		
    	}
    }
    
    private void showSection(String upcomingAppointmentDate) {
    	if (upcomingAppointmentDate == null) {
    		// get upcoming available appointments for doctor
    		upcomingAvailableAppointmentsForDoctor = currentITService.getUpcomingAvailableAppointmentsForDoctor(((Patient)currentUser).getDoctorUniqueID());
	
    		if (upcomingAvailableAppointmentsForDoctor == null) {
    			// no available appointments
    		} else {
    			// create an observable list with date only as string
    	    	ArrayList<String> appointmentDates = new ArrayList<String>();
    	    	for (int i = 0; i < upcomingAvailableAppointmentsForDoctor.size(); i++) {
    	    		// appointmentDates.add(appointments.get(i).getApptDate_String_Date());
    	    		// appointmentDates.add(upcomingAvailableAppointmentsForDoctor.get(i).getApptDate().toString());
    	    		appointmentDates.add(upcomingAvailableAppointmentsForDoctor.get(i));
    			}
    	    	
    	    	availableApptListView.setItems(FXCollections.observableArrayList(appointmentDates));
    		}

        	// hide upcoming appointment section
	    	cancelAppointment.setVisible(false);
	    	upcomingApptDateTextField.setVisible(false);
	    	labelUpcomingAppt.setVisible(false);
        	labelUpcomingApptDate.setVisible(false);
        	
        	// show schedule appointment section 
	    	scheduleAppointment.setVisible(true);
	    	availableApptListView.setVisible(true);
	    	labelScheduleAppt.setVisible(true);  
    	} else {
    		upcomingApptDateTextField.setText(upcomingAppointmentDate);
    		
	    	// show upcoming appointment section
        	cancelAppointment.setVisible(true);
        	upcomingApptDateTextField.setVisible(true);
        	labelUpcomingAppt.setVisible(true);
        	labelUpcomingApptDate.setVisible(true);
        	
	    	
	    	// hide schedule appointment section
        	scheduleAppointment.setVisible(false);
        	availableApptListView.setVisible(false);
        	labelScheduleAppt.setVisible(false);
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

    	// do nothing, already here
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
    private void ButtonHandlerScheduleAppt(ActionEvent event) throws Exception {
    	System.out.println("Patient Schedule Appt - Schedule Appt button action handler");
    	
    	// get list selection 
    	int currentSelectionIndex = availableApptListView.getSelectionModel().getSelectedIndex();
    	System.out.println("Patient Visit History - list view click action handler - currentSelectionIndex: " + currentSelectionIndex);
    	
    	if (currentSelectionIndex < 0) {
    		// no selection 
    	} else {
    		// selection made 

    		// update database
        	upcomingAppointment = new Appointment();
        	upcomingAppointment.setPatientUniqueID(currentUser.getUniqueID());
        	upcomingAppointment.setDoctorUniqueID(((Patient)currentUser).getDoctorUniqueID());
        	upcomingAppointment.setApptDate_String(upcomingAvailableAppointmentsForDoctor.get(currentSelectionIndex));
        	upcomingAppointment.setUniqueID(upcomingAppointment.getApptDate().toString() + " " + currentUser.getUniqueID() + " " + ((Patient)currentUser).getDoctorUniqueID());
        	currentITService.scheduleAppointment(upcomingAppointment);
        	
        	// update UI
            showSection(upcomingAppointment.getApptDate().toString());
    	}
    }

    @FXML
    private void ButtonHandlerCancelAppt(ActionEvent event) throws Exception {
    	System.out.println("Patient Schedule Appt - Cancel Appt button action handler");
    	
    	// update database - cancel appointment
    	Boolean cancelSuccessful = currentITService.cancelAppointment(upcomingAppointment);
    	if (cancelSuccessful) {
        	System.out.println("Patient Schedule Appt - Cancel Successful!");
    	} else {
        	System.out.println("Patient Schedule Appt - Cancel ERROR!");
    	}
    	upcomingAppointment = null;
    	
        showSection(null);
    }
}
