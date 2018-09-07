package main.java.org.javafx.studentsmanagementsystem.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.org.javafx.studentsmanagementsystem.model.SQLiteJDBC;

public class Main extends Application {
	
	public final static String FXMLS = "/fxml/";
	public final static String IMAGES = "/iamges/";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(FXMLS + "main.fxml"));
		primaryStage.setTitle("School");
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
		SQLiteJDBC.makeDb();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}