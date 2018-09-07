package main.java.org.javafx.studentsmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.org.javafx.studentsmanagementsystem.application.mainController;
import main.java.org.javafx.studentsmanagementsystem.model.Grade;
import main.java.org.javafx.studentsmanagementsystem.model.SQLiteJDBC;

public class profController {
	@FXML
	private Label profControllerId;
	@FXML
	private Label profControllerName;
	
	@FXML
	private TableView<Grade> profTableView;
	@FXML
	private TableColumn<Grade,String> profStudIdColumn;
	@FXML
	private TableColumn<Grade,String> profStudNameColumn;
	@FXML
	private TableColumn<Grade,String> profGradeColumn;
	
	@FXML
	private ImageView profSignOut;
	
	private ArrayList<Grade> grades = SQLiteJDBC.findGrades();
	
	private ObservableList<Grade> registeredData = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() {
		profControllerId.setText(mainController.prof.getId().toString());
		profControllerName.setText(mainController.prof.getName());
		
		addData();
		
		profStudIdColumn.setCellValueFactory(
				//"CourseName" is taken from Grade.java class
				new PropertyValueFactory<Grade,String>("studId"));
		
		profStudNameColumn.setCellValueFactory(new PropertyValueFactory<Grade,String>("StudName"));
		
		profGradeColumn.setCellValueFactory(new PropertyValueFactory<Grade,String>("grade"));
		
		//Add data to the table
		profTableView.setItems(registeredData);
	}
	
	public void onButtonClickProf(ActionEvent e) {
		Grade gr = null;
		Integer grade = null;
		System.out.println("Trying to grade a student...");
		
		try {
			gr = profTableView.getSelectionModel().getSelectedItem();
		} catch (java.lang.NullPointerException es) {
			mainController.showAlert(Alert.AlertType.WARNING, "You need to choose someone", "You haven't selected a student!");
			es.printStackTrace();
			return;
		}
		
		TextInputDialog dialog = new TextInputDialog("10");
		dialog.setTitle("Student Grade");
		dialog.setHeaderText("Provide a Grade");
		dialog.setContentText("Write a number (0-10):");
		
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			
			System.out.println("Your grade: " + result.get());
		}
		
		// The Java 8 way to get the response value (with lambda expression).
		//        result.ifPresent(name -> System.out.println("Your grade: " + name));
		
		try {
			grade = Integer.parseInt(result.get());
			if (!between(grade, 0, 10)) {
				mainController.showAlert(Alert.AlertType.WARNING, "Wrong Input", "Grade must be an integer between 0 and 10");
				return;
			}
		} catch (java.lang.NumberFormatException ex) {
			mainController.showAlert(Alert.AlertType.WARNING, "Wrong Input", "Grade must be an integer between 0 and 10");
			ex.printStackTrace();
			return;
		}
		
		try {
			
			gr.setGrade(grade);
			System.out.println("Επιτυχής Βαθμολόγηση");
		} catch (java.lang.RuntimeException ex) {
			mainController.showAlert(Alert.AlertType.WARNING, "You need to choose someone", "You haven't selected a student!");
			ex.printStackTrace();
			return;
		}
		
		SQLiteJDBC.grade(gr);
		
		//refresh table contents
		registeredData.removeAll(registeredData);
		this.initialize();
		
	}
	
	private void addData() {
		for (int j = 0; j < grades.size(); j++) {
			registeredData.addAll(FXCollections.observableArrayList(grades.get(j)));
		}
		
	}
	
	@FXML
	private void onProfSignOut() {
		System.out.println("Going back to login Screen");
		try {
			//new stage
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Εκπαιδευτικό Ίδρυμα");
			stage.setScene(new Scene(root1));
			stage.show();
			//close current stage
			Stage finale = (Stage) profControllerId.getScene().getWindow();
			finale.close();
		} catch (Exception es) {
			es.printStackTrace();
		}
	}
	
	private static boolean between(int i , int minValueInclusive , int maxValueInclusive) {
		return ( i >= minValueInclusive && i <= maxValueInclusive );
	}
}
