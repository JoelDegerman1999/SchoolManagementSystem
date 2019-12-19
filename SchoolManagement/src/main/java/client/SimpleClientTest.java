package client;

import java.time.LocalDate;

import gui.FxPreloader;
import service.SchoolManagement;

public class SimpleClientTest extends FxPreloader {

	public static void main(String[] args) {

		SchoolManagement sm = new SchoolManagement();
		
		launch(args);

	}
}
