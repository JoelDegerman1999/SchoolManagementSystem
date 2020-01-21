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
			"G�ran", "Ulf", "Thomas", "Bj�rn", "Andreas", "Stefan", "Mattias", "Kjell", "Rikard", "Olle", "Albin",
			"Kurt", "Sten", "Simon", "Tommy", "Patrik", "Joakim", "Dylan", "Enzo", "Elvin", "Caspar", "Erwin", "James",
			"Jaden", "Huxley", "Milton", "Ingvar", "Anna", "Maria", "Eva", "Kristina", "Birgitta", "Karin", "Elisabet",
			"Therese", "Matilda", "Siv", "Camilla", "Lovisa", "Agneta", "Britt", "Linnea", "Kerstin", "Helena", "Emma",
			"Sofia", "Kristina", "Frida", "Lisa", "Charlotte", "Sofia", "Emelie", "Sandra", "Ida", "Gunilla", "Susanna",
			"Louise", "Victoria", "Erika", "M�rta", "Agnes", "Hanna");

	List<String> lastNames = Arrays.asList("Abenius", "Bagger", "Baum", "Blomdahl", "Almkvist", "Bergman", "Brandt",
			"Von Horn", "Silvferstolpe", "Jaklinder", "Leijonstedt", "Cederstr�m", "Taube", "Lewenhaupt",
			"Liljencrantz", "Lagerberg", "Klingspor", "Skj�ldebrand", "Dahlbergh", "Ban�r", "Bj�rnstjerna", "Cronstedt",
			"Adelsw�rd", "Von Hessenstein", "von Saltza", "von Schlippenbach", "af Ugglas", "Wallenstedt",
			"Wachtmeister", "Wirs�n", "Gyldenstolpe", "Gyllenstierna af Fogelvik", "Hamilton", "von Rosen",
			"Wittenberg", "Ribbing", "Wrangel af Sauss", "Rehnski�ld", "Liljencrantz", "Bagge af Boo", "Edelsv�rd",
			"von Ehrenclou", "Lillieqvist", "Stjernsw�rd", "von Drake", "Leuhusen", "von Lietzen", "Lillie af Aspen�s",
			"Cederfeldt", "Lagerstr�m", "von der Lancken von Wackenitz", "von Snoilsky", "Solenblomma", "af Sotberg",
			"Cederberg", "von Castanie", "von Carlsson", "de la Chapelle", "Charpentier", "de Laignier", "Landzberg",
			"af Lehnberg", "Canterhielm", "von B�hnen", "Sj�stierna", "Sj�crona", "Lagercrantz", "af Kullberg",
			"af Sill�n", "Sinclair", "af Klintberg", "Sergel", "af Kleen", "Sch�nstr�m", "Schr�derstierna",
			"Schmilinsky", "von Schmieden", "af Schmidt", "J�gerhorn af Storby", "von Schantz", "von H�pken",
			"von Samson-Himmelstjerna", "Roxendorff", "R�ckersk�ld", "von Rothausen", "Rudbeck",
			"Rosenquist af �kershult", "G�thenstierna");

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
