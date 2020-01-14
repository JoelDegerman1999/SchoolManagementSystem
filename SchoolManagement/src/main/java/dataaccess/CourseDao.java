package dataaccess;

import java.util.List;

import domain.Course;

public interface CourseDao {
	public Course create(String subject);

	public Course update(Course course);

	public Course delete(Course course);

	public Course getCourseByIdWithEducations(int id);

	public Course getCourseBySubjectName(String subjectName);
	
	public List<Course> getAllCoursesWithEducations();

	public List<Course> getAllCoursesWithTeachers();

	Course getCourseById(int id);
}
