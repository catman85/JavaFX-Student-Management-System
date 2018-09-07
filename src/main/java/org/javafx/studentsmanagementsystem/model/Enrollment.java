package main.java.org.javafx.studentsmanagementsystem.model;

public class Enrollment {
	private Integer EnrolledStudId;
	private String CourseName;
	private Integer Grade;
	private String ProfName;
	private Integer ProfId;
	
	public Integer getProfId() {
		return ProfId;
	}
	
	public void setProfId(Integer profId) {
		ProfId = profId;
	}
	
	public Integer getEnrolledStudId() {
		return EnrolledStudId;
	}
	
	public void setEnrolledStudId(Integer enrolledStudId) {
		EnrolledStudId = enrolledStudId;
	}
	
	public String getCourseName() {
		return CourseName;
	}
	
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	
	public Integer getGrade() {
		return Grade;
	}
	
	public void setGrade(Integer grade) {
		Grade = grade;
	}
	
	public String getProfName() {
		return ProfName;
	}
	
	public void setProfName(String profName) {
		ProfName = profName;
	}
}
