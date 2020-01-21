/**
 * This file was generated by the Jeddict
 */
package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * @author charl
 */
@Entity
public class Education {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private LocalDate startDate;

	private LocalDate endDate;

	public Education() {
	}

	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "education")
	private List<Student> students;

	@ManyToMany
	private List<Course> courses;

	public Education(String name, LocalDate startDate, LocalDate endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		students = new ArrayList<Student>();
		courses = new ArrayList<Course>();
	}

	public void addStudentToEducation(Student student) {
		this.students.add(student);
		student.setEducation(this);
	}

	public void addCourseToEducation(Course course) {
		this.courses.add(course);
		course.getEducations().add(this);
	}
	
	public void removeStudentFromEducation(Student student) {
		this.students.remove(student);
		student.setEducation(null);
	}
	
	public void removeCourseFromEducation(Course course) {
		getCourses().remove(course);
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<Student> getStudents() {
		if (students == null) {
			students = new ArrayList<>();
		}
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Course> getCourses() {
		if (courses == null) {
			courses = new ArrayList<>();
		}
		return this.courses;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Education other = (Education) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "[" + getId() + "]" + " " + getName();
		
	}
	

}