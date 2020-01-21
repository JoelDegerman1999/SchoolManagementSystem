package dataaccess;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import domain.Course;

public class CourseDaoImpl implements CourseDao {

	EntityManagerFactory emf;

	public CourseDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Course create(String subject) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Course course = new Course(subject);
			em.persist(course);
			em.getTransaction().commit();
			em.close();
			return course;
		} catch (Exception e) {
			System.out.println("Error while creating the course");
			return null;
		}
	}

	@Override
	public Course update(Course course) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			course = em.merge(course);
			em.getTransaction().commit();
			em.close();
			return course;
		} catch (Exception e) {
			System.out.println("Error while updating the course");
			return null;
		}
	}

	@Override
	public Course delete(Course course) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			course = em.merge(course);
			em.remove(course);
			em.getTransaction().commit();
			em.close();
			return course;
		} catch (Exception e) {
			System.out.println("Error while deleting the course");
			return null;
		}
	}

	@Override
	public Course getCourseById(int id) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Course course = em.createQuery("select c from Course as c  where c.id = :id", Course.class)
					.setParameter("id", id).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return course;
		} catch (Exception e) {
			System.out.println("Error while getting the course by Id");
			return null;
		}
	}

	@Override
	public Course getCourseByIdWithEducations(int id) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Course course = em.createQuery("select c from Course as c left join fetch c.educations where c.id = :id",
					Course.class).setParameter("id", id).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return course;
		} catch (Exception e) {
			System.out.println("Error while getting the course by Id with educations");
			return null;
		}
	}

	@Override
	public Course getCourseByIdWithTeachers(int idOfTeacher) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Course course = em
					.createQuery("select c from Course as c left join fetch c.teachers where c.id = :id", Course.class)
					.setParameter("id", idOfTeacher).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return course;
		} catch (Exception e) {
			System.out.println("Error while getting the course by Id with teachers");
			return null;
		}
	}

	@Override
	public List<Course> getAllCourses() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Course> courses = em
					.createQuery("select distinct c from Course as c", Course.class)
					.getResultList();
			em.getTransaction().commit();
			em.close();
			return courses;
		} catch (Exception e) {
			System.out.println("Error while getting all courses");
			return null;
		}
	}
	
	@Override
	public List<Course> getAllCoursesWithEducations() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Course> courses = em
					.createQuery("select distinct c from Course as c left join fetch c.educations", Course.class)
					.getResultList();
			em.getTransaction().commit();
			em.close();
			return courses;
		} catch (Exception e) {
			System.out.println("Error while getting all courses with educations");
			return null;
		}
	}

	@Override
	public List<Course> getAllCoursesWithTeachers() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Course> courses = em
					.createQuery("select distinct c from Course as c left join fetch c.teachers", Course.class)
					.getResultList();
			em.getTransaction().commit();
			em.close();
			return courses;
		} catch (Exception e) {
			System.out.println("Error while getting all courses with teachers");
			return null;
		}
	}

}
