package jpa.mainrunner;

import java.util.InputMismatchException;
import java.util.Scanner;

import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;

/******************************************************************************************************
 ******************************************************************************************************
 *
 * DEVELOPED BY: DAVID MONTESDEOCA FOR PERSCHOLAS AD - SBA - Core
 * Java/Hibernate/JUnit ASSIGNMENT
 * 
 * DATE: NOVEMBER/2021
 * 
 ******************************************************************************************************
 *
 * THIS APP USES HSQLDB, Junit4. 
 * NOTE: All DB objects will be created with Hibernate in a memory DB.
 * 
 * To log in into the system the email: test@test.com and password: secret can
 * be used to test app.
 * 
 * Student records and courses records are loaded into DB from files located in
 * folder \src\main\resources when App starts.
 * 
 ******************************************************************************************************
 ****************************************************************************************************** 
 */
public class SMSRunner {

	public static void mainMenu() {
		System.out.println("\n  School Management System");
		System.out.println("----------------------------");
		System.out.println("  Choose One action:");
		System.out.println("  [1] Student Log in");
		System.out.println("  [2] Student Sign up");
		System.out.println("  [3] Quit");
	}

	public static void secondMenu() {
		System.out.println("\n  School Management System");
		System.out.println("----------------------------");
		System.out.println("  Choose One action:");
		System.out.println("  [1] Register to Class");
		System.out.println("  [2] Log out");
	}

	public static void main(String[] args) {
		StudentService studentService = new StudentService();
		CourseService courseService = new CourseService();

		studentService.Init(); // load records into DB from file \resources\Sudent.sql
		courseService.Init(); // load records into DB from file \resources\Course.sql

		String option = "";
		Scanner input = new Scanner(System.in);
		String email = "";
		String password = "";
		String name = "";
		Integer classID = 0;
		boolean tryAgain = true;
		while (option.trim() != "3") {
			mainMenu();
			option = input.nextLine();
			switch (option) {
			case "1":
				System.out.print("\nEnter your email:\n");
				email = input.nextLine();
				System.out.print("\nEnter your password:\n");
				password = input.nextLine();
				if (studentService.validateStudent(email, password)) {
					System.out.printf("\nWelcome %-20s\n", studentService.getStudentByEmail(email).getName());
					studentService.displayStudentCourses(email);
					while (option != "2") {
						secondMenu();
						option = input.nextLine();
						switch (option) {
						case "1":
							courseService.displayCourses();
							tryAgain = true;
							while (tryAgain) {
								try {
									System.out.print("\nWhich Class?\n");
									classID = input.nextInt();
									tryAgain = false;
								} catch (InputMismatchException e) {
									System.out.println("\nIncorrect ID class selected, please try again\n");
									input.next();
								}
							}
							studentService.registerStudentToCourse(email, classID);

							input.nextLine();
							studentService.displayStudentCourses(email);
							System.out.println("\nPress ENTER to continue");
							input.nextLine();
							break;
						case "2":
							System.out.println("\nYou have been signed out. Going back to main manu....\n");
							option = "2";
							break;
						default:
							System.out
									.println("\nOption selected not available, try again. Press ENTER to continue....");
							input.nextLine();
							break;
						}
					}
					option = "";
				} else
					System.out.print("\nStudent not found. Email or Password not correct. Try again\n");
				break;
			case "2":
				tryAgain = true;
				while (tryAgain) {
					System.out.print("\nEnter your email:\n");
					email = input.nextLine();
					if (email.trim().equals("")) {
						System.out.print("\nInvalid email. Try again. Press ENTER to continue...\n");
						input.nextLine();
					} else
						tryAgain = false;
				}

				tryAgain = true;
				while (tryAgain) {
					System.out.print("\nEnter your name:\n");
					name = input.nextLine();
					if (name.trim().equals("")) {
						System.out.print("\nInvalid name. Try again. Press ENTER to continue...\n");
						input.nextLine();
					} else
						tryAgain = false;
				}
				
				tryAgain = true;
				while (tryAgain) {
					System.out.print("\nEnter your password:\n");
					password = input.nextLine();
					if (password.trim().equals("")) {
						System.out.print("\nInvalid password. Try again. Press ENTER to continue...\n");
						input.nextLine();
					} else
						tryAgain = false;
				}

				if (studentService.getStudentByEmail(email) != null) {
					System.out.print("\nStudent is already signed up. Try a different email. Press ENTER to continue...\n");
					input.nextLine();
					break;
				}
				
				if (studentService.saveStudent(new Student(email, name, password))) {
					System.out.print("\nRecord succesfully saved. Press ENTER to continue...\n");
					input.nextLine();
				}
				break;
			case "3":
				System.out.println("\nExiting...Bye!!");
				option = "3";
				break;
			default:
				System.out.println("\nOption selected not available, try again. Press ENTER to continue...");
				input.nextLine();
				break;
			}
		}
		input.close();
		System.exit(0);
	}

}
