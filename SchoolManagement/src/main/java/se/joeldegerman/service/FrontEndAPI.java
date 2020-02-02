package se.joeldegerman.service;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se.joeldegerman.domain.Course;
import se.joeldegerman.domain.Education;
import se.joeldegerman.domain.Student;
import se.joeldegerman.domain.Teacher;

@Component
public class FrontEndAPI {

	@Autowired
	SchoolService schoolService;

	/*
	 * For CourseController, CourseEducationController and CourseTeacherController
	 */

	public Course createCourse(String subject) {
		return schoolService.createCourse(subject);
	}

	public void deleteCourse(int idOfCourse) {
		Course course = schoolService.getCourseByIdWithTeachersAndEducations(idOfCourse);
		Set<Education> educations = schoolService.getAllEducationsWithCourses();
		Set<Teacher> teachers = schoolService.getAllTeachersWithCourses();
		System.out.println(course.getEducations() + " | " + course.getTeachers());

		// Removes the connection in the DB between course and education to be able to
		// remove it safely.
		for (Education education : educations) {
			education.removeCourseFromEducation(course);
			schoolService.updateEducation(education);
		}
		for (Teacher teacher : teachers) {
			teacher.removeCourseFromTeacher(course);
			schoolService.updateTeacher(teacher);
		}
		schoolService.deleteCourse(course);
	}

	public ObservableList<Course> getAllCoursesAsObservableList() {
		ObservableList<Course> observableListOfCourses = FXCollections.observableArrayList();

		Set<Course> courses = schoolService.getAllCourses();
		for (Course course : courses) {
			observableListOfCourses.add(course);
		}
		return observableListOfCourses;
	}

	public ObservableList<Teacher> getAllTeachersFromAListCourseAsObservableList(int idOfCourse) {
		ObservableList<Teacher> observableListOfTeachersConnectodToCourse = FXCollections.observableArrayList();

		Course foundCourse = schoolService.getCourseByIdWithTeachers(idOfCourse);
		Set<Teacher> teachers = foundCourse.getTeachers();
		for (Teacher teacher : teachers) {
			observableListOfTeachersConnectodToCourse.add(teacher);
		}
		return observableListOfTeachersConnectodToCourse;
	}

	public ObservableList<Education> getAllEducationsFromACourseAsObservableList(int idOfCourse) {
		ObservableList<Education> observableListOfEducationsConnectodToCourse = FXCollections.observableArrayList();

		Course foundCourse = schoolService.getCourseByIdWithEducations(idOfCourse);
		Set<Education> educations = foundCourse.getEducations();
		for (Education education : educations) {
			observableListOfEducationsConnectodToCourse.add(education);
		}
		return observableListOfEducationsConnectodToCourse;
	}

	/*
	 * For EducationController, EducationStudentController and
	 * EducationCourseController
	 */

	public Education createEducation(String name, LocalDate startDate, LocalDate endDate) {
		return schoolService.createEducation(name, startDate, endDate);
	}

	public void deleteEducation(int idOfEducation) {
		Education education = schoolService.getEducationByIdWithCoursesAndStudents(idOfEducation);
		for (Student student : education.getStudents()) {
			student.setEducation(null);
		}
		for (Course course : education.getCourses()) {
			education.getCourses().remove(course);
		}
		schoolService.updateEducation(education);
		schoolService.deleteEducation(education);
	}

	public ObservableList<Education> getAllEducationsAsObservableList() {
		ObservableList<Education> observableListOfEducations = FXCollections.observableArrayList();

		Set<Education> educations = schoolService.getAllEducations();
		for (Education education : educations) {
			observableListOfEducations.add(education);
		}
		return observableListOfEducations;
	}

	public ObservableList<Student> getAllStudentsConnectedToAEducationAsObservableList(int idOfEducation) {
		ObservableList<Student> observableListOfStudents = FXCollections.observableArrayList();

		Education education = schoolService.getEducationByIdWithStudents(idOfEducation);

		for (Student student : education.getStudents()) {
			observableListOfStudents.add(student);
		}

		return observableListOfStudents;

	}

	public ObservableList<Student> getAllStudentsWhichHasNoEducationToAddIntoComboBox() {
		ObservableList<Student> observableListOfStudents = FXCollections.observableArrayList();

		for (Student student : schoolService.getAllStudents()) {
			if (student.getEducation() == null) {
				observableListOfStudents.add(student);
			}
		}

		return observableListOfStudents;

	}

	public void addStudentToEducation(int idOfEducation, ObservableList<Student> checkComboBoxObservableList) {
		Education education = schoolService.getEducationByIdWithStudents(idOfEducation);

		for (Student student : checkComboBoxObservableList) {
			if (student != null) {
				education.addStudentToEducation(student);
			}
		}
		schoolService.updateEducation(education);
	}

	public void removeStudentFromEducation(int idOfEducation, int idOfStudent) {
		Education education = schoolService.getEducationByIdWithStudents(idOfEducation);
		Student student = schoolService.getStudentById(idOfStudent);
		education.removeStudentFromEducation(student);
		schoolService.updateStudent(student);
	}

