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

	@ManyToMany
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

}