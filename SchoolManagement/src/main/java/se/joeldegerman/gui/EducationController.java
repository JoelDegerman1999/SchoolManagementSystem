package se.joeldegerman.gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;
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
import se.joeldegerman.domain.Education;
import se.joeldegerman.domain.Student;
import se.joeldegerman.service.FrontEndAPI;

public class EducationController implements Initializable {

	private EducationController educationController = this;

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

	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
	FrontEndAPI api = container.getBean(FrontEndAPI.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}

	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		updateTableView();
		addButtonToColumnStudents();
		addButtonToColumnCourse();
		deleteEducation();
	}

	public void createEducation() {
		api.createEducation(nameTextField.getText(), startDatePicker.getValue(), endDatePicker.getValue());
		
		nameTextField.clear();
		startDatePicker.getEditor().clear();
		startDatePicker.setValue(null);
		endDatePicker.getEditor().clear();
		endDatePicker.setValue(null);
		
		updateTableView();
	}

	public void updateTableView() {
		table.setItems(api.getAllEducationsAsObservableList());
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
										getClass().getResource("/se/joeldegerman/gui/EducationStudent.fxml"));

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
							Parent root;
							try {
								FXMLLoader fxmlLoader = new FXMLLoader(
										getClass().getResource("/se/joeldegerman/gui/EducationCourse.fxml"));

								root = fxmlLoader.load();
								Stage stage = new Stage();
								stage.setTitle("Courses");
								stage.setScene(new Scene(root));

								EducationCourseController controller = fxmlLoader
										.<EducationCourseController>getController();
								@SuppressWarnings("unchecked")
								TablePosition<Education, Integer> pos = table.getSelectionModel().getSelectedCells()
										.get(0);
								int row = pos.getRow();
								Education item = table.getItems().get(row);
								TableColumn<Education, Integer> col = pos.getTableColumn();
								int data = col.getCellObservableValue(item).getValue();
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

		courseColumn.setCellFactory(cellFactory);

	}

	private void deleteEducation() {
		table.setRowFactory(new Callback<TableView<Education>, TableRow<Education>>() {
			@Override
			public TableRow<Education> call(TableView<Education> tableView) {
				final TableRow<Education> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				final MenuItem updateMenuItem = new MenuItem("Update");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						api.deleteEducation(row.getItem().getId());
						table.getItems().remove(row.getItem());
					}
				});
				updateMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Parent root;
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/se/joeldegerman/gui/UpdateName.fxml"));

							root = fxmlLoader.load();
							Stage stage = new Stage();
							stage.setTitle("Update");
							stage.setScene(new Scene(root));
							int id = row.getItem().getId();

							UpdateNameController controller = fxmlLoader.<UpdateNameController>getController();
							controller.setId(id);
							controller.isEducation = true;
							controller.setEducationController(educationController);
							stage.show();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				});
				contextMenu.getItems().add(updateMenuItem);
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
