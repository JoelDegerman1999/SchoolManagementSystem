package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import domain.Course;
import domain.Teacher;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.SchoolManagement;

public class TeacherCourseController implements Initializable {

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

		deleteRowWithContextMenuDropdown();
	}

	public void updateTableViewToShowCourses() {
		Teacher teacher = sm.getTeacherByIdWithCourses(getIdOfTeacher());
		table.getItems().clear();
		List<Course> courses = teacher.getCourses();

		for (Course course : courses) {
			table.getItems().add(course);
		}

	}

	private void updateAndAddItemsToComboBox() {
		checkComboBox.getItems().clear();
		List<Course> courses = sm.getAllCoursesWithTeachers();
		Teacher teacher = sm.getTeacherById(getIdOfTeacher());
		List<Course> teacherCourses = teacher.getCourses();
		for (Course course : courses) {
			if (course != null) {
				checkComboBox.getItems().add(course);
			}
		}
		
		for (Course course : teacherCourses) {
			checkComboBox.getItems().remove(course);
		}
	}

	public void addCourseToTeacherCourseGroup() {
		Teacher teacher = sm.getTeacherByIdWithCourses(getIdOfTeacher());
		List<Course> coursesList = sm.getAllCoursesWithTeachers();
		ObservableList<Course> courses = checkComboBox.getCheckModel().getCheckedItems();

		for (Course allCourses : courses) {
			for (Course teacherCourses : coursesList) {
				if (allCourses.getId() == teacherCourses.getId()) {
					teacher.addToCourses(allCourses);
				}

			}
		}

		sm.updateTeacher(teacher);

		updateTableViewToShowCourses();

		updateAndAddItemsToComboBox();
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

						Course course = sm.getCourseByIdWithEducations(row.getItem().getId());
						Teacher teacher = sm.getTeacherByIdWithCourses(getIdOfTeacher());
						teacher.getCourses().remove(course);
						sm.updateTeacher(teacher);
						table.getItems().remove(row.getItem());
						updateAndAddItemsToComboBox();
					}
				});
				contextMenu.getItems().add(removeMenuItem);

				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
	}

	public void setIdOfTeacher(int idToUse) {
		this.idToUse = idToUse;
	}

	public int getIdOfTeacher() {
		return idToUse;
	}

}
