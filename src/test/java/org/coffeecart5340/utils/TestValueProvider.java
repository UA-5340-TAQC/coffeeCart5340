package org.coffeecart5340.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class TestValueProvider {
    private static final String ENV_FILE = "src/test/resources/env.properties";
    private static final String BASE_URL_PROPERTY = "baseUrl";
    private static final String HEADLESS_PROPERTY = "headless";

    private final Properties properties;

    public TestValueProvider() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(ENV_FILE)) {
            properties.load(fis);
        } catch (IOException err) {
            System.err.println("Could not load env.properties file: " + err.getMessage());
            System.err.println("Falling back to environment variables");
        }
    }

    public String getBaseUrl() {
        String baseUrl = readStringValue(BASE_URL_PROPERTY, "BASE_URL");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("Missing required base URL in " + ENV_FILE + " (baseUrl) or BASE_URL environment variable");
        }
        return baseUrl;
    }

    public boolean isHeadless() {
        String value = readStringValue(HEADLESS_PROPERTY, "HEADLESS");
        if (value == null || value.isBlank()) {
            return true;
        }
        return switch (value.trim().toLowerCase(Locale.ROOT)) {
            case "true", "1", "yes" -> true;
            case "false", "0", "no" -> false;
            default -> throw new IllegalArgumentException("Invalid boolean value for headless/HEADLESS: " + value);
        };
    }

    private String readStringValue(String propertyKey, String envKey) {
        String propertyValue = properties.getProperty(propertyKey);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue.trim();
        }
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue.trim();
        }
        return null;
    }
}
