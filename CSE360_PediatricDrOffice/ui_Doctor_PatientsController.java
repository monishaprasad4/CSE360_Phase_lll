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
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import java.io.IOException;

public class ui_Doctor_PatientsController {

	private Stage primaryStage;
	
	@FXML private Menu menuBar_Appointments;
	@FXML private Menu menuBar_Patients;
	@FXML private Menu menuBar_Messages;
	private ITService currentITService; private User currentUser;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Doctor_PatientsController() {
    	
    }    
    
    @SuppressWarnings("deprecation")
	@FXML
    private void initialize() {
    	
    	menuBar_Appointments.setGraphic(
			ButtonBuilder.create()
			    .text("Appointments")
			    .onAction(new EventHandler<ActionEvent>(){
			        @Override public void handle(ActionEvent t) {
			        	try {
							menuBarClick_Appointments(t);
						} catch (Exception e) {
							e.printStackTrace();
						}
			         } })
			        .build()
		);
    	
    	menuBar_Patients.setGraphic(
			ButtonBuilder.create()
			    .text("Patients")
			    .onAction(new EventHandler<ActionEvent>(){
			        @Override public void handle(ActionEvent t) {
			        	try {
							menuBarClick_Patients(t);
						} catch (Exception e) {
							e.printStackTrace();
						}
			         } })
			        .build()
		);
    	
    	menuBar_Messages.setGraphic(
			ButtonBuilder.create()
			    .text("Messages")
			    .onAction(new EventHandler<ActionEvent>(){
			        @Override public void handle(ActionEvent t) {
			        	try {
							menuBarClick_Messages(t);
						} catch (Exception e) {
							e.printStackTrace();
						}
			         } })
			        .build()
		);
    }   	
    
    public void initializeController(Stage stage, User currentUser, ITService currentITService) {
    	this.primaryStage = stage;
    	this.currentUser = currentUser;
    	this.currentITService = currentITService;
    	
    	primaryStage.setOnCloseRequest(event -> {
    	    System.out.println("Closing, print updated information to database (file)");
    	    currentITService.printToFile();
    	});
    	
    	// TODO - load current session information and prefill table
    	
    }
    
    @FXML
    private void menuBarClick_Appointments(ActionEvent event) throws Exception {
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
    private void menuBarClick_Patients(ActionEvent event) throws Exception {
    	System.out.println("Doctor Patients - Menu Bar Click - Patients");

    	// do nothing, already here
    }
    
    @FXML
    private void menuBarClick_Messages(ActionEvent event) throws Exception {
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
}
