package se.joeldegerman.gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.beans.binding.Bindings;
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
import se.joeldegerman.domain.Student;
import se.joeldegerman.service.FrontEndAPI;

public class EducationStudentController implements Initializable {

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

	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
	FrontEndAPI api = container.getBean(FrontEndAPI.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}

	private void setup() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
		updateItemsInComboBox();
		deleteRowWithContextMenuDropdown();
	}

	public void updateTableView() {
		table.setItems(api.getAllStudentsConnectedToAEducationAsObservableList(getIdOfEducation()));
	}

	private void updateItemsInComboBox() {
		checkComboBox.getItems().setAll(api.getAllStudentsWhichHasNoEducationToAddIntoComboBox());
	}

	public void addStudentToEducationStudentGroup() {
		api.addStudentToEducation(getIdOfEducation(), checkComboBox.getCheckModel().getCheckedItems());
		updateTableView();
		updateItemsInComboBox();
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
						api.removeStudentFromEducation(getIdOfEducation(), row.getItem().getId());
						table.getItems().remove(row.getItem());
						updateItemsInComboBox();
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
