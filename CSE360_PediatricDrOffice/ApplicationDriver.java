import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
 
public class ApplicationDriver extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	/*
    	// for initial creation of data text file - TODO comment out in production
    	ITService currentITService = new ITService();
    	currentITService.createDataAndPrintToFile();
    	*/
    	
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ApplicationDriver.class.getResource("ui_Login.fxml"));
        BorderPane borderPane = loader.<BorderPane>load();

        ui_LoginController controller = (ui_LoginController)loader.getController();
        controller.initializeController(primaryStage);
        
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}