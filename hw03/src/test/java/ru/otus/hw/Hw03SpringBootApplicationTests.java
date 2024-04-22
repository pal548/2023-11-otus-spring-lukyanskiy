package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Hw03SpringBootApplicationTests {
	@Autowired
	private QuestionDao questionDao;

	@Test
	void contextLoads() {
	}


	@Test
	void questionsAreLoaded() {
		assertEquals(3, questionDao.findAll().size());
	}
}
