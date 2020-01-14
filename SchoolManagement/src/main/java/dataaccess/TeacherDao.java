package dataaccess;

import java.time.LocalDate;
import java.util.List;

import domain.Teacher;

public interface TeacherDao {

	public Teacher create(String name, LocalDate dateHired);

	public Teacher update(Teacher teacher);

	public Teacher delete(Teacher teacher);

	public Teacher getTeacherByIdWithCourses(int id);

	public List<Teacher> getAllTeachersWithCourses();

	public Teacher getTeacherById(int id);

	public List<Teacher> getAllTeachers();

}
