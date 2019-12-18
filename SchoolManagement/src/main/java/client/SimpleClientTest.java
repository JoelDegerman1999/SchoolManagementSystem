package client;

import domain.Course;
import domain.Teacher;
import service.SchoolManagement;

/**
 * Hello world!
 *
 */
public class SimpleClientTest {
	public static void main(String[] args) {
		SchoolManagement sm = new SchoolManagement();
		
		Teacher teacher = sm.createTeacher("Joel", "2017-1-1");
		Course math = sm.createCourse("Math");
		Course eng = sm.createCourse("English");
		teacher.addToCourses(math);
		teacher.addToCourses(eng);
		System.out.println(teacher.getCourses());
		sm.updateTeacher(teacher);
		


	}
}
