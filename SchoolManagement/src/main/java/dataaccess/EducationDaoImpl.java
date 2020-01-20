package dataaccess;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import domain.Education;

public class EducationDaoImpl implements EducationDao {

	EntityManagerFactory emf;

	public EducationDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Education create(String name, LocalDate startDate, LocalDate educationLength) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Education education = new Education(name, startDate, educationLength);
			em.persist(education);
			em.getTransaction().commit();
			em.close();
			return education;
		} catch (Exception e) {
			System.out.println("Error while creating the education");
			return null;
		}
	}

	@Override
	public Education update(Education education) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			education = em.merge(education);
			em.getTransaction().commit();
			em.close();
			return education;
		} catch (Exception e) {
			System.out.println("Error while updating the education");
			return null;
		}
	}

	@Override
	public Education delete(Education education) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			education = em.merge(education);
			em.remove(education);
			em.getTransaction().commit();
			em.close();
			return education;
		} catch (Exception e) {
			System.out.println("Error while deleting the education");
			return null;
		}
	}

	@Override
	public Education getEducationById(int id) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Education education = em.find(Education.class, id);
			em.getTransaction().commit();
			em.close();
			return education;
		} catch (Exception e) {
			System.out.println("Error while getting the education by Id");
			return null;
		}
	}

	@Override
	public Education getEducationByIdWithStudents(int id) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Education education = em
					.createQuery("select edu from Education as edu left join fetch edu.students where edu.id = :id",
							Education.class)
					.setParameter("id", id).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return education;
		} catch (Exception e) {
			System.out.println("Error while getting the education by Id with students");
			return null;
		}
	}

	@Override
	public Education getEducationByIdWithCourses(int id) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Education education = em
					.createQuery("select edu from Education as edu left join fetch edu.courses where edu.id = :id",
							Education.class)
					.setParameter("id", id).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return education;
		} catch (Exception e) {
			System.out.println("Error while getting the education by Id with courses");
			return null;
		}
	}

	@Override
	public Education getEducationByName(String name) {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Education education = em
					.createQuery("select edu from Education as edu where edu.name=:name", Education.class)
					.setParameter("name", name).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return education;
		} catch (Exception e) {
			System.out.println("Error while getting the education by name");
			return null;
		}
	}

	@Override
	public List<Education> getAllEducations() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Education> educations = em.createQuery("select e from Education as e", Education.class)
					.getResultList();
			em.getTransaction().commit();
			em.close();
			return educations;
		} catch (Exception e) {
			System.out.println("Error while getting all educations");
			return null;
		}
	}

	@Override
	public List<Education> getAllEducationsWithStudents() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Education> educations = em
					.createQuery("select e from Education as e left join fetch e.students", Education.class)
					.getResultList();
			em.getTransaction().commit();
			em.close();
			return educations;
		} catch (Exception e) {
			System.out.println("Error while getting all educations with students");
			return null;
		}
	}

	@Override
	public List<Education> getAllEducationsWithCourses() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<Education> educations = em
					.createQuery("select distinct e from Education as e left join fetch e.courses", Education.class)
					.getResultList();
			em.getTransaction().commit();
			em.close();
			return educations;
		} catch (Exception e) {
			System.out.println("Error while getting all educations with courses");
			return null;
		}
	}

}
