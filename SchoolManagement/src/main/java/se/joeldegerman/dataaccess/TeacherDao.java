package se.joeldegerman.dataaccess;

import java.time.LocalDate;
import java.util.Set;

import se.joeldegerman.domain.Teacher;

public interface TeacherDao {

	public Teacher create(String name, LocalDate dateHired);

	public Teacher update(Teacher teacher);

	public Teacher delete(Teacher teacher);

	public Teacher getTeacherById(int id);

	public Teacher getTeacherByIdWithCourses(int id);

	public Set<Teacher> getAllTeachers();

	public Set<Teacher> getAllTeachersWithCourses();


}
