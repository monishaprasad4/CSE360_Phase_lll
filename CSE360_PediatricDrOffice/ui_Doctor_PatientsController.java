import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class ui_Doctor_PatientsController {

	private Stage primaryStage;
	
	@FXML private Menu menuBar_Appointments;
	@FXML private Menu menuBar_Patients;
	@FXML private Menu menuBar_Messages;
	
	@FXML private TableView<PatientDataView> tableView_Patients;
    @FXML private TableColumn<PatientDataView, String> nameColumn;
    @FXML private TableColumn<PatientDataView, String> lastVisitColumn;
	@FXML private TableColumn<PatientDataView, String> nextVisitColumn;
	
	@FXML private TextArea patientDetails;
	@FXML private ListView<String> listView_VisitHistory;
	
	private ITService currentITService; private User currentUser;
	
	private ArrayList<Appointment> appointments;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Doctor_PatientsController() {
    	
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
    	
    	nameColumn.setCellValueFactory(new PropertyValueFactory<PatientDataView, String>("fullName"));
    	lastVisitColumn.setCellValueFactory(new PropertyValueFactory<PatientDataView, String>("lastVisit"));
    	nextVisitColumn.setCellValueFactory(new PropertyValueFactory<PatientDataView, String>("nextVisit"));
    }   	
    
    public void initializeController(Stage stage, User currentUser, ITService currentITService) {
    	this.primaryStage = stage;
    	this.currentUser = currentUser;
    	this.currentITService = currentITService;
    	
    	primaryStage.setOnCloseRequest(event -> {
    	    System.out.println("Closing, print updated information to database (file)");
    	    currentITService.printToFile();
    	});
    	
    	ArrayList<User> list = currentITService.getPatientsForDoctor(currentUser.getCredentials().getUniqueID());
    	
    	ArrayList<PatientDataView> patientDataList = new ArrayList<PatientDataView>();
    	for(int i = 0; i < list.size(); i++)
    	{
    		patientDataList.add(new PatientDataView((Patient)list.get(i), currentITService));
    	}
    	
    	ObservableList data = FXCollections.observableList(patientDataList);
    	tableView_Patients.setItems(data);    	
    	
    	tableView_Patients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    	    if (newSelection != null) {
        		Patient patient = tableView_Patients.getSelectionModel().getSelectedItem().getPatient();
	    		patientDetails.setText(patient.toString());
	    		
	    		appointments = currentITService.getAppointmentsForUser(patient);
	        	
	        	listView_VisitHistory.getItems().clear();
	        	
	        	// create an observable list with date only as string
	        	ArrayList<String> appointmentDates = new ArrayList<String>();
	        	for (int i = 0; i < appointments.size(); i++) {
	        		
	        		// appointmentDates.add(appointments.get(i).getApptDate_String_Date());
	        		Date date = appointments.get(i).getApptDate();
	        		
	        		String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
	        		//appointmentDates.add(appointments.get(i).getApptDate().toString());
	        		appointmentDates.add(formattedDate + "\t" + appointments.get(i).getReason());
	    		}
	        	listView_VisitHistory.setItems(FXCollections.observableArrayList(appointmentDates));
	        	
	        	listView_VisitHistory.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        	    @Override
	        	    public void handle(MouseEvent click) {

	        	        if (click.getClickCount() == 2) {
	        	           //Use ListView's getSelected Item
	        	        	int index = listView_VisitHistory.getSelectionModel().getSelectedIndex();
	        	        	showVisitDetails(index);                                                    
	        	        }
	        	    }
	        	});
    	    }
    	});
    	
    	if (tableView_Patients.getItems().size() > 0) {    	
    		tableView_Patients.getSelectionModel().selectFirst();
    	}
    }
    
    @FXML
    private void menuBarClick_Appointments() throws Exception {
    	System.out.println("Doctor Patients - Menu Bar Click - Appointments");

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
    	System.out.println("Doctor Patients - Menu Bar Click - Patients");

    	// do nothing, already here
    }
    
    @FXML
    private void menuBarClick_Messages() throws Exception {
    	System.out.println("Doctor Patients - Menu Bar Click - Messages");

		// switch UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Doctor_Messages.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Doctor_MessagesController controller = (ui_Doctor_MessagesController)loader.getController();
        controller.initializeController(primaryStage, currentUser, currentITService);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void showVisitDetails(int index)
    {
    	Patient patient = tableView_Patients.getSelectionModel().getSelectedItem().getPatient();
		appointments = currentITService.getAppointmentsForUser(patient);
		Appointment appointment = appointments.get(index);
		
		
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Patient_Visit Detail.fxml"));
        BorderPane borderPane;
		try {
			borderPane = loader.<BorderPane>load();
			
			ui_Patient_VisitDetailController controller = (ui_Patient_VisitDetailController)loader.getController();
			
            controller.initializeController(primaryStage,  appointment, currentITService);
            
            Scene scene = new Scene(borderPane);
            
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		    	
    }
}
