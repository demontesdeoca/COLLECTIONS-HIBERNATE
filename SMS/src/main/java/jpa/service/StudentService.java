package jpa.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

public class StudentService implements StudentDAO {

	@SuppressWarnings("unchecked")
	public List<Student> getAllStudents() {
		Transaction transaction = null;
		List<Student> students = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get student object
			students = session.createQuery("from Student").list();
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return students;
	}

	public Student getStudentByEmail(String sEmail) {
		Transaction transaction = null;
		Student student = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get student object
			student = session.get(Student.class, sEmail);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return student;
	}

	public boolean validateStudent(String sEmail, String sPassword) {
		Student student = getStudentByEmail(sEmail);
		if (student != null) {
			if (student.getPassword().equals(sPassword))
				return true;
			else
				return false;
		} else
			return false;
	}

	public void registerStudentToCourse(String sEmail, Integer cId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			Student student = getStudentByEmail(sEmail);
			List<Course> student_courses = getStudentCourses(sEmail);
			CourseService courseService = new CourseService();
			Course course = courseService.getCourseById(cId);
			if (course != null) {
				boolean bContains = false;
				for (Course a : student_courses) {
					if (a.getcId() == cId) {
						bContains = true;
						break;
					}
				}
				if (!bContains) {
					student_courses.add(course);
					student.setStudent_courses(student_courses);
					transaction = session.beginTransaction();
					session.saveOrUpdate(student);
					transaction.commit();
					System.out.println("\nClass succesfully added!!\n");
				} else
					System.out.println("\nStudent is already registered in this class.\n");
			} else
				System.out.println("Course does not exist.");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public List<Course> getStudentCourses(String sEmail) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Student student = getStudentByEmail(sEmail);
			return student.getStudent_courses();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void displayStudentCourses(String sEmail) {
		try {
			Student student = getStudentByEmail(sEmail);
			List<Course> courses = getStudentCourses(sEmail);
			if (courses.isEmpty()) {
				System.out.println("\n" + student.getName() + " has no courses signed up yet\n");
				return;
			}
			System.out.printf("\nCourses of: %-50s\n", student.getName() + "  -  " + student.getEmail());
			System.out.print("ID   Course Name                   Instructor Name           \n");
			System.out.print("-------------------------------------------------------------\n");
			courses.forEach(
					s -> System.out.printf("%-5s%-30s%-30s\n", s.getcId(), s.getcName(), s.getcInstructorName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean saveStudent(Student student) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			session.save(student);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public void Init() {
		// Insert records to table STudents
		System.out.println("Loading records from resources/Student.sql\n");
		HibernateUtil hbObject = new HibernateUtil();
		Scanner input = null;
		File file = null;
		try {
			file = new File("src/main/resources/Student.sql");
			input = new Scanner(file);
			String data = "";
			while (input.hasNextLine()) {
				data = input.nextLine();
				hbObject.insertRecordSQL(data);
			}
			System.out.println("Records from resources/Student.sql succesfully loaded\n");
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} finally {
			input.close();
		}
	}
}
