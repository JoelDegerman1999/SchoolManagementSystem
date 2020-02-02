package se.joeldegerman.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

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
import se.joeldegerman.domain.Course;
import se.joeldegerman.service.FrontEndAPI;

public class EducationCourseController implements Initializable {

	private int idToUse;

	@FXML
	private TableView<Course> table;
	@FXML
	private TableColumn<Course, Integer> id;
	@FXML
	private TableColumn<Course, String> subjectName;
	@FXML
	private TextField idTextField;

	@FXML
	private CheckComboBox<Course> checkComboBox;

	ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
	FrontEndAPI api = container.getBean(FrontEndAPI.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setup();
	}

	private void setup() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		subjectName.setCellValueFactory(new PropertyValueFactory<>("subject"));
		
		deleteRowWithContextMenuDropdown();
	}

	@PostConstruct
	public void updateTableView() {
		table.setItems(api.getAllCoursesConnectedToAEducation(getIdOfEducation()));
		updateItemsInComboBox();
	}

	private void updateItemsInComboBox() {
		checkComboBox.getItems()
				.setAll(api.getAllCoursesWhichIsNotConnectedToAEducationToAddInComboBox(getIdOfEducation()));
	}

	public void addCourseToEducationCourseGroup() {
		api.addCourseToEducation(getIdOfEducation(), checkComboBox.getCheckModel().getCheckedItems());
		updateTableView();
		updateItemsInComboBox();
	}

	private void deleteRowWithContextMenuDropdown() {

		table.setRowFactory(new Callback<TableView<Course>, TableRow<Course>>() {

			@Override
			public TableRow<Course> call(TableView<Course> tableView) {
				final TableRow<Course> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("Remove");
				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						api.removeCourseFromEducation(getIdOfEducation(), row.getItem().getId());
						table.getItems().remove(row.getItem());
						updateItemsInComboBox();
					}
				});
				contextMenu.getItems().add(removeMenuItem);

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
