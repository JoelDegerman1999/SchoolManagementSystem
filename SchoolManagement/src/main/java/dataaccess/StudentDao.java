package dataaccess;

import domain.Student;

public interface StudentDao {
	
	public Student create(String name, String birthDate);

	public Student update(Student student);

	public Student delete(Student student);

	public Student getStudentById(int id);
}
