package se.joeldegerman.gui;

import java.io.IOException;
import java.net.URL;
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
import se.joeldegerman.domain.Course;
import se.joeldegerman.service.FrontEndAPI;

public class CourseController implements Initializable {
	@FXML
	private TableView<Course> table;

	@FXML
	private TableColumn<Integer, Course> id;
	@FXML
	private TableColumn<String, Course> subjectName;
	@FXML
	private TableColumn<Course, Void> educationButtonColumn;
	@FXML
	private TableColumn<Course, Void> teacherButtonColumn;

	@FXML
	private TextField textFieldName;
	private CourseController courseController = this;

	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
	FrontEndAPI api = container.getBean(FrontEndAPI.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}

	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		subjectName.setCellValueFactory(new PropertyValueFactory<>("subject"));
		updateTableView();
		deleteCourse();
		addButtonToEducationColumn();
		addButtonToTeacherColumn();
	}

	public void updateTableView() {
		table.setItems(api.getAllCoursesAsObservableList());
	}

	public void createCourse() {
		api.createCourse(textFieldName.getText());
		textFieldName.clear();
		updateTableView();
	}

	private void deleteCourse() {
		table.setRowFactory(new Callback<TableView<Course>, TableRow<Course>>() {
			@Override
			public TableRow<Course> call(TableView<Course> tableView) {
				final TableRow<Course> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				final MenuItem updateMenuItem = new MenuItem("Update");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						api.deleteCourse(row.getItem().getId());
						table.getItems().remove(row.getItem());

					}
				});

				updateMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						openUpdateNameView(row);
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

	private void addButtonToEducationColumn() {

		Callback<TableColumn<Course, Void>, TableCell<Course, Void>> cellFactory = new Callback<TableColumn<Course, Void>, TableCell<Course, Void>>() {
			@Override
			public TableCell<Course, Void> call(final TableColumn<Course, Void> param) {
				final TableCell<Course, Void> cell = new TableCell<Course, Void>() {

					private final Button btn = new Button("Education(s)");
					{
						btn.setOnAction((ActionEvent event) -> {
							openEducationsConnectedToCourse();
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

	private void addButtonToTeacherColumn() {

		Callback<TableColumn<Course, Void>, TableCell<Course, Void>> cellFactory = new Callback<TableColumn<Course, Void>, TableCell<Course, Void>>() {
			@Override
			public TableCell<Course, Void> call(final TableColumn<Course, Void> param) {
				final TableCell<Course, Void> cell = new TableCell<Course, Void>() {

					private final Button btn = new Button("Teacher(s)");
					{
						btn.setOnAction((ActionEvent event) -> {
							openTeachersConnectedToCourse();
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

		teacherButtonColumn.setCellFactory(cellFactory);

	}

	private void openEducationsConnectedToCourse() {
		Parent root;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/se/joeldegerman/gui/CourseEducation.fxml"));

			root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Educations");
			stage.setScene(new Scene(root));

			CourseEducationController controller = fxmlLoader.<CourseEducationController>getController();
			@SuppressWarnings("unchecked")
			TablePosition<Course, Integer> pos = table.getSelectionModel().getSelectedCells().get(0);
			int row = pos.getRow();
			Course item = table.getItems().get(row);
			TableColumn<Course, Integer> col = pos.getTableColumn();
			int data = col.getCellObservableValue(item).getValue();
			controller.setIdOfCourse(data);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openTeachersConnectedToCourse() {
		Parent root;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/se/joeldegerman/gui/CourseTeacher.fxml"));

			root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Teachers");
			stage.setScene(new Scene(root));

			CourseTeacherController controller = fxmlLoader.<CourseTeacherController>getController();
			@SuppressWarnings("unchecked")
			TablePosition<Course, Integer> pos = table.getSelectionModel().getSelectedCells().get(0);
			int row = pos.getRow();
			Course item = table.getItems().get(row);
			TableColumn<Course, Integer> col = pos.getTableColumn();
			int data = col.getCellObservableValue(item).getValue();
			controller.setIdOfCourse(data);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void openUpdateNameView(final TableRow<Course> row) {
		Parent root;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/se/joeldegerman/gui/UpdateName.fxml"));

			root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Update");
			stage.setScene(new Scene(root));
			int id = row.getItem().getId();

			UpdateNameController controller = fxmlLoader.<UpdateNameController>getController();
			System.out.println(id);
			controller.setId(id);
			controller.isCourse = true;
			controller.setCourseController(courseController);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
