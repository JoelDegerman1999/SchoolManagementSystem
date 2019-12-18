package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String birthdate;

	public Student() {
	}

	public void createCourse() {

	}

	@ManyToOne
	private Education education;

	public Student(String name, String birthdate) {
		this.name = name;
		this.birthdate = birthdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public Education getEducation() {
		return this.education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

}