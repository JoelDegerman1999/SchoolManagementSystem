package client;

import domain.Course;
import domain.Teacher;
import gui.FxPreloader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.SchoolManagement;

/**
 * Hello world!
 *
 */
public class SimpleClientTest extends FxPreloader {

	

	public static void main(String[] args) {
		launch(args);
		
//		SchoolManagement sm = new SchoolManagement();
//
//		Teacher teacher = sm.createTeacher("Joel", "2017-1-1");
//		Course math = sm.createCourse("Math");
//		Course eng = sm.createCourse("English");
//		teacher.addToCourses(math);
//		teacher.addToCourses(eng);
//		System.out.println(teacher.getCourses());
//		sm.updateTeacher(teacher);

	}
}
