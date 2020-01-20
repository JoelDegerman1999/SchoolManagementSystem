package dataaccess;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StatisticsDaoImpl implements StatisticsDao {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

	@Override
	public double averageAgeOfStudents() {
		try {
			double totalAge = 0l;
			double instancesToDivideBy = 0l;
			double averageAge = 0;

			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<LocalDate> allAges = em.createQuery("select s.birthdate from Student as s", LocalDate.class)
					.getResultList();
			System.out.println(allAges);
			for (LocalDate thisAge : allAges) {
				if (thisAge != null) {
					totalAge += ChronoUnit.YEARS.between(thisAge, LocalDate.now());
					instancesToDivideBy++;
				}
			}
			averageAge = totalAge / instancesToDivideBy;

			em.getTransaction().commit();
			em.close();
			return averageAge;

		} catch (Exception e) {
			System.out.println("Error while getting average age of students");
		}
		return 0;
	}

	@Override
	public int numberOfStudents() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			long numberOfStudents = em.createQuery("select Count(s) from Student as s", Long.class).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return (int) numberOfStudents;
		} catch (Exception e) {
			System.out.println("Error while getting number of students");
		}
		return 0;
	}

	@Override
	public int numberOfTeachers() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			long numberOfTeachers = em.createQuery("select Count(t) from Teacher as t", Long.class).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return (int) numberOfTeachers;
		} catch (Exception e) {
			System.out.println("Error while getting number of teachers");
		}
		return 0;
	}

	public StatisticsDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public int numberOfEducations() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			long numberOfEducations = em.createQuery("select Count(e) from Education as e", Long.class)
					.getSingleResult();
			em.getTransaction().commit();
			em.close();
			return (int) numberOfEducations;
		} catch (Exception e) {
			System.out.println("Error while getting number of educations");
		}
		return 0;
	}

	@Override
	public int numberOfCourses() {
		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			long numberOfCourses = em.createQuery("select Count(c) from Course as c", Long.class).getSingleResult();
			em.getTransaction().commit();
			em.close();
			return (int) numberOfCourses;
		} catch (Exception e) {
			System.out.println("Error while getting number of courses");
		}
		return 0;
	}

}
