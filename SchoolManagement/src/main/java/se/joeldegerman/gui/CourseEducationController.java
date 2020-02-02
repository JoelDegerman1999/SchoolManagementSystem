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
import se.joeldegerman.domain.Education;
import se.joeldegerman.service.FrontEndAPI;

public class CourseEducationController implements Initializable {

	private int idToUse;

	@FXML
	private TableView<Education> table;

	@FXML
	private TableColumn<Education, Integer> id;
	@FXML
	private TableColumn<Education, String> name;
	@FXML
	private TableColumn<Education, LocalDate> startDate;
	@FXML
	private TableColumn<Education, LocalDate> endDate;
	
	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
	FrontEndAPI api = container.getBean(FrontEndAPI.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}

	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
	}

	public void updateTableView() {
		table.setItems(api.getAllEducationsFromACourseAsObservableList(getIdOfCourse()));
	}

	public void setIdOfCourse(int idToUse) {
		this.idToUse = idToUse;
	}

	public int getIdOfCourse() {
		return idToUse;
	}

}
