package se.joeldegerman.gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import se.joeldegerman.domain.Teacher;
import se.joeldegerman.service.FrontEndAPI;

public class CourseTeacherController implements Initializable {
	
	private int idToUse;

	@FXML
	private TableView<Teacher> table;

	@FXML
	private TableColumn<Teacher, Integer> id;
	@FXML
	private TableColumn<Teacher, String> name;
	@FXML
	private TableColumn<Teacher, LocalDate> dateHired;

	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
	FrontEndAPI api = container.getBean(FrontEndAPI.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}
	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		dateHired.setCellValueFactory(new PropertyValueFactory<>("dateHired"));
	}
	
	public void updateTableView(){
		table.setItems(api.getAllTeachersFromAListCourseAsObservableList(getIdOfCourse()));
	}
	
	public void setIdOfCourse(int idToUse) {
		this.idToUse = idToUse;
	}

	public int getIdOfCourse() {
		return idToUse;
	}

}
