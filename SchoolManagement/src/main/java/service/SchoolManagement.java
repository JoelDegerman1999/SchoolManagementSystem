package service;

import java.time.LocalDate;
import java.util.List;

import dataaccess.CourseDao;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dataaccess.CourseDaoImpl;
import dataaccess.EducationDao;
import dataaccess.EducationDaoImpl;
import dataaccess.StatisticsDao;
import dataaccess.StatisticsDaoImpl;
import dataaccess.StudentDao;
import dataaccess.StudentDaoImpl;
import dataaccess.TeacherDao;
import dataaccess.TeacherDaoImpl;
import domain.Course;
import domain.Education;
import domain.Student;
import domain.Teacher;

public class SchoolManagement {

	private CourseDao courseDao;
	private EducationDao educationDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;
	private StatisticsDao statisticsDao;

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

	public SchoolManagement() {
		courseDao = new CourseDaoImpl(emf);
		educationDao = new EducationDaoImpl(emf);
		studentDao = new StudentDaoImpl(emf);
		teacherDao = new TeacherDaoImpl(emf);
		statisticsDao = new StatisticsDaoImpl(emf);
	}

	// Statistcs

	public long getTotalAmmountOfStudents() {
		return statisticsDao.numberOfStudents();
	}

	public long getTotalAmmountOfTeachers() {
		return statisticsDao.numberOfTeachers();
	}

	public long getTotalAmmountOfEducations() {
		return statisticsDao.numberOfEducations();
	}

	public long getTotalAmmountOfCourses() {
		return statisticsDao.numberOfCourses();
	}

	public double averageAgeOfAllStudents() {
		return statisticsDao.averageAgeOfStudents();
	}

	// Courses
	public Course createCourse(String subject) {
		return courseDao.create(subject);
	}

	public Course getCourseByIdWithEducations(int id) {
		return courseDao.getCourseByIdWithEducations(id);
	}
	
	public Course getCourseByIdWithTeachers(int idOfTeacher) {
		return courseDao.getCourseByIdWithTeachers(idOfTeacher);
	}

	public Course getCourseById(int id) {
		return courseDao.getCourseById(id);
	}

	public Course updateCourse(Course course) {
		return courseDao.update(course);
	}

	public Course deleteCourse(Course course) {
		return courseDao.delete(course);
	}

	public List<Course> getAllCoursesWithEducations() {
		return courseDao.getAllCoursesWithEducations();
	}

	public List<Course> getAllCoursesWithTeachers() {
		return courseDao.getAllCoursesWithTeachers();
	}

	// Education
	public Education createEducation(String name, LocalDate startDate, LocalDate educationLength) {
		return educationDao.create(name, startDate, educationLength);
	}

	public Education getEducationByIdWithStudents(int id) {
		return educationDao.getEducationByIdWithStudents(id);
	}

	public Education getEducationByIdWithCourses(int id) {
		return educationDao.getEducationByIdWithCourses(id);
	}

	public Education getEducationById(int id) {
		return educationDao.getEducationById(id);
	}

	public Education updateEducation(Education education) {
		return educationDao.update(education);
	}

	public Education deleteEducation(Education education) {
		return educationDao.delete(education);
	}

	public List<Education> getAllEducations() {
		return educationDao.getAllEducations();
	}

	public List<Education> getAllEducationsWithStudents() {
		return educationDao.getAllEducationsWithStudents();
	}

	public List<Education> getAllEducationsWithCourses() {
		return educationDao.getAllEducationsWithCourses();
	}

	// Student
	public Student createStudent(String name, LocalDate birthDate) {
		return studentDao.create(name, birthDate);
	}

	public Student getStudentById(int id) {
		return studentDao.getStudentById(id);
	}

	public Student updateStudent(Student student) {
		return studentDao.update(student);
	}

	public Student deleteStudent(Student student) {
		return studentDao.delete(student);
	}

	public List<Student> getAllStudents() {
		return studentDao.getAllStudents();
	}

	// Teacher
	public Teacher createTeacher(String name, LocalDate dateHired) {
		return teacherDao.create(name, dateHired);
	}

	public Teacher getTeacherByIdWithCourses(int id) {
		return teacherDao.getTeacherByIdWithCourses(id);
	}

	public Teacher getTeacherById(int id) {
		return teacherDao.getTeacherById(id);
	}

	public Teacher updateTeacher(Teacher teacher) {
		return teacherDao.update(teacher);
	}

	public Teacher deleteTeacher(Teacher teacher) {
		return teacherDao.delete(teacher);
	}

	public List<Teacher> getAllTeachersWithCourses() {
		return teacherDao.getAllTeachersWithCourses();
	}

	public List<Teacher> getAllTeachers() {
		return teacherDao.getAllTeachers();
	}

	
}
