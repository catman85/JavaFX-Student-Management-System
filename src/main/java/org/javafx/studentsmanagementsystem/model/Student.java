package main.java.org.javafx.studentsmanagementsystem.model;

public class Student {
	private String name;
	private String email;
	private Integer studId = -1;
	
	public Student(String name, String email, Integer id) {
		this.name = name;
		this.email = email;
		this.studId = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getStudId() {
		return studId;
	}
	
	public void setStudId(Integer studId) {
		this.studId = studId;
	}
}
