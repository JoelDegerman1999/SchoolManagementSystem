package se.joeldegerman.dataaccess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import se.joeldegerman.domain.Course;

@Repository
public class CourseDaoImpl implements CourseDao {

	@PersistenceContext
	public EntityManager em;

	@Override
	public Course create(String subject) {
		Course course = new Course(subject);
		em.persist(course);
		return course;
	}

	@Override
	public Course update(Course course) {
		course = em.merge(course);
		return course;
	}

	@Override
	public Course delete(Course course) {
		course = em.merge(course);
		em.remove(course);
		return course;
	}

	@Override
	public Course getCourseById(int id) {
		Course course = em.createQuery("select c from Course as c  where c.id = :id", Course.class)
				.setParameter("id", id).getSingleResult();
		return course;
	}

	@Override
	public Course getCourseByIdWithEducations(int id) {
		Course course = em
				.createQuery("select c from Course as c left join fetch c.educations where c.id = :id", Course.class)
				.setParameter("id", id).getSingleResult();
		return course;

	}

	@Override
	public Course getCourseByIdWithTeachers(int idOfTeacher) {
		Course course = em
				.createQuery("select c from Course as c left join fetch c.teachers where c.id = :id", Course.class)
				.setParameter("id", idOfTeacher).getSingleResult();
		return course;
	}

	@Override
	public Course getCourseByIdWithTeachersAndEducations(int idOfTeacher) {
		Course course = em.createQuery(
				"select c from Course as c left join fetch c.teachers t left join fetch c.educations e where c.id = :id",
				Course.class).setParameter("id", idOfTeacher).getSingleResult();
		return course;
	}

	@Override
	public Set<Course> getAllCourses() {
		List<Course> courses = em.createQuery("select c from Course as c", Course.class).getResultList();
		return new HashSet<Course>(courses);
	}

	@Override
	public Set<Course> getAllCoursesWithEducations() {
		List<Course> courses = em
				.createQuery("select c from Course as c left join fetch c.educations", Course.class)
				.getResultList();
		return new HashSet<Course>(courses);
	}

	@Override
	public Set<Course> getAllCoursesWithTeachers() {
		List<Course> courses = em
				.createQuery("select c from Course as c left join fetch c.teachers", Course.class)
				.getResultList();

		return new HashSet<Course>(courses);
	}

	@Override
	public Set<Course> getAllCoursesWithTeachersAndEducations() {
		List<Course> courses = em.createQuery(
				"select c from Course as c left join fetch c.teachers left join fetch c.educations",
				Course.class).getResultList();
		return new HashSet<Course>(courses);
	}

}
