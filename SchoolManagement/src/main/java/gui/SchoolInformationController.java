package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import service.SchoolManagement;

public class SchoolInformationController implements Initializable{

	private SchoolManagement sm;
	
	@FXML
	private Text totalStudentText;
	@FXML
	private Text averageAgeOfStudentText;
	@FXML
	private Text totalTeacherText;
	@FXML
	private Text totalEducationText;
	@FXML
	private Text total;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sm = new SchoolManagement();	
		countStudents();
		countTeachers();
		averageAgeOfStudent();
	}
	
	private void countStudents() {
		totalStudentText.setText(String.valueOf(sm.getTotalAmmountOfStudents()));
	}
	private void countTeachers() {
		totalTeacherText.setText(String.valueOf(sm.getTotalAmmountOfTeachers()));
	}
	private void averageAgeOfStudent() {
		averageAgeOfStudentText.setText(String.valueOf(sm.averageAgeOfAllStudents()));
	}
	
}
