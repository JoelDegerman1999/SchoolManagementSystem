package dataaccess;

import java.time.LocalDate;
import java.util.List;

import domain.Education;

public interface EducationDao {
	public Education create(String name, LocalDate startDate, LocalDate educationLength);

	public Education update(Education education);

	public Education delete(Education education);

	public Education getEducationByIdWithStudents(int id);

	public Education getEducationByName(String name);

	public List<Education> getAllEducations();

	public List<Education> getAllEducationsWithStudents();

	public List<Education> getAllEducationsWithCourses();

	public Education getEducationByIdWithCourses(int id);
}
