package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

	
	// This method opens up the student FXML file
	public void openStudents(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/Student.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Students");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	// This method opens up the courses FXML file
	public void openCourses(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/Course.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Courses");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// This method opens up the teacher FXML file
	public void openTeachers(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/Teacher.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Teachers");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// This method opens up the education FXML file
	public void openEducation(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/Education.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Educations");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
