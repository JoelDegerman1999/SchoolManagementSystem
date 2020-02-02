package se.joeldegerman.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import se.joeldegerman.domain.Student;

class SchoolServiceTest {

	@BeforeEach
	void setUp() throws Exception {
	
	}

	@Test
	void test() {
		ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
		SchoolService sm = container.getBean(SchoolService.class);
		
		Student student = sm.getStudentById(1);
		
		assertEquals(student, sm.getStudentById(1));
		
		container.close();
	}

}
