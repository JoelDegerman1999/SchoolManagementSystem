package se.joeldegerman.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

	@ManyToMany( mappedBy = "courses")
	private Set<Teacher> teachers;

	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "courses")
	private Set<Education> educations;

	public Course() {
	}

	public Course(String subject) {
		this.subject = subject;
		teachers = new HashSet<Teacher>();
	}
	
	
	public Set<Education> getEducations() {
		return educations;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Set<Teacher> getTeachers() {
		return this.teachers;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "[" + getId() + "]" + " " + getSubject();
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
		Course other = (Course) obj;
		if (id != other.id)
			return false;
		return true;
	}

}