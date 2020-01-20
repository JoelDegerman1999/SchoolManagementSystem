package gui;

import java.net.URL;
import java.util.ResourceBundle;

import domain.Course;
import domain.Education;
import domain.Student;
import domain.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.SchoolManagement;

public class UpdateNameController implements Initializable {

	private CourseController courseController;
	private StudentController studentController;
	private EducationController educationController;
	private TeacherController teacherController;
	private SchoolManagement sm;

	boolean isCourse;
	boolean isStudent;
	boolean isEducation;
	boolean isTeacher;

	private int id;

	@FXML
	private TextField nameTextField;
	@FXML
	private Button updateNameBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sm = new SchoolManagement();
	}

	public void updateName() {

		if (isCourse) {
			Course course = sm.getCourseById(getId());
			course.setSubject(nameTextField.getText());
			sm.updateCourse(course);
			nameTextField.clear();
			courseController.updateTable();
			Stage stage = (Stage) updateNameBtn.getScene().getWindow();
			stage.close();
			isCourse = false;
		}
		if (isStudent) {
			Student student = sm.getStudentById(getId());
			student.setName(nameTextField.getText());
			sm.updateStudent(student);
			nameTextField.clear();
			studentController.updateTable();
			Stage stage = (Stage) updateNameBtn.getScene().getWindow();
			stage.close();
			isStudent = false;
		}
		if (isTeacher) {
			Teacher teacher = sm.getTeacherById(getId());
			teacher.setName(nameTextField.getText());
			sm.updateTeacher(teacher);
			nameTextField.clear();
			teacherController.updateTable();
			Stage stage = (Stage) updateNameBtn.getScene().getWindow();
			stage.close();
			isTeacher = false;
		}
		if (isEducation) {
			Education education = sm.getEducationById(getId());
			education.setName(nameTextField.getText());
			sm.updateEducation(education);
			nameTextField.clear();
			educationController.updateTableViewToShowEducations();
			Stage stage = (Stage) updateNameBtn.getScene().getWindow();
			stage.close();
			isEducation = false;
		}

	}

	public void setId(int idOfTeacher) {
		this.id = idOfTeacher;
	}

	public int getId() {
		return id;
	}

	public void setCourseController(CourseController controller) {
		this.courseController = controller;
	}

	public void setEducationController(EducationController educationController) {
		this.educationController = educationController;
	}

	public void setStudentController(StudentController studentController) {
		this.studentController = studentController;
	}

	public void setTeacherController(TeacherController teacherController) {
		this.teacherController = teacherController;
	}
}
