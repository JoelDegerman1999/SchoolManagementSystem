package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import domain.Education;
import domain.Student;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.SchoolManagement;

public class EducationStudentController implements Initializable {

	SchoolManagement sm;

	private int idToUse = 1;

	@FXML
	private TableView<Student> table;
	@FXML
	private TableColumn<Student, Integer> idColumn;
	@FXML
	private TableColumn<Student, String> nameColumn;
	@FXML
	private TableColumn<Student, LocalDate> birthdateColumn;

	@FXML
	private CheckComboBox<Student> checkComboBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sm = new SchoolManagement();
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		System.out.println(getIdToUse());
	}

	public void updateTable() {
		System.out.println(getIdToUse());
		table.getItems().clear();
		Education education = sm.getEducationById(getIdToUse());
		System.out.println(education);
		List<Student> students = education.getStudents();
		System.out.println(students);
		
		for (Student student : students) {
			System.out.println(student);
			table.getItems().add(student);
		}
		
	}

	public void setIdToUse(int idToUse) {
		this.idToUse = idToUse;
	}

	public int getIdToUse() {
		return idToUse;
	}

}
