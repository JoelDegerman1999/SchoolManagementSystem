package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import domain.Student;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.SchoolManagement;

public class StudentController implements Initializable {

	@FXML
	private TableView<Student> table;

	@FXML
	private TableColumn<Integer, Student> id;
	@FXML
	private TableColumn<String, Student> name;
	@FXML
	private TableColumn<LocalDate, Student> birthDate;

	@FXML
	private DatePicker datePicker;
	@FXML
	private TextField textFieldName;

	private SchoolManagement sm;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sm = new SchoolManagement();
		setup();
	}

	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		birthDate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

		updateTable();
		deleteRowFromTable();
	}

	private void updateTable() {
		table.getItems().clear();
		ObservableList<Student> observableList = FXCollections.observableArrayList();

		List<Student> students = sm.getAllStudents();

		for (Student student : students) {
			observableList.add(student);
		}

		table.setItems(observableList);
	}

	private void deleteRowFromTable() {
		table.setRowFactory(new Callback<TableView<Student>, TableRow<Student>>() {
			@Override
			public TableRow<Student> call(TableView<Student> tableView) {
				final TableRow<Student> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						System.out.println(row.getItem());
						Student student = row.getItem();
						System.out.println(student.getEducation());
						if (student.getEducation() == null) {
							sm.deleteStudent(student);
							table.getItems().remove(row.getItem());
						}else {
							System.out.println("Kan inte ta bort student då den är kopplad till en utbidling");
						}

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

	public void createStudent() {
		LocalDate date = datePicker.getValue();
		sm.createStudent(textFieldName.getText(), date);
		textFieldName.clear();
		datePicker.getEditor().clear();
		datePicker.setValue(null);
		updateTable();
	}

}
