package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import domain.Course;
import domain.Education;
import domain.Student;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.SchoolManagement;

public class EducationCourseController implements Initializable {

	SchoolManagement sm;

	private int idToUse = 1;

	@FXML
	private TableView<Course> table;
	@FXML
	private TableColumn<Course, Integer> id;
	@FXML
	private TableColumn<Course, String> subjectName;
	@FXML
	private TextField idTextField;

	@FXML
	private CheckComboBox<Course> checkComboBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sm = new SchoolManagement();
		setup();

	}

	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		subjectName.setCellValueFactory(new PropertyValueFactory<>("subject"));
		
		addItemsToComboBox();
		deleteRowWithContextMenuDropdown();
	}

	public void updateTableViewToShowCourses() {
		Education education = sm.getEducationByIdWithCourses(getIdOfEducation());
		table.getItems().clear();
		List<Course> courses = education.getCourses();

		for (Course course : courses) {
			table.getItems().add(course);
		}

	}

	private void addItemsToComboBox() {
		checkComboBox.getItems().clear();
		List<Course> courses = sm.getAllCourses();
		for (Course course : courses) {
			if (course != null && course.getEducations().size() <= 0) {
				checkComboBox.getItems().add(course);
			}
		}
	}


	public void addCourseToEducationCourseGroup() {
		Education education = sm.getEducationByIdWithCourses(getIdOfEducation());
		List<Course> coursesList = sm.getAllCourses();
		ObservableList<Course> courses = checkComboBox.getCheckModel().getCheckedItems();
		System.out.println(courses);

		System.out.println(coursesList);

		for (Course course : courses) {
			System.out.println(course.getId());
			for (Course course2 : coursesList) {
				if (course.getId() == course2.getId()) {
					education.addCourseToEducation(course);
				}

			}
		}

		sm.updateEducation(education);

		updateTableViewToShowCourses();

		addItemsToComboBox();
	}

	private void deleteRowWithContextMenuDropdown() {

		table.setRowFactory(new Callback<TableView<Course>, TableRow<Course>>() {

			@Override
			public TableRow<Course> call(TableView<Course> tableView) {
				final TableRow<Course> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						Course course = sm.getCourseById(row.getItem().getId());
						System.out.println(course);
						System.out.println(getIdOfEducation());
						Education education = sm.getEducationByIdWithCourses(getIdOfEducation());
						education.getCourses().remove(course);
						sm.updateEducation(education);
						table.getItems().remove(row.getItem());
						addItemsToComboBox();
					}
				});
				contextMenu.getItems().add(removeMenuItem);

				// Set context menu on row, but use a binding to make it only show for non-empty
				// rows:
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
	}

	public void setIdOfEducation(int idToUse) {
		this.idToUse = idToUse;
	}

	public int getIdOfEducation() {
		return idToUse;
	}

}
