package dataaccess;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StatisticsDaoImpl implements StatisticsDao {

	EntityManagerFactory emf;

	public StatisticsDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public double averageAgeOfStudents() {
		double totalAge = 0l;
		double instancesToDivideBy = 0l;
		double averageAge = 0;

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<LocalDate> allAges = em.createQuery("select s.birthdate from Student as s", LocalDate.class)
				.getResultList();
		em.getTransaction().commit();
		em.close();

		for (LocalDate thisAge : allAges) {
			totalAge += ChronoUnit.YEARS.between(thisAge, LocalDate.now());
			instancesToDivideBy++;
		}
		averageAge = totalAge / instancesToDivideBy;

		return averageAge;
	}

	@Override
	public int numberOfStudents() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		long numberOfStudents = em.createQuery("select Count(s) from Student as s", Long.class).getSingleResult();
		em.getTransaction().commit();
		em.close();
		return (int) numberOfStudents;
	}

	@Override
	public int numberOfTeachers() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		long numberOfTeachers = em.createQuery("select Count(t) from Teacher as t", Long.class).getSingleResult();
		em.getTransaction().commit();
		em.close();
		return (int) numberOfTeachers;
	}

	@Override
	public int numberOfEducations() {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		long numberOfEducations = em.createQuery("select Count(e) from Education as e", Long.class).getSingleResult();
		em.getTransaction().commit();
		em.close();
		return (int) numberOfEducations;
	}

	@Override
	public int numberOfCourses() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		long numberOfCourses = em.createQuery("select Count(c) from Course as c", Long.class).getSingleResult();
		em.getTransaction().commit();
		em.close();
		return (int) numberOfCourses;
	}

}
