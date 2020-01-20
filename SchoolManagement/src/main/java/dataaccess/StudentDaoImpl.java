package dataaccess;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Student;

public class StudentDaoImpl implements StudentDao {

	EntityManagerFactory emf;

	public StudentDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Student create(String name, LocalDate birthDate) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Student newStudent = new Student(name, birthDate);
			em.persist(newStudent);
			em.getTransaction().commit();
			em.close();
			return newStudent;

		} catch (Exception e) {
			System.out.println("Error while creating the student");
			return null;
		}
	}

	@Override
	public Student update(Student student) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			student = em.merge(student);
			em.getTransaction().commit();
			em.close();
			return student;
		} catch (Exception e) {
			System.out.println("Error while updating the student");
			return null;
		}
	}

	@Override
	public Student delete(Student student) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			student = em.merge(student);
			em.remove(student);
			em.getTransaction().commit();
			em.close();
			return student;
		} catch (Exception e) {
			System.out.println("Error while deleting the student");
			return null;
		}
	}

	@Override
	public Student getStudentById(int id) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Student foundStudent = em.find(Student.class, id);
			em.getTransaction().commit();
			em.close();
			return foundStudent;
		} catch (Exception e) {
			System.out.println("Error while getting the student by Id");
			return null;
		}
	}

	@Override
	public List<Student> getAllStudents() {
		try {

			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Student> students = em.createQuery("select s from Student as s", Student.class).getResultList();
			em.getTransaction().commit();
			em.close();
			return students;
		} catch (Exception e) {
			System.out.println("Error while getting all students");
			return null;
		}
	}

}
