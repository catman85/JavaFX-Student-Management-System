package main.java.org.javafx.studentsmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import main.java.org.javafx.studentsmanagementsystem.application.Main;
import main.java.org.javafx.studentsmanagementsystem.model.Professor;
import main.java.org.javafx.studentsmanagementsystem.model.SQLiteJDBC;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class RegisterController {
	
	@FXML
	private TableView<Professor> registerTableView;
	@FXML
	private TableColumn<Professor,String> registerCourseColumn;
	@FXML
	private TableColumn<Professor,String> registerProfIdColumn;
	@FXML
	private TableColumn<Professor,String> registerProfColumn;
	
	@FXML
	private ImageView studRegisterBack;
	@FXML
	private Button studRegisterBtn;
	
	private ArrayList<Professor> profs = SQLiteJDBC.findCoursesUnregistered(MainController.stud);
	private ObservableList<Professor> registerData = FXCollections.observableArrayList();
	
	//    private ObservableList<Professor> registerData = FXCollections.observableArrayList(
	//
	//            new Professor("testname","id","course",-1),
	//            new Professor("testname","id","course",-1),
	//            new Professor("te125stname","idgdgd","cosfurse",6)
	//    );
	
	//initialize runs BEFORE the constructor
	@FXML
	private void initialize() {
		addData();
		
		// Initialize the columns.
		registerCourseColumn.setCellValueFactory(
				//"course" is the name of the property inside the Professor Class
				new PropertyValueFactory<Professor,String>("course"));
		registerProfIdColumn.setCellValueFactory(new PropertyValueFactory<Professor,String>("id"));
		registerProfColumn.setCellValueFactory(new PropertyValueFactory<Professor,String>("name"));
		
		// Add data to the table
		registerTableView.setItems(registerData);
	}
	
	private void addData() {
		for (int j = 0; j < profs.size(); j++) {
			registerData.addAll(FXCollections.observableArrayList(profs.get(j)));
		}
	}
	
	@FXML
	private void onRegisterBackBtnClicked() {
		System.out.println("Going to back to student's dashboard fxml");
		try {
			//--------------------Go to Login Page -------------
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.FXMLS + "StudentController.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Main.setContent(root1, "Student Home");
		} catch (Exception es) {
			es.printStackTrace();
		}
	}
	
	@FXML
	private void onRegisterBtnClicked() {
		System.out.println("Trying to regsister on a course");
		
		//pro is the Professor object that gets the attributes of the selected row
		Professor pro = registerTableView.getSelectionModel().getSelectedItem();
		System.out.println(pro.getCourse());
		
		SQLiteJDBC.enroll(pro.getCourse());
		
		//remove selected row
		registerTableView.getItems().removeAll(registerTableView.getSelectionModel().getSelectedItem());
		
	}
}
