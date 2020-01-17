package client;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dataaccess.StatisticsDao;
import dataaccess.StatisticsDaoImpl;
import domain.Course;
import domain.Student;
import gui.FxPreloader;

public class MainTestClient extends FxPreloader {

	public static void main(String[] args) {
		launch(args);
		
	}

}
