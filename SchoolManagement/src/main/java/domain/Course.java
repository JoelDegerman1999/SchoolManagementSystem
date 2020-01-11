package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String subject;

	@ManyToMany(mappedBy = "courses")
	private List<Teacher> teachers;

	@ManyToMany(mappedBy = "courses")
	private List<Education> educations;

	public Course() {
	}

	public Course(String subject) {
		this.subject = subject;
		teachers = new ArrayList<Teacher>();
	}

	public List<Education> getEducations() {
		return educations;
	}
	
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Teacher> getTeachers() {
		return this.teachers;
	}
	
	public int getId() {
		return id;
	}

	public String test() {
		StringBuilder builder = new StringBuilder();
		for (Teacher teacher: teachers) {
			builder.append(teacher.getName()+ ", ");
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return "Course : " + getSubject();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		Course other = (Course) obj;
		if (id != other.id)
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	
	
	

}