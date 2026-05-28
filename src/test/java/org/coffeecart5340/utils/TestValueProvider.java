package org.coffeecart5340.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestValueProvider {
    private Properties properties;

    public TestValueProvider() {
        try (FileInputStream fis = new FileInputStream("src/test/resources/env.properties")) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException err) {
            System.err.println("Could not load env.properties file: " + err.getMessage());
            System.err.println("Use system properties");
        }
    }

    public String getBaseUrl() {
        String baseUrl;
        if (properties != null) {
            baseUrl = properties.getProperty("baseUrl");
            System.out.println("[properties] baseUrl: " + baseUrl);
            return baseUrl;
        }
        baseUrl = System.getenv("BASE_URL");
        System.out.println("[System] baseUrl (BASE_URL): " + baseUrl);
        return baseUrl;
    }

    public Boolean isHeadless() {
        if (properties != null) {
            return Boolean.parseBoolean(properties.getProperty("headless",  "true"));
        }
        return Boolean.parseBoolean(System.getenv("HEADLESS"));
    }

}
