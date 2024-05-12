package ru.otus.hw.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Locale;
import java.util.Map;

// Использовать @ConfigurationProperties.
// Сейчас класс соответствует файлу настроек. Чтобы они сюда отобразились нужно только правильно разместить аннотации
@ConfigurationProperties(prefix = "test")
public class AppProperties implements TestConfig, TestFileNameProvider, LocaleConfig, CsvConfig, IoConfig {

    @Getter
    @Setter
    private int rightAnswersCountToPass;

    @Getter
    private Locale locale;

    @Getter
    @Setter
    private int ioMaxAttempts;

    @Setter
    private Map<String, String> fileNameByLocaleTag;

    @NestedConfigurationProperty
    private CsvProps csv;

    public void setLocale(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }

    @Override
    public int getSkipLines() {
        return csv.skipLines();
    }

    @Override
    public char getSeparator() {
        return csv.separator();
    }

    @Override
    public int getMaxAttempts() {
        return ioMaxAttempts;
    }
}
