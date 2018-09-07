package main.java.org.javafx.studentsmanagementsystem.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import main.java.org.javafx.studentsmanagementsystem.controller.MainController;

public class SQLiteJDBC {
	public static void makeDb() {
		Connection c = null;
		Statement stmt = null;
		try {
			Boolean dbExists = new File("my.db").isFile();
			if (dbExists) {
				System.out.println("database \"my.db\" already exists");
			} else {
				System.out.println("Database doesn't exist. Let's make a new one");
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:my.db");
				System.out.println("Opened database successfully");
				stmt = c.createStatement();
				
				//STUDID         INTEGER PRIMARY KEY
				//we replace _rowid_ with STUDID
				//defining database tables
				String sql = "CREATE TABLE STUDENTS " + "(STUDID         INTEGER PRIMARY KEY," + " NAME           CHAR(50)," + " EMAIL          TEXT         NOT NULL, "
						+ " PASSWORD       CHAR(50)     NOT NULL) ";
				stmt.executeUpdate(sql);
				
				String sql1 = "CREATE TABLE PROFESSOR " + "(PROFID         INTEGER PRIMARY KEY," + " NAME           CHAR(50)," + " EMAIL          TEXT         NOT NULL, "
						+ " PASSWORD       CHAR(50)     NOT NULL," + " COURSE         TEXT         NOT NULL) ";
				stmt.executeUpdate(sql1);
				
				String sql2 = "CREATE TABLE ENROLLMENTS " + "(STUDID         int," + " COURSE         TEXT," + " GRADE          int," + " PRIMARY KEY(STUDID,COURSE) ) ";
				stmt.executeUpdate(sql2);
				
				System.out.println("Tables created successfully");
				stmt.close();
				c.close();
			}
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void insertProf(String name , String email , String password , String course) {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			if (emailAlreadyExistsIn("p", email)) {
				MainController.showAlert(Alert.AlertType.WARNING, "This E-mail Already exists", "Try Again!");
				return;
			}
			
			stmt = c.createStatement();
			String sql = "INSERT INTO PROFESSOR (NAME,EMAIL,PASSWORD,COURSE) " + "VALUES (" + name + "," + email + "," + password + "," + course + ");";
			stmt.executeUpdate(sql);
			
			//successful registration
			MainController.showAlert(Alert.AlertType.INFORMATION, "Successful Registration", "Now you can connect!");
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
	}
	
	public static void insertStud(String name , String email , String password) {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			if (emailAlreadyExistsIn("s", email)) {
				MainController.showAlert(Alert.AlertType.WARNING, "This E-mail Already exists", "Try Again!");
				return;
			}
			stmt = c.createStatement();
			String sql = "INSERT INTO STUDENTS (NAME,EMAIL,PASSWORD) " + "VALUES (" + name + "," + email + "," + password + ");";
			stmt.executeUpdate(sql);
			
			//successful registration
			MainController.showAlert(Alert.AlertType.INFORMATION, "Successful Registration", "Now you can connect!");
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
	}
	
	//called when the student Signs In
	public static Student findStud(String mail , String pass) {
		Connection c = null;
		Statement stmt = null;
		Boolean found = false;
		Student s = new Student("", "", -1);
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS WHERE EMAIL =" + mail + " AND PASSWORD=" + pass + ";");
			
			if (!rs.next()) {
				System.out.println("This person doesn't exist. No data");
				found = false;
			} else {
				
				s.setEmail(rs.getString("EMAIL"));
				s.setName(rs.getString("NAME"));
				s.setStudId(rs.getInt("STUDID"));
				System.out.println("This person exists!");
				found = true;
			}
			rs.close();
			stmt.close();
			c.close();
			
			System.out.println("Operation done successfully");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			return s;
		}
		
	}
	
	//--is called from mainController when the teacher signs in--//
	public static Professor findProf(String mail , String pass) {
		Connection c = null;
		Statement stmt = null;
		Boolean found = false;
		Professor prof = new Professor("", "", "", -1);
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PROFESSOR WHERE EMAIL =" + mail + " AND PASSWORD=" + pass + ";");
			
			if (!rs.next()) {
				System.out.println("This person doesn't exist. No data");
				found = false;
			} else {
				
				prof.setMail(rs.getString("EMAIL"));
				prof.setName(rs.getString("NAME"));
				prof.setId(rs.getInt("PROFID"));
				prof.setCourse(rs.getString("COURSE"));
				System.out.println("This person exists!");
				found = true;
			}
			rs.close();
			stmt.close();
			c.close();
			
			System.out.println("Operation done successfully");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			return prof;
		}
		
	}
	
