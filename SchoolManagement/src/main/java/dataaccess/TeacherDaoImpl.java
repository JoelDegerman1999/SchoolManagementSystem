package dataaccess;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Teacher;

public class TeacherDaoImpl implements TeacherDao {

	EntityManagerFactory emf;

	public TeacherDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Teacher create(String name, LocalDate dateHired) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Teacher newTeacher = new Teacher(name, dateHired);
			em.persist(newTeacher);
			em.getTransaction().commit();
			em.close();
			return newTeacher;
		} catch (Exception e) {
			System.out.println("Error while creating the teacher");
			return null;
		}
	}

	@Override
	public Teacher update(Teacher teacher) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.merge(teacher);
			em.getTransaction().commit();
			em.close();
			return teacher;
		} catch (Exception e) {
			System.out.println("Error while updating the teacher");
			return null;
		}
	}

	@Override
	public Teacher delete(Teacher teacher) {
		try {
			EntityManager em = emf.createEntityManager();

			em.getTransaction().begin();
			teacher = em.merge(teacher);
			em.remove(teacher);
			em.getTransaction().commit();
			em.close();
			return teacher;
		} catch (Exception e) {
			System.out.println("Error while deleting the teacher");
			return null;
		}
	}

	@Override
	public Teacher getTeacherByIdWithCourses(int id) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Teacher teacher = em
					.createQuery("select t from Teacher as t left join fetch t.courses where t.id = :id", Teacher.class)
					.setParameter("id", id).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return teacher;
		} catch (Exception e) {
			System.out.println("Error while getting the teacher by Id with courses");
			return null;
		}
	}

	@Override
	public Teacher getTeacherById(int id) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Teacher teacher = em.find(Teacher.class, id);
			em.getTransaction().commit();
			em.close();
			return teacher;
		} catch (Exception e) {
			System.out.println("Error while getting the teacher by Id");
			return null;
		}
	}

	@Override
	public List<Teacher> getAllTeachersWithCourses() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Teacher> teachers = em
					.createQuery("select distinct t from Teacher as t left join fetch t.courses", Teacher.class)
					.getResultList();
			em.getTransaction().commit();
			em.close();
			return teachers;
		} catch (Exception e) {
			System.out.println("Error while getting all teachers with courses");
			return null;
		}
	}

	@Override
	public List<Teacher> getAllTeachers() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Teacher> teachers = em.createQuery("select distinct t from Teacher as t", Teacher.class)
					.getResultList();
			em.getTransaction().commit();
			em.close();
			return teachers;
		} catch (Exception e) {
			System.out.println("Error while getting all teachers");
			return null;
		}
	}

}
