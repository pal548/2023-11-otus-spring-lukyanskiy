package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestServiceImplTest {

    @Test
    void happyPass() {
        // prepare
        IOService ioServiceMock = mock(IOService.class);

        QuestionDao questionDaoMock = mock(QuestionDao.class);
        when(questionDaoMock.findAll()).thenReturn(
            List.of(new Question("Aaa", List.of(new Answer("Bbb", true))))
        );

        TestService testService = new TestServiceImpl(ioServiceMock, questionDaoMock);

        // act
        testService.executeTest();

        // verify
        verify(ioServiceMock, times(2)).printLine("");
        verify(ioServiceMock, times(1)).printFormattedLine("Please answer the questions below%n");
    }

}