	private static Boolean emailAlreadyExistsIn(String w , String mail) {
		Connection c = null;
		Statement stmt = null;
		Boolean exists = false;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			ResultSet rs;
			
			if (w.equals("s")) {
				rs = stmt.executeQuery("SELECT EMAIL FROM STUDENTS WHERE EMAIL = " + mail);
			} else {
				rs = stmt.executeQuery("SELECT EMAIL FROM PROFESSOR WHERE EMAIL = " + mail);
			}
			
			if (!rs.next()) {
				System.out.println("This email doesn't already exist. No data");
				exists = false;
			} else {
				System.out.println("This email already exists");
				exists = true;
			}
			rs.close();
			stmt.close();
			c.close();
			
			System.out.println("Searched succesfully");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			return exists;
		}
		
	}
	
	public static ArrayList<Professor> findCoursesUnregistered(Student stud) {
		ArrayList<Professor> profs = new ArrayList<>();
		
		Connection c = null;
		Statement stmt = null;
		Boolean found = false;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			
			String studid = MainController.addThingies(MainController.stud.getStudId().toString());
			
			//            SELECT * FROM PROFESSOR WHERE PROFESSOR.COURSE NOT IN(SELECT PROFESSOR.COURSE FROM PROFESSOR,ENROLLMENTS WHERE PROFESSOR.COURSE = ENROLLMENTS.COURSE AND STUDID = 3)
			ResultSet rs = stmt.executeQuery("SELECT * FROM PROFESSOR WHERE " + "PROFESSOR.COURSE NOT IN "
					+ "(SELECT PROFESSOR.COURSE FROM PROFESSOR,ENROLLMENTS WHERE PROFESSOR.COURSE = ENROLLMENTS.COURSE AND STUDID = " + studid + ");");
			
			System.out.println("Searching for courses you haven't enrolled in....");
			if (!rs.next()) {
				System.out.println("No results");
				found = false;
			} else {
				do {
					System.out.println("found a professor");
					Professor newentry = new Professor();
					newentry.setCourse(rs.getString("COURSE"));
					newentry.setId(rs.getInt("PROFID"));
					newentry.setMail(rs.getString("EMAIL"));
					newentry.setName(rs.getString("NAME"));
					
					profs.add(newentry);
				} while (rs.next());
				found = true;
			}
			rs.close();
			stmt.close();
			c.close();
			
			System.out.println("Operation done successfully");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			return profs;
		}
		
	}
	
	//--is called from stud Controller--//
	public static ArrayList<Enrollment> findCoursesRegistered(Student stud) {
		ArrayList<Enrollment> enrolls = new ArrayList<>();
		
		Connection c = null;
		Statement stmt = null;
		Boolean found = false;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			
			String studid = MainController.addThingies(MainController.stud.getStudId().toString());
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM PROFESSOR,ENROLLMENTS WHERE " + "PROFESSOR.COURSE = ENROLLMENTS.COURSE AND STUDID = " + studid + ";");
			
			System.out.println("Searching for courses you have enrolled in....");
			if (!rs.next()) {
				System.out.println("No results");
				found = false;
			} else {
				do {
					System.out.println("found an enrollment");
					Enrollment newentry = new Enrollment();
					newentry.setCourseName(rs.getString("COURSE"));
					newentry.setEnrolledStudId(rs.getInt("STUDID"));
					newentry.setGrade(rs.getInt("GRADE"));
					newentry.setProfName(rs.getString("NAME"));
					newentry.setProfId(rs.getInt("PROFID"));
					
					enrolls.add(newentry);
				} while (rs.next());
				found = true;
			}
			rs.close();
			stmt.close();
			c.close();
			
			System.out.println("Operation done successfully");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			return enrolls;
		}
	}
	
	public static void enroll(String CourseName) {
		Integer studid = MainController.stud.getStudId();
		
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			String sql = "INSERT INTO ENROLLMENTS (STUDID,COURSE) " + "VALUES (" + studid + "," + MainController.addThingies(CourseName) + ");";
			stmt.executeUpdate(sql);
			
			//successful registration
			MainController.showAlert(Alert.AlertType.INFORMATION, "Successful Enrollment", "Now you can be rated!");
			stmt.close();
			c.commit();
			c.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
		
	}
	
	//--Called by Prof Controller οταν συνδεεται ο καθηγητής--//
	public static ArrayList<Grade> findGrades() {
		ArrayList<Grade> grades = new ArrayList<>();
		
		Connection c = null;
		Statement stmt = null;
		Boolean found = false;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			
			String profscourse = MainController.addThingies(MainController.prof.getCourse());
			System.out.println("Professor's Course: " + profscourse);
			//            select * from STUDENTS where STUDID IN (select STUDID from ENROLLMENTS WHERE COURSE = 'r')
			//            SELECT * FROM STUDENTS,ENROLLMENTS WHERE STUDENTS.STUDID = ENROLLMENTS.STUDID AND COURSE = 'r'
			ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS,ENROLLMENTS WHERE" + " STUDENTS.STUDID = ENROLLMENTS.STUDID AND COURSE =" + profscourse + " ;");
			
			System.out.println("Searching for student that enrolled in your course....");
			if (!rs.next()) {
				System.out.println("No results");
				found = false;
			} else {
				do {
					System.out.println("found an enrollment");
					Grade newentry = new Grade();
					//connecting  STUDENTS columns to grades class properties
					newentry.setStudName(rs.getString("NAME"));
					newentry.setStudId(rs.getInt("STUDID"));
					newentry.setGrade(rs.getInt("GRADE"));
					
					grades.add(newentry);
				} while (rs.next());
				found = true;
			}
			rs.close();
			stmt.close();
			c.close();
			
			System.out.println("Operation done successfully");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		} finally {
			return grades;
		}
	}
	
	//Βαθμολόγηση του καθηγητή
	public static void grade(Grade gr) {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:my.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			String profsrate = MainController.addThingies(gr.getGrade().toString());
			String studid = MainController.addThingies(gr.getStudId().toString());
			String profscourse = MainController.addThingies(MainController.prof.getCourse());
			
			System.out.println("\ngrade: " + profsrate + "\nSTUDID: " + studid + "\nPROFID" + profscourse);
			
			stmt = c.createStatement();
			String sql = "UPDATE ENROLLMENTS SET GRADE = " + profsrate + " WHERE " + "(STUDID=" + studid + " AND COURSE=" + profscourse + ");";
			stmt.executeUpdate(sql);
			
			//successful registration
			MainController.showAlert(Alert.AlertType.INFORMATION, "Successful Rating", "Now you can see the grade you gave!");
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
		
	}
	
}
