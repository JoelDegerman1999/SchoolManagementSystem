package dataaccess;

import java.util.List;

import domain.Course;

public interface CourseDao {
	
	public Course create(String subject);

	public Course update(Course course);

	public Course delete(Course course);

	public Course getCourseById(int id);

	public Course getCourseByIdWithTeachers(int idOfTeacher);

	public Course getCourseByIdWithEducations(int id);
	
	public List<Course> getAllCourses();

	public List<Course> getAllCoursesWithEducations();

	public List<Course> getAllCoursesWithTeachers();

}
