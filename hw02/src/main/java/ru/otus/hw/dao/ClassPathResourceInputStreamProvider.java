package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class ClassPathResourceInputStreamProvider implements InputStreamProvider {

    private final TestFileNameProvider fileNameProvider;

    @Override
    public InputStream getInputStream() {
        return getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName());
    }
}
