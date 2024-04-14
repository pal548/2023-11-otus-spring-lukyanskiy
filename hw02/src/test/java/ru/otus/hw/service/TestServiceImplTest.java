package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestServiceImplTest {

    @Test
    void happyPass() {
        // prepare
        IOService ioServiceMock = mock(IOService.class);
        when(ioServiceMock.readIntForRangeWithPrompt(any(Integer.class), any(Integer.class), any(String.class), any(String.class))).thenReturn(1);

        QuestionDao questionDaoMock = mock(QuestionDao.class);
        when(questionDaoMock.findAll()).thenReturn(
            List.of(new Question("Aaa", List.of(new Answer("Bbb", true))))
        );

        TestService testService = new TestServiceImpl(ioServiceMock, questionDaoMock);

        // act
        TestResult testResult = testService.executeTestFor(new Student("James", "Dawson"));

        // verify
        verify(ioServiceMock, times(1)).printLine("");
        verify(ioServiceMock, times(1)).printFormattedLine("Please answer the questions below%n");

        assertEquals(1, testResult.getRightAnswersCount());
    }

}