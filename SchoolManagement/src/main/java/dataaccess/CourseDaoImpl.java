package dataaccess;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Course;

public class CourseDaoImpl implements CourseDao {

	EntityManagerFactory emf;

	public CourseDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;	
		}

	@Override
	public Course create(String subject) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Course course = new Course(subject);
		em.persist(course);
		em.getTransaction().commit();
		em.close();
		return course;
	}

	@Override
	public Course update(Course course) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		course = em.merge(course);
		em.getTransaction().commit();
		em.close();
		return course;
	}

	@Override
	public Course delete(Course course) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		course = em.merge(course);
		em.remove(course);
		em.getTransaction().commit();
		em.close();
		return course;
	}

	@Override
	public Course getCourseByIdWithEducations(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Course course = em.createQuery("select c from Course as c left join fetch c.educations where c.id = :id", Course.class).setParameter("id", id).getSingleResult();
		em.getTransaction().commit();
		em.close();
		return course;
	}
	@Override
	public Course getCourseById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Course course = em.createQuery("select c from Course as c  where c.id = :id", Course.class).setParameter("id", id).getSingleResult();
		em.getTransaction().commit();
		em.close();
		return course;
	}

	@Override
	public Course getCourseBySubjectName(String subjectName) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Course course = em.createQuery("select c from Course as c where c.name=:name", Course.class)
				.setParameter("name", subjectName).getSingleResult();
		em.getTransaction().commit();
		em.close();
		return course;
	}

	@Override
	public List<Course> getAllCoursesWithEducations() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Course> courses = em.createQuery("select distinct c from Course as c left join fetch c.educations", Course.class).getResultList();
		em.getTransaction().commit();
		em.close();
		return courses;
	}
	@Override
	public List<Course> getAllCoursesWithTeachers() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Course> courses = em.createQuery("select distinct c from Course as c left join fetch c.teachers", Course.class).getResultList();
		em.getTransaction().commit();
		em.close();
		return courses;
	}
	
	

}
