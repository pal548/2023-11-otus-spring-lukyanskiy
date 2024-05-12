package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            List<Answer> answers = question.answers();
            ioService.printLine(question.text());
            int correctAnswerNum = 0;
            for (int i = 0; i < answers.size(); i++) {
                Answer answer = answers.get(i);
                ioService.printFormattedLine("%d. %s", i + 1, answer.text());
                if (answer.isCorrect()) {
                    correctAnswerNum = i + 1;
                }
            }
            int number = ioService.readIntForRangeWithPromptLocalized(1, answers.size(), "TestService.enter.number", "TestService.incorrect.number");
            var isAnswerValid = number == correctAnswerNum; // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

}
