package dataaccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Education;

public class EducationDaoImpl implements EducationDao {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

	@Override
	public Education create(String name, String startDate, String educationLength) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Education education = new Education(name, startDate, educationLength);
		em.persist(education);
		em.getTransaction().commit();
		em.close();
		return education;
	}

	@Override
	public Education update(Education education) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		education = em.merge(education);
		em.getTransaction().commit();
		em.close();
		return education;
	}

	@Override
	public Education delete(Education education) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		education = em.merge(education);
		em.remove(education);
		em.getTransaction().commit();
		em.close();
		return education;
	}

	@Override
	public Education getEducationById(int id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Education education = em.find(Education.class, id);
		em.getTransaction().commit();
		em.close();
		return education;
	}

	@Override
	public Education getEducationByName(String name) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Education edcuation = em.createQuery("select edu from Education as edu where edu.name=:name", Education.class).setParameter("name", name).getSingleResult();
		em.getTransaction().commit();
		em.close();
		return edcuation;
	}

}
