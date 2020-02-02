package se.joeldegerman.service;

import java.time.LocalDate;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.joeldegerman.dataaccess.CourseDao;
import se.joeldegerman.dataaccess.EducationDao;
import se.joeldegerman.dataaccess.StatisticsDao;
import se.joeldegerman.dataaccess.StudentDao;
import se.joeldegerman.dataaccess.TeacherDao;
import se.joeldegerman.domain.Course;
import se.joeldegerman.domain.Education;
import se.joeldegerman.domain.Student;
import se.joeldegerman.domain.Teacher;

@Service
@Transactional
public class SchoolService {

	@Autowired
	private CourseDao courseDao;
	@Autowired
	private EducationDao educationDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private StatisticsDao statisticsDao;

	@PostConstruct
	public void created() {
		System.out.println("Created");
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

	public Course getCourseByIdWithTeachersAndEducations(int idOfTeacher) {
		return courseDao.getCourseByIdWithTeachersAndEducations(idOfTeacher);
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

	public Set<Course> getAllCoursesWithEducations() {
		return courseDao.getAllCoursesWithEducations();
	}

	public Set<Course> getAllCoursesWithTeachers() {
		return courseDao.getAllCoursesWithTeachers();
	}

	public Set<Course> getAllCourses() {
		return courseDao.getAllCourses();
	}

	// Education
	public Education createEducation(String name, LocalDate startDate, LocalDate endDate) {
		return educationDao.create(name, startDate, endDate);
	}

	public Education getEducationByIdWithStudents(int id) {
		return educationDao.getEducationByIdWithStudents(id);
	}

	public Education getEducationByIdWithCourses(int id) {
		return educationDao.getEducationByIdWithCourses(id);
	}

	public Education getEducationByIdWithCoursesAndStudents(int id) {
		return educationDao.getEducationByIdWithCoursesAndStudents(id);
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

	public Set<Education> getAllEducations() {
		return educationDao.getAllEducations();
	}

	public Set<Education> getAllEducationsWithStudents() {
		return educationDao.getAllEducationsWithStudents();
	}

	public Set<Education> getAllEducationsWithCourses() {
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

	public Set<Student> getAllStudents() {
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

	public Set<Teacher> getAllTeachersWithCourses() {
		return teacherDao.getAllTeachersWithCourses();
	}

	public Set<Teacher> getAllTeachers() {
		return teacherDao.getAllTeachers();
	}

}
