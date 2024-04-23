package ru.otus.hw.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;
import java.util.Map;

// Использовать @ConfigurationProperties.
// Сейчас класс соответствует файлу настроек. Чтобы они сюда отобразились нужно только правильно разместить аннотации
@ConfigurationProperties(prefix = "test")
public class AppProperties implements TestConfig, TestFileNameProvider, LocaleConfig, CsvConfig {

    @Getter
    private final int rightAnswersCountToPass;

    @Getter
    private final Locale locale;

    private final Map<String, String> fileNameByLocaleTag;

    @NestedConfigurationProperty
    private final CsvProps csv;

    @ConstructorBinding
    public AppProperties(int rightAnswersCountToPass, String locale, Map<String, String> fileNameByLocaleTag, CsvProps csv) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.locale = Locale.forLanguageTag(locale);
        this.fileNameByLocaleTag = fileNameByLocaleTag;
        this.csv = csv;
    }

    /*public void setLocale(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    public void setRightAnswersCountToPass(int rightAnswersCountToPass) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
    }*/

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
}