	public ObservableList<Course> getAllCoursesConnectedToAEducation(int idOfEducation) {
		ObservableList<Course> observableListOfCourses = FXCollections.observableArrayList();

		Education education = schoolService.getEducationByIdWithCourses(idOfEducation);

		for (Course course : education.getCourses()) {
			observableListOfCourses.add(course);
		}

		return observableListOfCourses;

	}

	public ObservableList<Course> getAllCoursesWhichIsNotConnectedToAEducationToAddInComboBox(int idOfEducation) {
		ObservableList<Course> observableListOfCourses = FXCollections.observableArrayList();

		Education education = schoolService.getEducationByIdWithCourses(idOfEducation);

		Set<Course> allCourses = schoolService.getAllCoursesWithEducations();
		Set<Course> educationCourses = education.getCourses();

		for (Course course : allCourses) {
			if (course != null) {
				observableListOfCourses.add(course);
			}
		}
		for (Course course : educationCourses) {
			observableListOfCourses.remove(course);
		}

		return observableListOfCourses;

	}

	public void addCourseToEducation(int idOfEducation, ObservableList<Course> checkBoxObservableList) {
		Education education = schoolService.getEducationByIdWithCourses(idOfEducation);
		for (Course course : checkBoxObservableList) {
			if (course != null)
				education.addCourseToEducation(course);
		}

		schoolService.updateEducation(education);

	}

	public void removeCourseFromEducation(int idOfEducation, int idOfCourse) {

		Education education = schoolService.getEducationByIdWithCourses(idOfEducation);
		Course course = schoolService.getCourseByIdWithEducations(idOfCourse);

		education.getCourses().remove(course);
		schoolService.updateEducation(education);
	}

	/* StudentController */

	public ObservableList<Student> getAllStudentsAsObservableList() {

		ObservableList<Student> observableList = FXCollections.observableArrayList();

		Set<Student> students = schoolService.getAllStudents();

		for (Student student : students) {
			observableList.add(student);
		}

		return observableList;

	}

	public void deleteStudent(int idOfStudent) {
		Student student = schoolService.getStudentById(idOfStudent);
		student.setEducation(null);
		schoolService.updateStudent(student);
		schoolService.deleteStudent(student);
	}

	public void createStudent(String name, LocalDate birthDate) {
		schoolService.createStudent(name, birthDate);
	}

	/* TeacherController, TeacherCourseController */

	public ObservableList<Teacher> getAllTeachesAsObservableList() {
		ObservableList<Teacher> observableList = FXCollections.observableArrayList();

		Set<Teacher> teachers = schoolService.getAllTeachers();

		for (Teacher teacher : teachers) {
			observableList.add(teacher);
		}

		return observableList;
	}

	public void deleteTeacher(int idOfTeacher) {
		Teacher teacher = schoolService.getTeacherById(idOfTeacher);
		Set<Course> allCourse = schoolService.getAllCoursesWithTeachers();
		
		for (Course course : allCourse) {
			course.getTeachers().remove(teacher);
		}

		schoolService.updateTeacher(teacher);
		schoolService.deleteTeacher(teacher);
	}

	public void createTeacher(String name, LocalDate dateHired) {
		schoolService.createTeacher(name, dateHired);
	}

	public ObservableList<Course> getAllCoursesConnectedToASpecificTeacher(int idOfTeacher) {
		ObservableList<Course> observableList = FXCollections.observableArrayList();

		Teacher teacher = schoolService.getTeacherByIdWithCourses(idOfTeacher);

		for (Course course : teacher.getCourses()) {
			observableList.add(course);
		}

		return observableList;
	}

	public ObservableList<Course> getAllCoursesNotConnectedToSpecificTeacher(int idOfTeacher) {
		ObservableList<Course> observableList = FXCollections.observableArrayList();

		Teacher teacher = schoolService.getTeacherByIdWithCourses(idOfTeacher);
		Set<Course> allCourses = schoolService.getAllCoursesWithTeachers();

		for (Course course : allCourses) {
			observableList.add(course);
		}

		for (Course course : teacher.getCourses()) {
			observableList.remove(course);
		}

		return observableList;
	}

	public void addCourseToTeacherCourseGroup(int idOfTeacher, ObservableList<Course> checkedCourses) {
		Teacher teacher = schoolService.getTeacherByIdWithCourses(idOfTeacher);

		for (Course course : checkedCourses) {
			teacher.addToCourses(course);
		}
		schoolService.updateTeacher(teacher);
	}

	public void removeCourseFromTeacherCourseGroup(int idOfCourse, int idOfTeacher) {
		Course course = schoolService.getCourseById(idOfCourse);
		Teacher teacher = schoolService.getTeacherByIdWithCourses(idOfTeacher);

		teacher.getCourses().remove(course);
		schoolService.updateTeacher(teacher);
	}
}
