package jpa.dao;

import java.util.List;
import jpa.entitymodels.Course;

public interface CourseDAO {
	public List<Course> getAllCourses();
}
