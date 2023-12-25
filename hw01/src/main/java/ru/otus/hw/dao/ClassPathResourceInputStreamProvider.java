package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;

import java.io.InputStream;

@RequiredArgsConstructor
public class ClassPathResourceInputStreamProvider implements InputStreamProvider {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public InputStream getInputStream() {
        return getClass().getClassLoader().getResourceAsStream(fileNameProvider.getTestFileName());
    }
}
