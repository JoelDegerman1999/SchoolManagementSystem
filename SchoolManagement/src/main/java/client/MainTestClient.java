package client;

import gui.FxPreloader;
import service.SchoolManagement;

public class MainTestClient extends FxPreloader {

	public static void main(String[] args) {
		new SchoolManagement();
		launch(args);
	}
}
