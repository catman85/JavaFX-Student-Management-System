package main.java.org.javafx.studentsmanagementsystem.controller;

import java.io.IOException;

import org.kordamp.ikonli.javafx.StackedFontIcon;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import main.java.org.javafx.studentsmanagementsystem.application.Main;

public class CloseAppBox extends StackPane {
	
	//--------------------------------------------------------------
	
	@FXML
	private JFXButton minimize;
	
	@FXML
	private JFXButton maxOrNormalize;
	
	@FXML
	private StackedFontIcon sizeStackedFontIcon;
	
	@FXML
	private JFXButton exitApplication;
	
	// -------------------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public CloseAppBox() {
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(Main.FXMLS + "CloseAppBox.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Called as soon as fxml is initialized
	 */
	@FXML
	private void initialize() {
		
		
		
		// minimize
		minimize.setOnAction(ac -> Main.window.setIconified(true));
		
		// maximize_normalize
		maxOrNormalize.setOnAction(ac -> Main.borderlessScene.maximizeStage());
		
		// close
		exitApplication.setOnAction(ac -> System.exit(0));
		
	}
	
}
