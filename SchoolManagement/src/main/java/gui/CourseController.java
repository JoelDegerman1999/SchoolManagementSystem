package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import domain.Course;
import domain.Education;
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

	private SchoolManagement sm;
	private CourseController courseController = this;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sm = new SchoolManagement();
		setup();
	}

	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		subjectName.setCellValueFactory(new PropertyValueFactory<>("subject"));

		updateTable();
		deleteTableRow();
		addButtonToEducationColumn();
		addButtonToTeacherColumn();
	}

	public void updateTable() {
		table.getItems().clear();
		ObservableList<Course> observableList = FXCollections.observableArrayList();

		List<Course> courses = sm.getAllCoursesWithEducations();

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
				final MenuItem updateMenuItem = new MenuItem("Update");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Course course = sm.getCourseByIdWithEducations(row.getItem().getId());
						List<Education> educations = sm.getAllEducationsWithCourses();
						for (Education education: educations) {
							education.removeCourseFromEducation(course);
							sm.updateEducation(education);
						}
						sm.deleteCourse(row.getItem());
						table.getItems().remove(row.getItem());

					}
				});
				
				updateMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Parent root;
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(
									getClass().getResource("/gui/UpdateName.fxml"));

							root = fxmlLoader.load();
							Stage stage = new Stage();
							stage.setTitle("Update");
							stage.setScene(new Scene(root));
							int id = row.getItem().getId();

							UpdateNameController controller = fxmlLoader
									.<UpdateNameController>getController();
							controller.setId(id);
							controller.isCourse = true;
							controller.setCourseController(courseController);
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

	private void addButtonToEducationColumn() {

		Callback<TableColumn<Course, Void>, TableCell<Course, Void>> cellFactory = new Callback<TableColumn<Course, Void>, TableCell<Course, Void>>() {
			@Override
			public TableCell<Course, Void> call(final TableColumn<Course, Void> param) {
				final TableCell<Course, Void> cell = new TableCell<Course, Void>() {

					private final Button btn = new Button("Education(s)");
					{
						btn.setOnAction((ActionEvent event) -> {
							Parent root;
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(
										getClass().getResource("/gui/CourseEducation.fxml"));

								root = fxmlLoader.load();
								Stage stage = new Stage();
								stage.setTitle("Educations");
								stage.setScene(new Scene(root));

								CourseEducationController controller = fxmlLoader
										.<CourseEducationController>getController();
								@SuppressWarnings("unchecked")
								TablePosition<Course, Integer> pos = table.getSelectionModel().getSelectedCells()
										.get(0);
								int row = pos.getRow();
								Course item = table.getItems().get(row);
								TableColumn<Course, Integer> col = pos.getTableColumn();
								int data = col.getCellObservableValue(item).getValue();
								controller.setIdOfCourse(data);
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
							Parent root;
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(
										getClass().getResource("/gui/CourseTeacher.fxml"));

								root = fxmlLoader.load();
								Stage stage = new Stage();
								stage.setTitle("Teachers");
								stage.setScene(new Scene(root));

								CourseTeacherController controller = fxmlLoader
										.<CourseTeacherController>getController();
								@SuppressWarnings("unchecked")
								TablePosition<Course, Integer> pos = table.getSelectionModel().getSelectedCells()
										.get(0);
								int row = pos.getRow();
								Course item = table.getItems().get(row);
								TableColumn<Course, Integer> col = pos.getTableColumn();
								int data = col.getCellObservableValue(item).getValue();
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
		
		teacherButtonColumn.setCellFactory(cellFactory);
		
	}

	public void createCourse() {
		sm.createCourse(textFieldName.getText());
		textFieldName.clear();
		updateTable();
	}
}
