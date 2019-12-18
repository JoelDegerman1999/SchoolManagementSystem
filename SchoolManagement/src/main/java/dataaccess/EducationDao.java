package dataaccess;

import domain.Education;

public interface EducationDao {
	public Education create(String name, String startDate, String educationLength);

	public Education update(Education education);

	public Education delete(Education education);

	public Education getEducationById(int id);

	public Education getEducationByName(String name);
}
