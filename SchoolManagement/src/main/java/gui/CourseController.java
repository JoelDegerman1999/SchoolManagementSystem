package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import domain.Course;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.SchoolManagement;

public class CourseController implements Initializable {
	@FXML
	TableView<Course> table;

	@FXML
	private TableColumn<Integer, Course> id;
	@FXML
	private TableColumn<String, Course> subjectName;
	@FXML
	private TableColumn<Course, Void> educationButtonColumn;

	@FXML
	private TextField textFieldName;

	private SchoolManagement sm;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sm = new SchoolManagement();

		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		subjectName.setCellValueFactory(new PropertyValueFactory<>("subject"));

		updateTableView();
		deleteTableRow();
		addButtonToTable();
	}

	private void updateTableView() {
		table.getItems().clear();
		ObservableList<Course> observableList = FXCollections.observableArrayList();

		List<Course> courses = sm.getAllCourses();

		for (Course course : courses) {
			observableList.add(course);
		}

		table.setItems(observableList);
	}

	private void deleteTableRow() {
		table.setRowFactory(new Callback<TableView<Course>, TableRow<Course>>() {
			@Override
			public TableRow<Course> call(TableView<Course> tableView) {
				final TableRow<Course> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						sm.deleteCourse(row.getItem());
						table.getItems().remove(row.getItem());

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

	private void addButtonToTable() {

		Callback<TableColumn<Course, Void>, TableCell<Course, Void>> cellFactory = new Callback<TableColumn<Course, Void>, TableCell<Course, Void>>() {
			@Override
			public TableCell<Course, Void> call(final TableColumn<Course, Void> param) {
				final TableCell<Course, Void> cell = new TableCell<Course, Void>() {

					private final Button btn = new Button("Educations");
					{
						btn.setOnAction((ActionEvent event) ->{
							// TODO Gör så att det öppnas ett litet fönster som visar alla educations som denna kurs finns inom, gör så att man kan lägga till och ta bort.
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

		educationButtonColumn.setCellFactory(cellFactory);


	}

	public void createCourse() {
		sm.createCourse(textFieldName.getText());
		updateTableView();
	}
}
