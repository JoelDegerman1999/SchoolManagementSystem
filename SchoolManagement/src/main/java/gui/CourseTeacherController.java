package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import domain.Course;
import domain.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.SchoolManagement;

public class CourseTeacherController implements Initializable {
	
	private SchoolManagement sm;
	private int idToUse;

	@FXML
	private TableView<Teacher> table;

	@FXML
	private TableColumn<Teacher, Integer> id;
	@FXML
	private TableColumn<Teacher, String> name;
	@FXML
	private TableColumn<Teacher, LocalDate> dateHired;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}

	private void setup() {
		sm = new SchoolManagement();
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		dateHired.setCellValueFactory(new PropertyValueFactory<>("dateHired"));
	}
	
	public void updateTableViewToShowTeachers() {
		table.getItems().clear();
		Course course = sm.getCourseByIdWithTeachers(getIdOfTeacher());
		List<Teacher> teachers = course.getTeachers();
		
		for (Teacher teacher : teachers) {
			table.getItems().add(teacher);
		}

	}
	
	public void setIdOfTeacher(int idToUse) {
		this.idToUse = idToUse;
	}

	public int getIdOfTeacher() {
		return idToUse;
	}

}
