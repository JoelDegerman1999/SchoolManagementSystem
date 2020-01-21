package client;

import domain.Course;
import gui.FxPreloader;
import service.SchoolManagement;

public class MainTestClient extends FxPreloader {

	public static void main(String[] args) {
		SchoolManagement sm = new SchoolManagement();
		Course html = sm.createCourse("HTML");
		html.setSubject("JAVA");
		System.out.println(html); // [1] JAVA
		
		
		
		sm.updateCourse(html);
		
		Course htmlDB = sm.getCourseById(1);
		System.out.println(htmlDB); // [1] JAVA
		launch(args);
	}
}
