import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class ui_Nurse_ApptsController {

	private Stage primaryStage;
	
	@FXML private Menu menuBar_Appointments;
	@FXML private Menu menuBar_Patients;
	@FXML private Menu menuBar_Messages;
	@FXML private Menu menuBar_Today;
	@FXML private Menu menuBar_All;
	@FXML private Menu menuBar_Search;
	@FXML private Menu menuBar_Delete;
	@FXML private Menu menuBar_New;
	@FXML private TableView<AppointmentDataView> tableView_Appointments;
    @FXML private TableColumn<AppointmentDataView, String> dateColumn;
    @FXML private TableColumn<AppointmentDataView, String> timeColumn;
    @FXML private TableColumn<AppointmentDataView, String> patientColumn;
    @FXML private TableColumn<AppointmentDataView, String> reasonColumn;
    @FXML private TableColumn<AppointmentDataView, String> doctorColumn;
    
	private ITService currentITService;
	private User currentUser;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Nurse_ApptsController() {
    	
    }    
    
    @SuppressWarnings("deprecation")
	@FXML
    private void initialize() {
    	
    	Label label = new Label("Appointments");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_Appointments();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_Appointments.setGraphic(label);
    	    	
    	label = new Label("Patients");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_Patients();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_Patients.setGraphic(label);
    	
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
    	
    	label = new Label("Today");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_Appointments_Today();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_Today.setGraphic(label);
    	
    	label = new Label("All");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_Appointments_All();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_All.setGraphic(label);
    	
    	label = new Label("Search");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_Appointments_Search();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_Search.setGraphic(label);
    	
    	label = new Label("Delete");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_Appointments_Delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_Delete.setGraphic(label);
    	
    	label = new Label("New");
    	label.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		menuBarClick_Appointments_New();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	menuBar_New.setGraphic(label);
    	
    	dateColumn.setCellValueFactory(new PropertyValueFactory<AppointmentDataView, String>("date"));
    	timeColumn.setCellValueFactory(new PropertyValueFactory<AppointmentDataView, String>("time"));
    	patientColumn.setCellValueFactory(new PropertyValueFactory<AppointmentDataView, String>("patientFullName"));
    	reasonColumn.setCellValueFactory(new PropertyValueFactory<AppointmentDataView, String>("reason"));
    	doctorColumn.setCellValueFactory(new PropertyValueFactory<AppointmentDataView, String>("doctorFullName"));    	
    }   	
    
    public void initializeController(Stage stage, User currentUser, ITService currentITService) {
    	this.primaryStage = stage;
    	this.currentUser = currentUser;
    	this.currentITService = currentITService;
    	
    	primaryStage.setOnCloseRequest(event -> {
    	    System.out.println("Closing, print updated information to database (file)");
    	    currentITService.printToFile();
    	});
    	
    	updateAppointmentsList();
    }
    
    @FXML
    private void menuBarClick_Appointments() throws Exception {
    	System.out.println("Nurse/Doctor Appts - Menu Bar Click - Appointments");

    	// switch UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Nurse_Appts.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Nurse_ApptsController controller = (ui_Nurse_ApptsController)loader.getController();
        controller.initializeController(primaryStage, currentUser, currentITService);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @FXML
    private void menuBarClick_Patients() throws Exception {
    	System.out.println("Nurse/Doctor Appts - Menu Bar Click - Patients");
    	
    	if (currentUser.getUserType() == UserType.DOCTOR) {
    		// Doctor
    		// switch UI
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ApplicationDriver.class.getResource("ui_Doctor_Patients.fxml"));
            BorderPane borderPane = loader.<BorderPane>load();
            
            ui_Doctor_PatientsController controller = (ui_Doctor_PatientsController)loader.getController();
            controller.initializeController(primaryStage, currentUser, currentITService);
            
            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);
            primaryStage.show();
    	} else {
    		// Nurse
    		// switch UI
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ApplicationDriver.class.getResource("ui_Nurse_Patients.fxml"));
            BorderPane borderPane = loader.<BorderPane>load();
            
            ui_Nurse_PatientsController controller = (ui_Nurse_PatientsController)loader.getController();
            controller.initializeController(primaryStage, currentUser, currentITService);
            
            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);
            primaryStage.show();
    	}
    }
    
    @FXML
    private void menuBarClick_Messages() throws Exception {
    	System.out.println("Nurse/Doctor Appts - Menu Bar Click - Messages");

    	if (currentUser.getUserType() == UserType.DOCTOR) {
    		// Doctor
    		// switch UI
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ApplicationDriver.class.getResource("ui_Doctor_Messages.fxml"));
            BorderPane borderPane = loader.<BorderPane>load();
            
            ui_Doctor_MessagesController controller = (ui_Doctor_MessagesController)loader.getController();
            controller.initializeController(primaryStage, currentUser, currentITService);
            
            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);
            primaryStage.show();
    	} else {
    		// Nurse
    		// switch UI
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ApplicationDriver.class.getResource("ui_Nurse_Messages.fxml"));
            BorderPane borderPane = loader.<BorderPane>load();
            
            ui_Nurse_MessagesController controller = (ui_Nurse_MessagesController)loader.getController();
            controller.initializeController(primaryStage, currentUser, currentITService);
            
            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);
            primaryStage.show();
    	}
    }
    
    @FXML
    private void menuBarClick_Appointments_New() throws Exception {
    	System.out.println("New Appointment");
    }
    
    @FXML
    private void menuBarClick_Appointments_All() throws Exception {
    	System.out.println("Display All Appointments");
    }
    
    @FXML
    private void menuBarClick_Appointments_Search() throws Exception {
    	System.out.println("Search Appointment");
    }
    
    @FXML
    private void menuBarClick_Appointments_Delete() throws Exception {
    	System.out.println("Delete Appointment");
    	
    	if (tableView_Appointments.getItems().size() > 0) {
    		
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this appointment?", ButtonType.YES, ButtonType.NO);
    		alert.setTitle("Doctor's Office Portal");
	        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
    	        
  	        if (ButtonType.YES.equals(result)) {
  	        	Appointment appt = tableView_Appointments.getSelectionModel().getSelectedItem().getAppointment();
  	        	currentITService.cancelAppointment(appt);
    		
  	        	updateAppointmentsList();
  	        }
    	}
    }
    
    @FXML
    private void menuBarClick_Appointments_Today() throws Exception {
    	System.out.println("Appointments Today");
    }
    
    private void updateAppointmentsList()
    {
    	ArrayList<Appointment> list;
    	if (currentUser.getUserType() == UserType.NURSE)
    	{
    		list = currentITService.getAppointments();
    	}
    	else
    	{
    		list = currentITService.getAppointmentsForUser(currentUser.getUniqueID());
    	}
    	
    	ArrayList<AppointmentDataView> appointmentDataList = new ArrayList<AppointmentDataView>();
    	for(int i = 0; i < list.size(); i++)
    	{
    		appointmentDataList.add(new AppointmentDataView(list.get(i), currentITService));
    	}
    	
    	ObservableList data = FXCollections.observableList(appointmentDataList);
    	tableView_Appointments.setItems(data);
    	
    	if (tableView_Appointments.getItems().size() > 0) {
    		tableView_Appointments.getSelectionModel().selectFirst();
    	}
    }
}
