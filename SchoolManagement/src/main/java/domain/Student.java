package domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 * @author Joel
 *
 */
@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private LocalDate birthdate;

	public Student() {
	}


	@ManyToOne
	private Education education;

	public Student(String name, LocalDate birthdate) {
		this.name = name;
		this.birthdate = birthdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public Education getEducation() {
		return this.education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
		public String toString() {
			return "[" + getId() + "]" + " " + getName();
		}


}