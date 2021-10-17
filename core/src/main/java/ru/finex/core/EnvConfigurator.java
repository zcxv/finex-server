package ru.finex.core;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Singleton;

/**
 * @author finfan
 */
@Singleton
public class EnvConfigurator {

    private static final Pattern PATTERN = Pattern.compile("\\$\\{([\\w\\d_]+)}");

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public void configure(Map<Object, Object> configuration) {
        configuration.entrySet().forEach(entry -> {
            String value = entry.getValue().toString();
            Matcher matcher = PATTERN.matcher(value);
            while (matcher.find()) {
                String envName = matcher.group(1);
                String envValue = Matcher.quoteReplacement(System.getenv().get(envName));
                entry.setValue(matcher.replaceFirst(Objects.requireNonNullElse(envValue, StringUtils.EMPTY)));
            }
        });
    }

}
