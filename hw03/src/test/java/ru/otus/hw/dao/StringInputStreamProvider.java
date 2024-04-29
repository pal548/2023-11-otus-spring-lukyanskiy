package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class StringInputStreamProvider implements InputStreamProvider {
    private final String string;

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }
}
