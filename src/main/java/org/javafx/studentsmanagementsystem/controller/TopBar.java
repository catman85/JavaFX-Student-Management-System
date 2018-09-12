/*
 * 
 */
package main.java.org.javafx.studentsmanagementsystem.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import main.java.org.javafx.studentsmanagementsystem.application.Main;

/**
 * The Top bar of the application Window.
 *
 * @author GOXR3PLUS
 */
public class TopBar extends BorderPane {
	
	// ----------------------------------------------
	
	@FXML
	private Label label;
	
	// ----------------------------------------------
	
	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * Constructor.
	 */
	public TopBar() {
		
		//---------------------FXML LOADER---------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(Main.FXMLS + "TopBar.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			logger.log(Level.WARNING, "", ex);
		}
	}
	
	/**
	 * Called as soon as .fxml is initialized [[SuppressWarningsSpartan]]
	 */
	@FXML
	private void initialize() {
		
		//Root
		setRight(new CloseAppBox());
		
	}
	
	public void setTitle(String title) {
		label.setText(title);
	}
	
}
