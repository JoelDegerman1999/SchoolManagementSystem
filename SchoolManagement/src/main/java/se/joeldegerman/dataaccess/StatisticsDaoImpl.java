package se.joeldegerman.dataaccess;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class StatisticsDaoImpl implements StatisticsDao {

	@PersistenceContext
	public EntityManager em;

	@Override
	public double averageAgeOfStudents() {
		double totalAge = 0;
		double instancesToDivideBy = 0;
		double averageAge = 0;

		List<LocalDate> allAges = em.createQuery("select s.birthdate from Student as s", LocalDate.class)
				.getResultList();
		for (LocalDate thisAge : allAges) {
			if (thisAge != null) {
				totalAge += ChronoUnit.YEARS.between(thisAge, LocalDate.now());
				instancesToDivideBy++;
			}
		}
		averageAge = totalAge / instancesToDivideBy;

		return averageAge;
	}

	@Override
	public int numberOfStudents() {
		long numberOfStudents = em.createQuery("select Count(s) from Student as s", Long.class).getSingleResult();
		return (int) numberOfStudents;

	}

	@Override
	public int numberOfTeachers() {
		long numberOfTeachers = em.createQuery("select Count(t) from Teacher as t", Long.class).getSingleResult();
		return (int) numberOfTeachers;
	}

	@Override
	public int numberOfEducations() {
		long numberOfEducations = em.createQuery("select Count(e) from Education as e", Long.class).getSingleResult();
		return (int) numberOfEducations;
	}

	@Override
	public int numberOfCourses() {
		long numberOfCourses = em.createQuery("select Count(c) from Course as c", Long.class).getSingleResult();
		return (int) numberOfCourses;
	}

}