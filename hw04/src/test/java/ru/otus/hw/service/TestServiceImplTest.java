package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class TestServiceImplTest {

    @MockBean
    LocalizedIOService ioServiceMock;
    @MockBean
    QuestionDao questionDaoMock;

    @Autowired
    TestService testService;

    @Test
    void happyPass() {
        // prepare
        when(ioServiceMock.readIntForRangeWithPromptLocalized(any(Integer.class), any(Integer.class), any(String.class), any(String.class))).thenReturn(1);

        when(questionDaoMock.findAll()).thenReturn(
            List.of(new Question("Aaa", List.of(new Answer("Bbb", true))))
        );

        // act
        TestResult testResult = testService.executeTestFor(new Student("James", "Dawson"));

        // verify
        verify(ioServiceMock, times(1)).printLineLocalized("TestService.answer.the.questions");
        verify(ioServiceMock, times(1)).printFormattedLine(any(), any(), any());

        assertEquals(1, testResult.getRightAnswersCount());
    }
}