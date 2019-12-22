package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
	
	public void openStudents(ActionEvent event) {
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getResource("/gui/Student.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Students");
	            stage.setScene(new Scene(root));
	            stage.show();
	            // Hide this current window (if this is what you want)
//	            ((Node)(event.getSource())).getScene().getWindow().hide();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }  
	}
	
	public void openCourses(ActionEvent event) {
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getResource("/gui/Course.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Courses");
	            stage.setScene(new Scene(root));
	            stage.show();
	            // Hide this current window (if this is what you want)
//	            ((Node)(event.getSource())).getScene().getWindow().hide();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }  
	}
	
	public void openTeachers(ActionEvent event) {
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getResource("/gui/Teacher.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Teachers");
	            stage.setScene(new Scene(root));
	            stage.show();
	            // Hide this current window (if this is what you want)
//	            ((Node)(event.getSource())).getScene().getWindow().hide();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }  
	}
	
	public void openEducation(ActionEvent event) {
		 Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getResource("/gui/Education.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Educations");
	            stage.setScene(new Scene(root));
	            stage.show();
	            // Hide this current window (if this is what you want)
//	            ((Node)(event.getSource())).getScene().getWindow().hide();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }  
	}

}
