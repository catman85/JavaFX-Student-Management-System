package main.java.org.javafx.studentsmanagementsystem.model;

public class Professor {
	private String name;
	private String mail;
	private String course;
	private Integer id = -1;
	
	public Professor(String name, String mail, String course, Integer id) {
		this.name = name;
		this.mail = mail;
		this.course = course;
		this.id = id;
	}
	
	public Professor() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void setCourse(String course) {
		this.course = course;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMail() {
		return mail;
	}
	
	public String getCourse() {
		return course;
	}
	
	public Integer getId() {
		return id;
	}
}
