package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final InputStreamProvider inputStreamProvider;

    private final char separator;

    private final int skipLines;

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
