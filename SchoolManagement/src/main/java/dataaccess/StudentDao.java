package dataaccess;

import java.time.LocalDate;
import java.util.List;

import domain.Student;

public interface StudentDao {
	
	public Student create(String name, LocalDate birthDate);

	public Student update(Student student);

	public Student delete(Student student);

	public Student getStudentById(int id);
	
	public List<Student> getAllStudents();
	
}
