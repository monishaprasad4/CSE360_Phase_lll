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
import javafx.scene.control.Button;
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
}
