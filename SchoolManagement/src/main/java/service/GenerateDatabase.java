package service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import domain.Education;
import domain.Student;

public class GenerateDatabase {
	SchoolManagement sm = new SchoolManagement();
	int idCounter = 1;

	List<String> firstNames = Arrays.asList("Erik", "Johan", "Lars", "Anders", "Per", "Carl", "Mikael", "Alexander",
			"Göran", "Ulf", "Thomas", "Björn", "Andreas", "Stefan", "Mattias", "Kjell", "Rikard", "Olle", "Albin",
			"Kurt", "Sten", "Simon", "Tommy", "Patrik", "Joakim", "Dylan", "Enzo", "Elvin", "Caspar", "Erwin", "James",
			"Jaden", "Huxley", "Milton", "Ingvar", "Anna", "Maria", "Eva", "Kristina", "Birgitta", "Karin", "Elisabet",
			"Therese", "Matilda", "Siv", "Camilla", "Lovisa", "Agneta", "Britt", "Linnea", "Kerstin", "Helena", "Emma",
			"Sofia", "Kristina", "Frida", "Lisa", "Charlotte", "Sofia", "Emelie", "Sandra", "Ida", "Gunilla", "Susanna",
			"Louise", "Victoria", "Erika", "Märta", "Agnes", "Hanna");

	List<String> lastNames = Arrays.asList("Abenius", "Bagger", "Baum", "Blomdahl", "Almkvist", "Bergman", "Brandt",
			"Von Horn", "Silvferstolpe", "Jaklinder", "Leijonstedt", "Cederström", "Taube", "Lewenhaupt",
			"Liljencrantz", "Lagerberg", "Klingspor", "Skjöldebrand", "Dahlbergh", "Banér", "Björnstjerna", "Cronstedt",
			"Adelswärd", "Von Hessenstein", "von Saltza", "von Schlippenbach", "af Ugglas", "Wallenstedt",
			"Wachtmeister", "Wirsén", "Gyldenstolpe", "Gyllenstierna af Fogelvik", "Hamilton", "von Rosen",
			"Wittenberg", "Ribbing", "Wrangel af Sauss", "Rehnskiöld", "Liljencrantz", "Bagge af Boo", "Edelsvärd",
			"von Ehrenclou", "Lillieqvist", "Stjernswärd", "von Drake", "Leuhusen", "von Lietzen", "Lillie af Aspenäs",
			"Cederfeldt", "Lagerström", "von der Lancken von Wackenitz", "von Snoilsky", "Solenblomma", "af Sotberg",
			"Cederberg", "von Castanie", "von Carlsson", "de la Chapelle", "Charpentier", "de Laignier", "Landzberg",
			"af Lehnberg", "Canterhielm", "von Böhnen", "Sjöstierna", "Sjöcrona", "Lagercrantz", "af Kullberg",
			"af Sillén", "Sinclair", "af Klintberg", "Sergel", "af Kleen", "Schönström", "Schröderstierna",
			"Schmilinsky", "von Schmieden", "af Schmidt", "Jägerhorn af Storby", "von Schantz", "von Höpken",
			"von Samson-Himmelstjerna", "Roxendorff", "Rückersköld", "von Rothausen", "Rudbeck",
			"Rosenquist af Åkershult", "Göthenstierna");

	// !!! Array is limited - do not call with value over 50
	public void addStudents(int numberOfStudentsToAdd) {
		Education edu = sm.getEducationByIdWithStudents(idCounter);

		Collections.shuffle(firstNames);
		Collections.shuffle(lastNames);

		for (int i = 0; i < numberOfStudentsToAdd; i++) {

			edu.addStudentToEducation(new Student(firstNames.get(i) + " " + lastNames.get(i), getRandomDate()));

		}

		sm.updateEducation(edu);

		idCounter++;
		if (idCounter == 5) {
			idCounter = 1;
		}
	}

	public LocalDate getRandomDate() {
		Random r = new Random();

		int day = r.nextInt(27) + 1;
		int month = r.nextInt(11) + 1;
		int year = r.nextInt(79) + 1921;

		LocalDate randomBirthDate = LocalDate.of(year, month, day);

		return randomBirthDate;
	}

}
