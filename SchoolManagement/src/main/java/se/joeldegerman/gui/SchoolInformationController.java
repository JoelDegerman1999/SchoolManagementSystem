package se.joeldegerman.gui;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import se.joeldegerman.service.SchoolService;

public class SchoolInformationController implements Initializable {

	@FXML
	private Text totalStudentText;
	@FXML
	private Text averageAgeOfStudentText;
	@FXML
	private Text totalTeacherText;
	@FXML
	private Text totalCoursesText;
	@FXML
	private Text totalEducationText;
	@FXML
	private Text total;

	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
	SchoolService sm = container.getBean(SchoolService.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}

	public void setup() {
		countStudents();
		countTeachers();
		countCourses();
		countEducations();
		averageAgeOfStudent();
	}

	private void countStudents() {
		totalStudentText.setText(String.valueOf(sm.getTotalAmmountOfStudents()));
	}

	private void countTeachers() {
		totalTeacherText.setText(String.valueOf(sm.getTotalAmmountOfTeachers()));
	}

	private void countEducations() {
		totalEducationText.setText(String.valueOf(sm.getTotalAmmountOfEducations()));
	}

	private void countCourses() {
		totalCoursesText.setText(String.valueOf(sm.getTotalAmmountOfCourses()));
	}

	private void averageAgeOfStudent() {
		DecimalFormat df = new DecimalFormat("#.##");
		averageAgeOfStudentText.setText(String.valueOf(df.format(sm.averageAgeOfAllStudents())));
	}

}
