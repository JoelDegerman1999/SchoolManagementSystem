package dataaccess;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Teacher;

public class TeacherDaoImpl implements TeacherDao {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
	
	@Override
	public Teacher create(String name, String dateHired) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Teacher newTeacher = new Teacher(name, dateHired);
		em.persist(newTeacher);
		em.getTransaction().commit();
		em.close();
		return newTeacher;
	}

	@Override
	public Teacher update(Teacher teacher) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(teacher);
		em.getTransaction().commit();
		em.close();
		return teacher;
	}

	@Override
	public Teacher delete(Teacher teacher) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		teacher = em.merge(teacher);
		em.remove(teacher);
		em.getTransaction().commit();
		em.close();
		return teacher;
	}

	@Override
	public Teacher getTeacherById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Teacher teacher = em.find(Teacher.class, id);
		em.getTransaction().commit();
		em.close();
		return teacher;
	}

	@Override
	public List<Teacher> getAllTeachers() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Teacher> teachers = em.createQuery("select distinct t from Teacher as t left join fetch t.courses", Teacher.class).getResultList();
		em.getTransaction().commit();
		em.close();
		return teachers;
	}
	

}
