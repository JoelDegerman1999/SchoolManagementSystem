package client;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dataaccess.StatisticsDao;
import dataaccess.StatisticsDaoImpl;
import domain.Course;
import domain.Student;
import gui.FxPreloader;
import service.GenerateDatabase;
import service.SchoolManagement;

public class MainTestClient extends FxPreloader {

	 public static void main(String[] args) {
		 SchoolManagement CHARGINGMAHLAZR = new SchoolManagement();
		 GenerateDatabase db = new GenerateDatabase();
		 db.addStudents(25);
		 db.addStudents(25);
		 db.addStudents(25);
		 db.addStudents(25);
		 
		 launch(args);
		
	 }

}
