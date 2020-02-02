package se.joeldegerman.dataaccess;

import java.util.Set;

import se.joeldegerman.domain.Course;

public interface CourseDao {

	public Course create(String subject);

	public Course update(Course course);

	public Course delete(Course course);

	public Course getCourseById(int id);

	public Course getCourseByIdWithTeachers(int idOfTeacher);

	public Course getCourseByIdWithEducations(int id);

	public Course getCourseByIdWithTeachersAndEducations(int id);

	public Set<Course> getAllCourses();

	public Set<Course> getAllCoursesWithEducations();

	public Set<Course> getAllCoursesWithTeachers();

	public Set<Course> getAllCoursesWithTeachersAndEducations();

}
