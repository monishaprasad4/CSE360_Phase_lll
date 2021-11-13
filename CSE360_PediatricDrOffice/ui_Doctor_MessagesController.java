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

public class ui_Doctor_MessagesController {

	private Stage primaryStage;
	
	@FXML private Menu menuBar_Appointments;
	@FXML private Menu menuBar_Patients;
	@FXML private Menu menuBar_Messages;
	
	@FXML private TableView<MessageDataView> tableView_Messages;
    @FXML private TableColumn<MessageDataView, String> fromColumn;
    @FXML private TableColumn<MessageDataView, String> subjectColumn;
    @FXML private TableColumn<MessageDataView, String> dateColumn;
    
    @FXML private Button button_MessageReply;
    @FXML private Button button_MessageDelete;
    @FXML private Button button_MessageCall;
    @FXML private TextArea textArea_MessageDetails;
    
	private ITService currentITService;
	private User currentUser;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Doctor_MessagesController() {
    	
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
    	
    	button_MessageReply.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
					buttonClick_Reply();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	    	
    	button_MessageDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
					buttonClick_Delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});
    	
    	button_MessageCall.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	    	try {
					buttonClick_Call();
				} catch (Exception e) {
					e.printStackTrace();
				}
    	    }
    	});

    	
    	fromColumn.setCellValueFactory(new PropertyValueFactory<MessageDataView, String>("from"));
    	subjectColumn.setCellValueFactory(new PropertyValueFactory<MessageDataView, String>("subject"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<MessageDataView, String>("date"));
    }   	
    
    public void initializeController(Stage stage, User currentUser, ITService currentITService) {
    	this.primaryStage = stage;
    	this.currentUser = currentUser;
    	this.currentITService = currentITService;
    	
    	primaryStage.setOnCloseRequest(event -> {
    	    System.out.println("Closing, print updated information to database (file)");
    	    currentITService.printToFile();
    	});
    	
    	ArrayList<Message> list = currentITService.getMessagesForUser(currentUser);
    	
    	ArrayList<MessageDataView> messageDataList = new ArrayList<MessageDataView>();
    	for(int i = 0; i < list.size(); i++)
    	{
    		messageDataList.add(new MessageDataView(list.get(i), currentITService));
    	}
    	
    	ObservableList data = FXCollections.observableList(messageDataList);
    	tableView_Messages.setItems(data);
    	
    	tableView_Messages.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    	    if (newSelection != null) {
        		Message message = tableView_Messages.getSelectionModel().getSelectedItem().getMessage();
	    		textArea_MessageDetails.setText(message.toString());
    	    }
    	});
    	
    	if (tableView_Messages.getItems().size() > 0) {    	
    		tableView_Messages.getSelectionModel().selectFirst();
    	}
    }
    
    @FXML
    private void menuBarClick_Appointments() throws Exception {
    	System.out.println("Doctor Messages - Menu Bar Click - Appointments");

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
    	System.out.println("Doctor Messages - Menu Bar Click - Patients");

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
    	System.out.println("Doctor Messages - Menu Bar Click - Messages");
		// do nothing, already here
    }
    
    @FXML
    private void buttonClick_Reply() throws Exception {
    	System.out.println("Message Reply");
    }
    
    @FXML
    private void buttonClick_Delete() throws Exception {
    	System.out.println("Message Delete");
    }
    
    @FXML
    private void buttonClick_Call() throws Exception {
    	System.out.println("Message Call");
    }
}
