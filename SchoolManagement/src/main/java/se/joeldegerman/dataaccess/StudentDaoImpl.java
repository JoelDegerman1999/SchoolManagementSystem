package se.joeldegerman.dataaccess;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import se.joeldegerman.domain.Student;

@Repository
public class StudentDaoImpl implements StudentDao {

	@PersistenceContext
	public EntityManager em;

	@Override
	public Student create(String name, LocalDate birthDate) {
		Student newStudent = new Student(name, birthDate);
		em.persist(newStudent);
		return newStudent;

	}

	@Override
	public Student update(Student student) {
		student = em.merge(student);
		return student;
	}

	@Override
	public Student delete(Student student) {
		student = em.merge(student);
		em.remove(student);
		return student;
	}

	@Override
	public Student getStudentById(int id) {
		Student foundStudent = em.find(Student.class, id);
		return foundStudent;
	}

	@Override
	public Set<Student> getAllStudents() {
		List<Student> students = em.createQuery("select s from Student as s", Student.class).getResultList();
		
		return new HashSet<Student>(students);
	}

}
