package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import ru.otus.hw.config.CsvConfig;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvQuestionDaoTest {

    @Test
    void happyPass() {
        // prepare
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(
                new CsvConfig() {
                    @Override
                    public int getSkipLines() {
                        return 1;
                    }

                    @Override
                    public char getSeparator() {
                        return ';';
                    }
                },
                new StringInputStreamProvider("""
                        # Добавить сюда своих вопросов. Эту строку надо пропустить при настройке CsvToBean (withSkipLines)
                        Is there life on Mars?;Science doesn't know this yet%true|Certainly. The red UFO is from Mars. And green is from Venus%false|Absolutely not%false
                        How should resources be loaded form jar in Java?;ClassLoader#geResourceAsStream or ClassPathResource#getInputStream%true|ClassLoader#geResource#getFile + FileReader%false|Wingardium Leviosa%false
                        Which option is a good way to handle the exception?;@SneakyThrow%false|e.printStackTrace()%false|Rethrow with wrapping in business exception (for example, QuestionReadException)%true|Ignoring exception%false
                        What was the name of main character in the Titanic movie?;Jack Dawson%true|John Smith%false|Sam Wilson%false|James Flutter%false
                        """)
        );

        // act
        List<Question> questions = csvQuestionDao.findAll();

        // verify
        assertThat(questions).hasSize(4);
        assertThat(questions.stream().map(Question::text)).containsExactly(
                "Is there life on Mars?",
                "How should resources be loaded form jar in Java?",
                "Which option is a good way to handle the exception?",
                "What was the name of main character in the Titanic movie?"
        );

        assertThat(questions.stream().map(Question::answers).map(List::size)).containsExactly(
                3, 3, 4, 4
        );

        assertThat(
                questions.stream()
                        .map(Question::answers)
                        .map(answers -> answers.stream().filter(Answer::isCorrect).findFirst())
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(Answer::text)
        ).containsExactly(
                "Science doesn't know this yet",
                "ClassLoader#geResourceAsStream or ClassPathResource#getInputStream",
                "Rethrow with wrapping in business exception (for example, QuestionReadException)",
                "Jack Dawson"
        );
    }

    @Test
    void errorPass() {
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(null, null);

        assertThrows(QuestionReadException.class, csvQuestionDao::findAll);
    }
}