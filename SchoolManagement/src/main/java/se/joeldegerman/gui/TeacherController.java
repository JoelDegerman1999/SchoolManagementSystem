package se.joeldegerman.gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.beans.binding.Bindings;
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
import se.joeldegerman.domain.Teacher;
import se.joeldegerman.service.FrontEndAPI;

public class TeacherController implements Initializable {

	private TeacherController teacherController = this;

	@FXML
	private TableView<Teacher> table;

	@FXML
	private TableColumn<Teacher, Integer> id;
	@FXML
	private TableColumn<Teacher, String> name;
	@FXML
	private TableColumn<Teacher, LocalDate> dateHired;
	@FXML
	private TableColumn<Teacher, Void> coursesButtonColumn;

	@FXML
	private TextField textFieldName;

	@FXML
	private DatePicker datePicker;

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

		updateTable();
		deleteTableRow();
		addButtonToCoursesColumn();
	}

	public void updateTable() {
		table.setItems(api.getAllTeachesAsObservableList());
	}

	private void deleteTableRow() {
		table.setRowFactory(new Callback<TableView<Teacher>, TableRow<Teacher>>() {
			@Override
			public TableRow<Teacher> call(TableView<Teacher> tableView) {
				final TableRow<Teacher> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				final MenuItem updateMenuItem = new MenuItem("Update");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						api.deleteTeacher(row.getItem().getId());
						table.getItems().remove(row.getItem());
					}
				});
				updateMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Parent root;
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(
									getClass().getResource("/se/joeldegerman/gui/UpdateName.fxml"));

							root = fxmlLoader.load();
							Stage stage = new Stage();
							stage.setTitle("Update");
							stage.setScene(new Scene(root));
							int id = row.getItem().getId();

							UpdateNameController controller = fxmlLoader.<UpdateNameController>getController();
							controller.setId(id);
							controller.isTeacher = true;
							controller.setTeacherController(teacherController);
							stage.show();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				});
				contextMenu.getItems().add(removeMenuItem);
				contextMenu.getItems().add(updateMenuItem);
				// Set context menu on row, but use a binding to make it only show for non-empty
				// rows:
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
	}

	private void addButtonToCoursesColumn() {

		Callback<TableColumn<Teacher, Void>, TableCell<Teacher, Void>> cellFactory = new Callback<TableColumn<Teacher, Void>, TableCell<Teacher, Void>>() {
			@Override
			public TableCell<Teacher, Void> call(final TableColumn<Teacher, Void> param) {
				final TableCell<Teacher, Void> cell = new TableCell<Teacher, Void>() {

					private final Button btn = new Button("Course(s)");
					{
						btn.setOnAction((ActionEvent event) -> {
							Parent root;
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(
										getClass().getResource("/se/joeldegerman/gui/TeacherCourse.fxml"));

								root = fxmlLoader.load();
								Stage stage = new Stage();
								stage.setTitle("Students");
								stage.setScene(new Scene(root));

								TeacherCourseController controller = fxmlLoader
										.<TeacherCourseController>getController();
								@SuppressWarnings("unchecked")
								TablePosition<Teacher, Integer> pos = table.getSelectionModel().getSelectedCells()
										.get(0);
								int row = pos.getRow();
								Teacher item = table.getItems().get(row);
								TableColumn<Teacher, Integer> col = pos.getTableColumn();
								int data = col.getCellObservableValue(item).getValue();
								System.out.println(data);
								controller.setIdOfTeacher(data);
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

		coursesButtonColumn.setCellFactory(cellFactory);

	}

	public void createTeacher() {
		api.createTeacher(textFieldName.getText(), datePicker.getValue());
		textFieldName.clear();
		datePicker.setValue(null);
		datePicker.getEditor().clear();
		updateTable();
	}
}
