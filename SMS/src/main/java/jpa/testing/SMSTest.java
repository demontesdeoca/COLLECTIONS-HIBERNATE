package jpa.testing;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.*;

public class SMSTest {
	static StudentService studentService = new StudentService();

	@BeforeClass
	public static void init() {
		studentService.Init();
	}

	@Test
	public void testStudentByEmail() {
		String sEmail = "sbowden1@yellowbook.com";
		Student student = studentService.getStudentByEmail(sEmail);
		Assert.assertEquals("Checks if existes student with email " + sEmail, sEmail, student.getEmail());
	}

	@Test
	public void testValidateStudent() {
		String sEmail = "sbowden1@yellowbook.com";
		String sPassword = "SJc4aWSU";
		Assert.assertTrue(studentService.validateStudent(sEmail, sPassword));
	}

	@Test
	public void testGetAllStudents() {
		List<Student> student = studentService.getAllStudents();
		Assert.assertTrue(!student.isEmpty());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testStudentNoPublicField() {
		Field firstField = Student.class.getFields()[0];
		Assert.fail("Item should only contain the private instance variables " + firstField.getName());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testCourseNoPublicField() {
		Field firstField = Course.class.getFields()[0];
		Assert.fail("Item should only contain the private instance variables " + firstField.getName());
	}
}
