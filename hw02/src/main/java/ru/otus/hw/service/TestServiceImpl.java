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

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
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
            int number = ioService.readIntForRangeWithPrompt(1, answers.size(), "Enter number of correct answer:", "Incorrect number");
            var isAnswerValid = number == correctAnswerNum; // Задать вопрос, получить ответ
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
