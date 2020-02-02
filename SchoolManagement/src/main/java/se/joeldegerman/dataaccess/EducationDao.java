package se.joeldegerman.dataaccess;

import java.time.LocalDate;
import java.util.Set;

import se.joeldegerman.domain.Education;

public interface EducationDao {
	public Education create(String name, LocalDate startDate, LocalDate educationLength);

	public Education update(Education education);

	public Education delete(Education education);

	public Set<Education> getAllEducations();

	public Set<Education> getAllEducationsWithStudents();

	public Set<Education> getAllEducationsWithCourses();

	public Education getEducationByIdWithStudents(int id);

	public Education getEducationById(int id);

	public Education getEducationByIdWithCourses(int id);

	Education getEducationByIdWithCoursesAndStudents(int id);

}
