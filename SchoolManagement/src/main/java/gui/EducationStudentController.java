package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;
import org.hibernate.cache.spi.UpdateTimestampsCache;

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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.SchoolManagement;

public class EducationStudentController implements Initializable {

	private SchoolManagement sm;

	private int idToUse;

	@FXML
	private TableView<Student> table;
	@FXML
	private TableColumn<Student, Integer> idColumn;
	@FXML
	private TableColumn<Student, String> nameColumn;
	@FXML
	private TableColumn<Student, LocalDate> birthdateColumn;

	@FXML
	private TextField idTextField;

	@FXML
	private CheckComboBox<Student> checkComboBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sm = new SchoolManagement();
		setup();
	}

	private void setup() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		addItemsToComboBox();
		deleteRowWithContextMenuDropdown();
	}

	public void updateTableViewToShowStudents() {
		table.getItems().clear();
		Education education = sm.getEducationByIdWithStudents(getIdOfEducation());
		List<Student> students = education.getStudents();

		for (Student student : students) {
			table.getItems().add(student);
		}

	}

	private void addItemsToComboBox() {
		checkComboBox.getItems().clear();
		List<Student> students = sm.getAllStudents();
		for (Student student : students) {
			if (student.getEducation() == null) {
				checkComboBox.getItems().add(student);
			}
		}
	}

	public void addStudentToEducationStudentGroup() {
		Education education = sm.getEducationByIdWithStudents(getIdOfEducation());
		ObservableList<Student> students = checkComboBox.getCheckModel().getCheckedItems();

		for (Student student : students) {
			if (student != null)
				education.addStudentToEducation(student);
		}

		sm.updateEducation(education);
		updateTableViewToShowStudents();
		addItemsToComboBox();
	}

	private void deleteRowWithContextMenuDropdown() {

		table.setRowFactory(new Callback<TableView<Student>, TableRow<Student>>() {

			@Override
			public TableRow<Student> call(TableView<Student> tableView) {
				final TableRow<Student> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						table.getItems().remove(row.getItem());

						Education e = sm.getEducationByIdWithStudents(row.getItem().getEducation().getId());
						Student s = sm.getStudentById(row.getItem().getId());

						e.removeStudentFromEducation(s);
						sm.updateStudent(s);
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
