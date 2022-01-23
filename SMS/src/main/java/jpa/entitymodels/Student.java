package jpa.entitymodels;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@Column(name = "email", length = 50, nullable = false)
	private String sEmail;

	@Column(name = "name", length = 50, nullable = false)
	private String sName;

	@Column(name = "password", length = 50, nullable = false)
	private String sPass;

	// fetch = FetchType.EAGER, 
	//referencedColumnName
	
	@ManyToMany(targetEntity = Course.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "STUDENT_COURSES", joinColumns = { @JoinColumn(name = "email") }, inverseJoinColumns = {
			@JoinColumn(name = "id") })
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<Course> sCourses = new ArrayList<Course>();

	public String getEmail() {
		return sEmail;
	}

	public void setEmail(String email) {
		this.sEmail = email;
	}

	public String getName() {
		return sName;
	}

	public void setName(String name) {
		this.sName = name;
	}

	public String getPassword() {
		return sPass;
	}

	public void setPassword(String password) {
		this.sPass = password;
	}

	public Student() {
		this.sEmail = "";
		this.sName = "";
		this.sPass = "";
		sCourses = new ArrayList<Course>();
	}

	public Student(String email, String name, String password) {
		this.sEmail = email;
		this.sName = name;
		this.sPass = password;
		sCourses = new ArrayList<Course>();
	}

	public List<Course> getStudent_courses() {
		return this.sCourses;
	}

	public void setStudent_courses(List<Course> sCourses) {
		this.sCourses = sCourses;
	}
}