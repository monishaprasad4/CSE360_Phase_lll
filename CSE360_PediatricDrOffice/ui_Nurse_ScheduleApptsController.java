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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class ui_Nurse_ScheduleApptsController {

	private Stage primaryStage;
	
	@FXML private ListView<String> listViewPatients;
	@FXML private ListView<String> listViewAvailableDates;
	@FXML private TextArea textReason;
	@FXML private Label labelDoctor;
	@FXML private Button buttonScheduleAppt;
	@FXML private Button buttonCancel;
	
	private ITService currentITService;
	private User currentUser;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Nurse_ScheduleApptsController() {
    	
    	
    }    
    
    @SuppressWarnings("deprecation")
	@FXML
    private void initialize() {
    	
    	buttonCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		Stage stage = (Stage) buttonCancel.getScene().getWindow();
    	    		stage.close();
    	    		
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	
    	buttonScheduleAppt.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {

    	    		ArrayList<User> patients = currentITService.getPatients();
    	    		int index = listViewPatients.getSelectionModel().getSelectedIndex();
    	    		Patient patient = (Patient) patients.get(index);
    	    	    
    	    		int currentSelectionIndex = listViewAvailableDates.getSelectionModel().getSelectedIndex();
    	    		Appointment upcomingAppointment = new Appointment();
    	        	upcomingAppointment.setPatientUniqueID(currentUser.getUniqueID());
    	        	upcomingAppointment.setDoctorUniqueID(patient.getDoctorUniqueID());
    	        	upcomingAppointment.setApptDate_String(listViewAvailableDates.getSelectionModel().getSelectedItem());
    	        	upcomingAppointment.setUniqueID(upcomingAppointment.getApptDate().toString() + " " + patient.getUniqueID() + " " + patient.getDoctorUniqueID());
    	        	upcomingAppointment.setReason(textReason.getText().trim());
    	        	
    	        	currentITService.scheduleAppointment(upcomingAppointment);
    	    		Stage stage = (Stage) buttonScheduleAppt.getScene().getWindow();
    	    		stage.close();
    	    		
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	listViewPatients.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                    	int index = listViewPatients.getSelectionModel().getSelectedIndex();
                        listViewPatientClickedHandler(index);                            
                }
            });  
    	
    	labelDoctor.setStyle("-fx-background-color: white;");
    	
    }   	
    
    public void initializeController(Stage stage, User currentUser, ITService currentITService) {
    	this.primaryStage = stage;
    	this.currentUser = currentUser;
    	this.currentITService = currentITService;
    	
    	ArrayList<String> patients = currentITService.getPatientsForListView(currentITService.getPatients());
    	
    	listViewPatients.setItems(FXCollections.observableArrayList(patients));
    	
    	if(patients.size() > 0)
    	{
    		listViewPatients.getSelectionModel().selectFirst();
    	}
    }
    
    private void listViewPatientClickedHandler(int index)
    {
    	ArrayList<User> patients = currentITService.getPatients();
    	
    	Patient patient = (Patient) patients.get(index);
    	String doctorID = patient.getDoctorUniqueID();
    	User user = currentITService.getUserFromUniqueID(doctorID);
    	if(user != null)
    	{
    		String doctorName = currentITService.getUserFullNameFromUniqueID(doctorID);
    		labelDoctor.setText(doctorName);
    
    		ArrayList<String> upcomingAvailableAppointmentsForDoctor;
    		upcomingAvailableAppointmentsForDoctor = currentITService.getUpcomingAvailableAppointmentsForDoctor(doctorID);
    		
    		ArrayList<String> appointmentDates = new ArrayList<String>();
	    	for (int i = 0; i < upcomingAvailableAppointmentsForDoctor.size(); i++) {
	    		appointmentDates.add(upcomingAvailableAppointmentsForDoctor.get(i));
			}
	    	
	    	listViewAvailableDates.setItems(FXCollections.observableArrayList(appointmentDates));
	    	
	    	listViewAvailableDates.getSelectionModel().selectFirst();
    	}
    }
}
