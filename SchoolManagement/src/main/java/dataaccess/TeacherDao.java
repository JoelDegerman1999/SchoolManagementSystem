package dataaccess;

import java.util.List;

import domain.Teacher;

public interface TeacherDao {

	public Teacher create(String name, String dateHired);

	public Teacher update(Teacher teacher);

	public Teacher delete(Teacher teacher);

	public Teacher getTeacherById(int id);
	
	public List<Teacher> getAllTeachers();

}
