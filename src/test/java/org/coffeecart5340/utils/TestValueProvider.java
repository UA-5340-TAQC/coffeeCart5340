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
        return properties.getProperty("baseUrl");
    }


}
