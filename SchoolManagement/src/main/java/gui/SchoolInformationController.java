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
	private Text totalTeacherText;
	@FXML
	private Text totalEducationText;
	@FXML
	private Text total;
	
	private EntityManagerFactory emf;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sm = new SchoolManagement();	
		emf = Persistence.createEntityManagerFactory("PU");
		countStudents();
	}
	
	private void countStudents() {
		EntityManager em = emf.createEntityManager();
		Long amount = em.createQuery("select COUNT(s) from Student as s left join fetch s.educations", Long.class).getSingleResult();
		totalStudentText.setText(String.valueOf(amount));
	}
	
}
