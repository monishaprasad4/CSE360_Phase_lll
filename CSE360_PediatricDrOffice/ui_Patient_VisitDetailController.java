import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ui_Patient_VisitDetailController {

	private Stage primaryStage;

	@FXML
	private TextField weightTextField;
	@FXML
	private TextField temperatureTextField;
	@FXML
	private TextField heightTextField;
	@FXML
	private TextField bloodPressureTextField;
	@FXML
	private TextArea knownAllergiesTextArea;
	@FXML
	private TextArea generalHealthConcernsTextArea;
	@FXML
	private TextArea previousHealthIssuesTextArea;
	@FXML
	private TextArea prescribedMedicationsTextArea;
	@FXML
	private TextArea historyOfImmunizationsTextArea;
	@FXML
	private TextArea physicalTestFindingsTextArea;
	@FXML
	private TextArea PrescriptionsTextArea;
	@FXML
	private MenuBar menuBar;
	@FXML
	private Label visitDetailsLabel;
	@FXML
	private Button buttonClose;
	
	private ITService currentITService;
	private User currentUser;
	

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	public ui_Patient_VisitDetailController() {

	}

	@SuppressWarnings("deprecation")
	@FXML
	private void initialize() {
		buttonClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
    	    		Stage stage = (Stage) buttonClose.getScene().getWindow();
    	    		stage.close();
    	    		
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});

	}

	public void initializeController(Stage stage, Appointment appointment, ITService currentITService) {
		this.primaryStage = stage;
		this.currentUser = currentUser;
		this.currentITService = currentITService;
		
		updateDisplayedVisitDetails(appointment);

	}

	private void updateDisplayedVisitDetails(Appointment appointment) {
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

		Patient patient = (Patient)currentITService.getUserFromUniqueID(appointment.getPatientUniqueID());
		
		visitDetailsLabel.setText("Visit Details for " + patient.getFirstName() + " " + patient.getLastName());
		
		VisitDetails visitDetails = appointment.getVisitDetails();
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
				bloodPressureTextField.setText(
						String.valueOf(vitals.getBPSystolic()) + "/" + String.valueOf(vitals.getBPDiastolic()));
			}
			if (patient.getKnownAllergies() != null) {
				knownAllergiesTextArea.setText(patient.getKnownAllergies());
			}
			if (appointment.getReason() != null) {
				generalHealthConcernsTextArea.setText(appointment.getReason());
			}
			if (patient.getPreviousHealthIssues() != null) {
				previousHealthIssuesTextArea.setText(patient.getPreviousHealthIssues());
			}
			if (patient.getPrescribedMedications() != null) {
				prescribedMedicationsTextArea.setText(patient.getPrescribedMedications());
			}
			if (patient.getImmunizationHistory() != null) {
				historyOfImmunizationsTextArea.setText(patient.getImmunizationHistory());
			}
			if (visitDetails.getPhysicalHealthFindings() != null) {
				physicalTestFindingsTextArea.setText(visitDetails.getPhysicalHealthFindings());
			}
			if (visitDetails.getPrescriptions() != null) {
				PrescriptionsTextArea.setText(visitDetails.getPrescriptions());
			}
		}
	}
}
