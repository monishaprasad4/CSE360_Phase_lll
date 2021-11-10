import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ui_Doctor_MessageDetailController {

	private Stage primaryStage;
	
	@FXML private Menu menuBar_Appointments;
	@FXML private Menu menuBar_Patients;
	@FXML private Menu menuBar_Messages;
	private ITService currentITService;
	private User currentUser;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Doctor_MessageDetailController() {
    	
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
    private void menuBarClick_Appointments() throws Exception {
    	System.out.println("Doctor Message Details - Menu Bar Click - Appointments");

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
    	System.out.println("Doctor Message Details - Menu Bar Click - Patients");

		// switch UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Doctor_Patients.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Doctor_PatientsController controller = (ui_Doctor_PatientsController)loader.getController();
        controller.initializeController(primaryStage, currentUser, currentITService);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @FXML
    private void menuBarClick_Messages() throws Exception {
    	System.out.println("Doctor Message Details - Menu Bar Click - Messages");

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
