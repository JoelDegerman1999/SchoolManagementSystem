package se.joeldegerman.dataaccess;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import se.joeldegerman.domain.Education;

@Repository
public class EducationDaoImpl implements EducationDao {

	@PersistenceContext
	public EntityManager em;

	@Override
	public Education create(String name, LocalDate startDate, LocalDate educationLength) {
		Education education = new Education(name, startDate, educationLength);
		em.persist(education);
		return education;
	}

	@Override
	public Education update(Education education) {
		education = em.merge(education);
		return education;
	}

	@Override
	public Education delete(Education education) {
		education = em.merge(education);
		em.remove(education);
		return education;
	}

	@Override
	public Education getEducationById(int id) {
		Education education = em.find(Education.class, id);
		return education;
	}

	@Override
	public Education getEducationByIdWithStudents(int id) {
		Education education = em
				.createQuery("select edu from Education as edu left join fetch edu.enrolledStudents where edu.id = :id",
						Education.class)
				.setParameter("id", id).getSingleResult();
		return education;
	}

	@Override
	public Education getEducationByIdWithCourses(int id) {
		Education education = em
				.createQuery("select edu from Education as edu left join fetch edu.courses where edu.id = :id",
						Education.class)
				.setParameter("id", id).getSingleResult();
		return education;
	}
	
	@Override
	public Education getEducationByIdWithCoursesAndStudents(int id) {
		Education education = em
				.createQuery("select edu from Education as edu left join fetch edu.courses c left join fetch edu.enrolledStudents es where edu.id = :id",
						Education.class)
				.setParameter("id", id).getSingleResult();
		return education;
	}

	@Override
	public Set<Education> getAllEducations() {
		List<Education> educations = em.createQuery("select e from Education as e", Education.class).getResultList();
		return new HashSet<Education>(educations);
	}

	@Override
	public Set<Education> getAllEducationsWithStudents() {
		List<Education> educations = em
				.createQuery("select e from Education as e left join fetch e.students", Education.class)
				.getResultList();
		return new HashSet<Education>(educations);
	}

	@Override
	public Set<Education> getAllEducationsWithCourses() {
		List<Education> educations = em
				.createQuery("select distinct e from Education as e left join fetch e.courses", Education.class)
				.getResultList();
		return new HashSet<Education>(educations);
	}

}
