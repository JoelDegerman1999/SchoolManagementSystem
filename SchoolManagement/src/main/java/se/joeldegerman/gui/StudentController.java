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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import se.joeldegerman.domain.Student;
import se.joeldegerman.service.FrontEndAPI;

public class StudentController implements Initializable {

	private StudentController studentController = this;

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

	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
	FrontEndAPI api = container.getBean(FrontEndAPI.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}

	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		birthDate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		updateTable();
		deleteRowFromTable();
	}

	public void updateTable() {
		table.setItems(api.getAllStudentsAsObservableList());
	}

	private void deleteRowFromTable() {
		table.setRowFactory(new Callback<TableView<Student>, TableRow<Student>>() {
			@Override
			public TableRow<Student> call(TableView<Student> tableView) {
				final TableRow<Student> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				final MenuItem updateMenuItem = new MenuItem("Update");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						api.deleteStudent(row.getItem().getId());
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
							controller.isStudent = true;
							controller.setStudentController(studentController);
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

	public void createStudent() {
		api.createStudent(textFieldName.getText(), datePicker.getValue());
		textFieldName.clear();
		datePicker.getEditor().clear();
		datePicker.setValue(null);
		updateTable();
	}

}
