# coffeeCart5340

UI automation framework for the [coffee-cart.app](https://coffee-cart.app) site using Java, Selenium, TestNG, and Allure.


**[allure:report](https://ua-5340-taqc.github.io/coffeeCart5340/main)**

## Tech Stack

- Java 21
- Maven
- Selenium WebDriver
- TestNG
- WebDriverManager
- Allure Reports

## Project Structure

```text
src/main/java/org/coffeecart5340
  ui/
    components/   # reusable page components
    pages/        # page objects

src/test/java/org/coffeecart5340
  ui/             # UI tests
  utils/          # test helpers and config providers

src/test/resources
  env.properties  # runtime test config
```

## Prerequisites

- Java 21 installed and available on `PATH`
- Maven installed and available on `PATH`
- Google Chrome installed

## Configuration

Tests read `baseUrl` from `src/test/resources/env.properties`.

Example:

```properties
baseUrl=https://coffee-cart.app
```

You can use `src/test/resources/example.env.properties` as a template.

## Run Tests

```bash
mvn clean test
```

## Generate Allure Report

Run tests first, then generate/serve the report:

```bash
mvn allure:report
mvn allure:serve
```

By default, test results are written to `target/allure-results`.

## Notes

- `BaseUiTestRunner` handles browser setup/teardown.
- ChromeDriver binaries are managed automatically by WebDriverManager.
- The sample test `TestExample` demonstrates page-object/component chaining through the header navigation.
