package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Student;

public class StudentDaoImpl implements StudentDao {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	
	@Override
	public Student create(String name, String birthDate) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Student newTeacher = new Student(name, birthDate);
		em.persist(newTeacher);
		em.getTransaction().commit();
		em.close();
		return newTeacher;
	}

	@Override
	public Student update(Student student) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		student = em.merge(student);
		em.getTransaction().commit();
		em.close();
		return student;
	}

	@Override
	public Student delete(Student student) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(student);
		em.getTransaction().commit();
		em.close();
		return student;
	}

	@Override
	public Student getStudentById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Student foundStudent = em.find(Student.class, id);
		em.getTransaction().commit();
		em.close();
		return foundStudent;
	}

}
