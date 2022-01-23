package jpa.entitymodels;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer cId;

	@Column(name = "name", length = 50, nullable = false)
	private String cName;

	@Column(name = "instructor", length = 50, nullable = false)
	private String cInstructorName;

	@ManyToMany(mappedBy = "sCourses", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Student> students = new ArrayList<Student>();

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcInstructorName() {
		return cInstructorName;
	}

	public void setcInstructorName(String cInstructorName) {
		this.cInstructorName = cInstructorName;
	}

	public Integer getcId() {
		return cId;
	}

	public Course(Integer id, String name, String instructorName) {
		this.cId = id;
		this.cName = name;
		this.cInstructorName = instructorName;
	}

	public Course() {

	}

	public List<Student> getCourses() {
		return students;
	}

	public void setCourses(List<Student> students) {
		this.students = students;
	}
//	
}
