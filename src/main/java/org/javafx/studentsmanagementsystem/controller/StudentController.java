package main.java.org.javafx.studentsmanagementsystem.controller;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.org.javafx.studentsmanagementsystem.application.Main;
import main.java.org.javafx.studentsmanagementsystem.model.Enrollment;
import main.java.org.javafx.studentsmanagementsystem.model.SQLiteJDBC;

public class StudentController {
	
	@FXML
	private Button enrollButton;
	
	@FXML
	private Label studControllerID;
	
	@FXML
	private Label studControllerName;
	
	@FXML
	private JFXButton signOut;
	
	@FXML
	private TableView<Enrollment> studTableView;
	@FXML
	private TableColumn<Enrollment,String> studEnrolledCoursesColumn;
	@FXML
	private TableColumn<Enrollment,String> studEnrolledProfId;
	@FXML
	private TableColumn<Enrollment,String> studEnrolledProfName;
	@FXML
	private TableColumn<Enrollment,String> studEnrolledGrade;
	
	private ArrayList<Enrollment> enroll = SQLiteJDBC.findCoursesRegistered(MainController.stud);
	
	private ObservableList<Enrollment> registeredData = FXCollections.observableArrayList();
	
	@FXML
	public void initialize() {
		System.out.println("Entered Student Initialize Method...");
		
		
		studControllerID.setText(MainController.stud.getStudId().toString());
		studControllerName.setText(MainController.stud.getName().toString());
		
		addData();
		
		studEnrolledCoursesColumn.setCellValueFactory(
				//"CourseName" is taken from Enrollment.java class
				new PropertyValueFactory<Enrollment,String>("CourseName"));
		
		studEnrolledProfId.setCellValueFactory(new PropertyValueFactory<Enrollment,String>("ProfId"));
		
		studEnrolledProfName.setCellValueFactory(new PropertyValueFactory<Enrollment,String>("ProfName"));
		
		studEnrolledGrade.setCellValueFactory(new PropertyValueFactory<Enrollment,String>("Grade"));
		
		//Add data to the table
		studTableView.setItems(registeredData);
		
		//enrollButton
		enrollButton.setOnAction(a -> {
			System.out.println("Going to registration fxml");
			try {
				
				//--------------------Go to Login Page -------------
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.FXMLS + "RegisterController.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Main.setContent(root1, "Register to lesson");
				
			} catch (Exception es) {
				es.printStackTrace();
			}
		});
		
		System.err.println("Adding Student sign out action");
		//signOut
		signOut.setOnAction(a -> {
			System.out.println("Going back to login Screen");
			try {
				//--------------------Go to Login Page -------------
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.FXMLS + "MainController.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Main.setContent(root1, "Login Home");
			} catch (Exception es) {
				es.printStackTrace();
			}
		});
		
	}
	
	private void addData() {
		for (int j = 0; j < enroll.size(); j++) {
			registeredData.addAll(FXCollections.observableArrayList(enroll.get(j)));
		}
	}
	
}
