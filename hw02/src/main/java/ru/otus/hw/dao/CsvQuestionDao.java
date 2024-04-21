package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Repository
@PropertySource("application.properties")
public class CsvQuestionDao implements QuestionDao {

    private final InputStreamProvider inputStreamProvider;

    private final char separator;

    private final int skipLines;

    public CsvQuestionDao(@Autowired InputStreamProvider inputStreamProvider,
                          @Value("${csv.separator}") char separator,
                          @Value("${csv.skipLines}") int skipLines) {
        this.inputStreamProvider = inputStreamProvider;
        this.separator = separator;
        this.skipLines = skipLines;
    }

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStreamProvider.getInputStream()))) {
            return new CsvToBeanBuilder<QuestionDto>(reader)
                    .withType(QuestionDto.class)
                    .withSkipLines(skipLines)
                    .withSeparator(separator)
                    .build()
                    .parse().stream()
                    .map(QuestionDto::toDomainObject)
                    .toList();
        } catch (Exception e) {
            throw new QuestionReadException(e.getMessage(), e);
        }
    }
}
