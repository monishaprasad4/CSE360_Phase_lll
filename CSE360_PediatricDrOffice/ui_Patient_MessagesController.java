import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class ui_Patient_MessagesController {

	private Stage primaryStage;
	
	@FXML private Button sendMessage;
	@FXML private TextField subjectTextField;
	@FXML private TextArea bodyTextArea;
	@FXML private TextArea messageDetailsTextArea;
	@FXML private ListView<String> msgListView;
	@FXML private ComboBox<String> recipientType;
	@FXML private Label labelNotification;
	@FXML private MenuBar menuBar;
	@FXML private Menu menuBar_Account;
	@FXML private Menu menuBar_VisitHistory;
	@FXML private Menu menuBar_Messages;
	@FXML private Menu menuBar_ScheduleAppt;
	@FXML private Menu menuBar_Doctors;
	private ITService currentITService;
	private User currentUser;
	private ArrayList<Message> messages;
	private ArrayList<String> messagesObservableList;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public ui_Patient_MessagesController() {
    	
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
    	
    	recipientType.getItems().addAll(
    			"Nurse",
    			"Doctor"
    			);
    	
    	// get messages for current user (Patient)
    	messages = currentITService.getMessagesForUser(currentUser);
    	
    	// create an observable list with only a string to display for Message History section
    	updateListView();
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

    	// do nothing, already here
    }
    
    @FXML
    private void menuBarClick_ScheduleAppt() throws Exception {
    	System.out.println("Patient Account - Menu Bar Click - Schedule Appt");

    	// switch UI
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Patient_Schedule Appt.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();
        
        ui_Patient_ScheduleApptController controller = (ui_Patient_ScheduleApptController)loader.getController();
        controller.initializeController(primaryStage, currentUser, currentITService);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
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
    private void ButtonHandlerSendMessage(ActionEvent event) throws Exception {
    	System.out.println("Patient Messages - send message button action handler");
    
	    // clear previous error messages
		labelNotification.setText("");
		
		// if any field is empty, set isEmptyFields flag to true
        Boolean emptyFieldCheck = false;
        if (subjectTextField.getText() == null || subjectTextField.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (bodyTextArea.getText() == null || bodyTextArea.getText().trim().isEmpty()) {
        	emptyFieldCheck = true;
        }
        if (recipientType.getValue() == null) {
        	emptyFieldCheck = true;        	
        }

        if (emptyFieldCheck) {
            System.out.println("Patient Messages - ERROR - at least one field is empty");
            // display error message if there are empty fields
        	labelNotification.setText("Please fill out all fields");
        } else {
            // all fields are input, create message
        	Message tempMessage = new Message();

            if (recipientType.getValue().toString().equals("Nurse")) {
                System.out.println("Account Type Nurse Selected");
                tempMessage.setRecipient(UserType.NURSE);
    	    } else if (recipientType.getValue().toString().equals("Doctor")) {
                System.out.println("Account Type Doctor Selected");
                tempMessage.setRecipient(UserType.DOCTOR);
                // also add doctor unique ID
                tempMessage.setReceiverUniqueID(((Patient)currentUser).getDoctorUniqueID());
    	    }
            
            tempMessage.setSubject(subjectTextField.getText());
            tempMessage.setBody(bodyTextArea.getText());
            tempMessage.setSenderUniqueID(currentUser.getUniqueID());
            tempMessage.setSentDate(new Date());

            // update database
            currentITService.newMessage(tempMessage);
            
            // update local messages (add to front/head)
            messages.add(0, tempMessage);
            updateDisplayedMessageDetails(0);
            
            // update UI 
            updateListView();
            subjectTextField.setText("");
            bodyTextArea.setText("");
            recipientType.setValue(null);
        }
    }
    
    private void updateListView() {
    	messagesObservableList = new ArrayList<String>();
    	for (int i = 0; i < messages.size(); i++) {
    		messagesObservableList.add(messages.get(i).toString_MessageHistory());
		}    	
    	
    	msgListView.setItems(FXCollections.observableArrayList(messagesObservableList));
    }
    
    @FXML
    private void onMouseClickListViewHandler(MouseEvent event) throws Exception {
    	System.out.println("Patient Messages - list view click action handler");
    	
    	// get list selection 
    	int currentSelectionIndex = msgListView.getSelectionModel().getSelectedIndex();
    	System.out.println("Patient Messages - list view click action handler - currentSelectionIndex: " + currentSelectionIndex);
    	
    	if (currentSelectionIndex < 0) {
    		// no selection 
    	} else {
    		// selection made 
    		
        	updateDisplayedMessageDetails(currentSelectionIndex);
    	}
    }
    
    private void updateDisplayedMessageDetails(int index) {
    	messageDetailsTextArea.setText(messages.get(index).toString());
    }
}
