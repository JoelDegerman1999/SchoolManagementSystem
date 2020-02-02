package se.joeldegerman.dataaccess;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import se.joeldegerman.domain.Teacher;

@Repository
public class TeacherDaoImpl implements TeacherDao {

	@PersistenceContext
	public EntityManager em;

	@Override
	public Teacher create(String name, LocalDate dateHired) {
		Teacher newTeacher = new Teacher(name, dateHired);
		em.persist(newTeacher);
		return newTeacher;
	}

	@Override
	public Teacher update(Teacher teacher) {
		em.merge(teacher);
		return teacher;
	}

	@Override
	public Teacher delete(Teacher teacher) {
		teacher = em.merge(teacher);
		em.remove(teacher);
		return teacher;
	}

	@Override
	public Teacher getTeacherByIdWithCourses(int id) {
		Teacher teacher = em
				.createQuery("select t from Teacher as t left join fetch t.courses where t.id = :id", Teacher.class)
				.setParameter("id", id).getSingleResult();
		return teacher;
	}

	@Override
	public Teacher getTeacherById(int id) {
		Teacher teacher = em.find(Teacher.class, id);
		return teacher;
	}

	@Override
	public Set<Teacher> getAllTeachersWithCourses() {
		List<Teacher> teachers = em
				.createQuery("select distinct t from Teacher as t left join fetch t.courses", Teacher.class)
				.getResultList();
		return new HashSet<Teacher>(teachers);
	}

	@Override
	public Set<Teacher> getAllTeachers() {
		List<Teacher> teachers = em.createQuery("select distinct t from Teacher as t", Teacher.class).getResultList();
		return new HashSet<Teacher>(teachers);
	}

}
