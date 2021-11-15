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
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

public class ui_Nurse_MessagesController {

	private Stage primaryStage;
	
	@FXML private Menu menuBar_Appointments;
	@FXML private Menu menuBar_Patients;
	@FXML private Menu menuBar_Messages;
	
	@FXML private TableView<MessageDataView> tableView_Messages;
    @FXML private TableColumn<MessageDataView, String> fromColumn;
    @FXML private TableColumn<MessageDataView, String> toColumn;
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

    public ui_Nurse_MessagesController() {
    	
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
    	toColumn.setCellValueFactory(new PropertyValueFactory<MessageDataView, String>("to"));
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
    	
    	updateMessageView();
    }
    
    @FXML
    private void menuBarClick_Appointments() throws Exception {
    	System.out.println("Nurse Messages - Menu Bar Click - Appointments");

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
    	System.out.println("Nurse Messages - Menu Bar Click - Patients");

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
    
    @FXML
    private void menuBarClick_Messages() throws Exception {
    	System.out.println("Nurse Messages - Menu Bar Click - Messages");

    	// do nothing, already here
    }
    
    @FXML
    private void buttonClick_Reply() throws Exception {
    	System.out.println("Message Reply");
    }
    
    @FXML
    private void buttonClick_Delete() throws Exception {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this message?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Doctor's Office Portal");
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
        if (ButtonType.YES.equals(result)) {
	    	if (tableView_Messages.getSelectionModel().getSelectedIndex() >= 0) 
	    	{
	    		Message message = tableView_Messages.getSelectionModel().getSelectedItem().getMessage();
	    		System.out.println("Delete message YES! " + message.getSubject());
	    		currentITService.deleteMessage(message.getMessageUniqueID());
	    		updateMessageView();
	    	}
        }
    }
    
    @FXML
    private void buttonClick_Call() throws Exception {
    	System.out.println("Message Call");
    }
    
    private void updateMessageView()
    {
    	ArrayList<Message> list = currentITService.getMessages();
    	
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
}
