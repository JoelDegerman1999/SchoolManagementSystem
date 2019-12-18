package service;

import java.util.List;

import dataaccess.CourseDaoImpl;
import dataaccess.EducationDaoImpl;
import dataaccess.StudentDaoImpl;
import dataaccess.TeacherDaoImpl;
import domain.Course;
import domain.Education;
import domain.Student;
import domain.Teacher;

public class SchoolManagement {

	CourseDaoImpl courseDao;
	EducationDaoImpl educationDao;
	StudentDaoImpl studentDao;
	TeacherDaoImpl teacherDao;

	public SchoolManagement() {
		courseDao = new CourseDaoImpl();
		educationDao = new EducationDaoImpl();
		studentDao = new StudentDaoImpl();
		teacherDao = new TeacherDaoImpl();
	}

	// Courses
	public Course createCourse(String subject) {
		return courseDao.create(subject);
	}

	public Course getCourseById(int id) {
		return courseDao.getCourseById(id);
	}

	public Course getCourseBySubjectName(String subjectName) {
		return courseDao.getCourseBySubjectName(subjectName);
	}

	public Course updateCourse(Course course) {
		return courseDao.update(course);
	}

	public Course deleteCourse(Course course) {
		return courseDao.delete(course);
	}

	// Education
	public Education createEducation(String name, String startDate, String educationLength) {
		return educationDao.create(name, startDate, educationLength);
	}

	public Education getEducationById(int id) {
		return educationDao.getEducationById(id);
	}

	public Education getEducationByName(String name) {
		return educationDao.getEducationByName(name);
	}

	public Education updateEducation(Education education) {
		return educationDao.update(education);
	}

	public Education deleteEducation(Education education) {
		return educationDao.delete(education);
	}

	// Student
	public Student createStudent(String name, String birthDate) {
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

	// Teacher
	public Teacher createTeacher(String name, String dateHired) {
		return teacherDao.create(name, dateHired);
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
	
	public List<Teacher> getAllTeachers(){
		return teacherDao.getAllTeachers();
	}
}
