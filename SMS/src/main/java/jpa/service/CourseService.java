package jpa.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;

public class CourseService implements CourseDAO {

	@SuppressWarnings("unchecked")
	public List<Course> getAllCourses() {
		Transaction transaction = null;
		List<Course> courses = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get courses object
			courses = session.createQuery("from Course").list();
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return courses;
	}

    //Display all the courses
	public void displayCourses() {
		List<Course> courses = getAllCourses();
		System.out.print("\nAll Courses:\n");
		System.out.print("ID   Course Name                   Instructor Name           \n");
		System.out.print("-------------------------------------------------------------\n");
		courses.forEach(s -> System.out.printf("%-5s%-30s%-30s\n", s.getcId(), s.getcName(), s.getcInstructorName()));
	}
	
	public Course getCourseById(Integer cID) {
		Transaction transaction = null;
		Course course = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get course object
			course = session.get(Course.class, cID);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return course;
	}
	
	public void Init() {
		System.out.println("Loading records from resources/Course.sql\n");
		HibernateUtil hbObject = new HibernateUtil();
		Scanner input = null;
		File file = null;
		// Insert records to table courses
		try {
			file = new File("src/main/resources/Course.sql");
			input = new Scanner(file);
			String data = "";
			while (input.hasNextLine()) {
				data = input.nextLine();
				hbObject.insertRecordSQL(data);
			}
			System.out.println("Records from resources/Course.sql succesfully loaded\n");
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} finally {
			input.close();
		}

	}
}
