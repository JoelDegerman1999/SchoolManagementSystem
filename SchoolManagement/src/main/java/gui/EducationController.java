package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import domain.Education;
import domain.Student;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.SchoolManagement;

public class EducationController implements Initializable {

	SchoolManagement sm;

	@FXML
	private CheckComboBox<Student> checkComboBox;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private TextField nameTextField;

	@FXML
	private TableView<Education> table;
	@FXML
	private TableColumn<Education, Void> studentColumn;
	@FXML
	private TableColumn<Education, Void> courseColumn;
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
		sm = new SchoolManagement();
		setup();

	}

	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		addItemsToComboBox();
		updateTableViewToShowEducations();
		addButtonToColumnStudents();
		addButtonToColumnCourse();
		deleteRowWithContextMenuDropdown();
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

	public void createEducation() {
		Education education = sm.createEducation(nameTextField.getText(), startDatePicker.getValue(),
				endDatePicker.getValue());
		ObservableList<Student> students = checkComboBox.getCheckModel().getCheckedItems();

		for (Student student : students) {
			if (student != null)
				education.addStudentToEducation(student);
		}
		sm.updateEducation(education);
		updateTableViewToShowEducations();
		addItemsToComboBox();
	}

	private void updateTableViewToShowEducations() {
		table.getItems().clear();
		ObservableList<Education> observableList = FXCollections.observableArrayList();

		List<Education> educations = sm.getAllEducations();

		for (Education education : educations) {
			if (education != null)
				observableList.add(education);
		}
		table.setItems(observableList);
	}

	private void addButtonToColumnStudents() {

		Callback<TableColumn<Education, Void>, TableCell<Education, Void>> cellFactory = new Callback<TableColumn<Education, Void>, TableCell<Education, Void>>() {
			@Override
			public TableCell<Education, Void> call(final TableColumn<Education, Void> param) {
				final TableCell<Education, Void> cell = new TableCell<Education, Void>() {

					private final Button btn = new Button("Students");
					{
						btn.setOnAction((ActionEvent event) -> {
							Parent root;
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(
										getClass().getResource("/gui/EducationStudent.fxml"));

								root = fxmlLoader.load();
								Stage stage = new Stage();
								stage.setTitle("Students");
								stage.setScene(new Scene(root));

								EducationStudentController controller = fxmlLoader
										.<EducationStudentController>getController();
								@SuppressWarnings("unchecked")
								TablePosition<Education, Integer> pos = table.getSelectionModel().getSelectedCells()
										.get(0);
								int row = pos.getRow();
								Education item = table.getItems().get(row);
								TableColumn<Education, Integer> col = pos.getTableColumn();
								int data = col.getCellObservableValue(item).getValue();
								System.out.println(data);
								controller.setIdOfEducation(data);
								stage.show();
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		studentColumn.setCellFactory(cellFactory);

	}

	private void addButtonToColumnCourse() {

		Callback<TableColumn<Education, Void>, TableCell<Education, Void>> cellFactory = new Callback<TableColumn<Education, Void>, TableCell<Education, Void>>() {
			@Override
			public TableCell<Education, Void> call(final TableColumn<Education, Void> param) {
				final TableCell<Education, Void> cell = new TableCell<Education, Void>() {

					private final Button btn = new Button("Courses");
					{
						btn.setOnAction((ActionEvent event) -> {
							// TODO Gör så att det öppnas ett litet fönster som visar alla educations som
							// denna kurs finns inom, gör så att man kan lägga till och ta bort.
							System.out.println("Opening educations");
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		courseColumn.setCellFactory(cellFactory);

	}

	private void deleteRowWithContextMenuDropdown() {
		table.setRowFactory(new Callback<TableView<Education>, TableRow<Education>>() {
			@Override
			public TableRow<Education> call(TableView<Education> tableView) {
				final TableRow<Education> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Education education = sm.getEducationById(row.getItem().getId());
						for (Student student : education.getStudents()) {
							student.setEducation(null);
						}
						table.getItems().remove(row.getItem());
						sm.deleteEducation(education);
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

}
