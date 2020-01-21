package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import domain.Course;
import domain.Education;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.SchoolManagement;

public class CourseEducationController implements Initializable {

	private SchoolManagement sm;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}

	private void setup() {
		sm = new SchoolManagement();
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
	}

	public void updateTableView() {
		table.getItems().clear();
		List<Education> educations = sm.getAllEducationsWithCourses();

		Course course = sm.getCourseByIdWithEducations(getIdOfCourse());
		
		for (Education education : educations) {
			List<Course> courses = education.getCourses();
			for (Course c : courses) {
				if (course.equals(c)) {
					table.getItems().add(education);
				}
			}
		}

	}

	public void setIdOfCourse(int idToUse) {
		this.idToUse = idToUse;
	}

	public int getIdOfCourse() {
		return idToUse;
	}

}
