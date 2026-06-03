# coffeeCart5340

UI automation framework for the [coffee-cart.app](https://coffee-cart.app) site using Java, Selenium, TestNG, Cucumber, and Allure.

<a href="https://ua-5340-taqc.github.io/coffeeCart5340/main" target="_blank">
  <img src="https://img.shields.io/badge/Allure_Report-🔗-blue?style=for-the-badge" alt="Allure Report">
</a>

## Tech Stack

| Tool / Library         | Version  |
|------------------------|----------|
| Java                   | 21       |
| Maven                  | –        |
| Selenium WebDriver     | 4.25.0   |
| TestNG                 | 7.10.2   |
| Cucumber               | 7.34.3   |
| WebDriverManager       | 6.3.4    |
| Allure Reports         | 2.29.0   |
| Lombok                 | 1.18.46  |
| PicoContainer          | 7.15.0   |
| AspectJ Weaver         | 1.9.22.1 |

## Project Structure

```text
src/main/java/org/coffeecart5340
  ui/
    components/         # reusable page components
      BaseComponent
      CartItemComponent
      CartItemListComponent
      CartPreviewComponent
      CupCardComponent
      CupComponent
      DiscountComponent
      HeaderComponent
      IngredientComponent
      ListItemMenuComponent
      TotalButtonComponent
      TotalButtonMenuComponent
    enumData/           # enumerations
      CoffeeType
      HighlightedStyles
    modals/             # modal dialogs
      AddToCartModal
      BaseModal
      PaymentModal
    pages/              # page objects
      BasePage
      CartPage
      GitHubPage
      MenuPage
    Base.java

src/test/java/org/coffeecart5340
  cucumber/
    hooks/              # Cucumber lifecycle hooks
      CucumberHook
    steps/              # BDD step definitions
      CartPageSteps
      ConfirmPurchaseModalSteps
      MenuPageSteps
      PaymentSteps
    TestRunnerCucumber  # Cucumber test runner
  ui/                   # TestNG UI tests
    CartManagementTests
    CartPreviewTests
    CheckoutEmailValidationTest
    CheckoutWithPromoTest
    DiscountedItemIncreaseButtonDisabledTest
    DoubleClickTranslatingTest
    EmptyCartPageTests
    HeaderNavigationTest
    PaymentModalCheckoutTest
    PaymentModalOpenCloseTest
    PaymentModalPrefilledFieldsTest
    PromoDiscountTests
    TestExample
    testrunners/
      BaseUiTestRunner   # browser setup / teardown
  utils/
    BaseAllureListener   # Allure lifecycle listener
    DriverManager        # WebDriver factory
    TestValueProvider    # config/property reader

src/test/resources
  features/             # Gherkin feature files
    AddingItemUsingRightClick.feature
    cart_hover_preview.feature
    cart_item_quantity_update.feature
    CartPreviewHover.feature
    CompletingOrderCartEmpty.feature
    espresso_macchiato_cup.feature
    FieldsRemainPrefilledAfterClosing.feature
    header_navigation.feature
    ImpossibilityOfAddingExtraDiscountedMocha.feature
    payment_modal_checkout.feature
    promo_discount_after_three_items.feature
    PromoCheck.feature
    PromoDrinkNotAddedAfterDecline.feature
    QuickCartPreviewHover.feature
    RemoveAllItemsFromCart.feature
  allure.properties     # Allure configuration
  env.properties        # runtime test config
  example.env.properties
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

### TestNG (all UI tests)

```bash
mvn clean test
```

Test execution is driven by `testng.xml`, which runs all UI test classes with **2 parallel threads**.

### Cucumber (BDD tests)

The `TestRunnerCucumber` class picks up all feature files under `src/test/resources/features` automatically when running via Maven.

## Generate Allure Report

Run tests first, then generate/serve the report:

```bash
mvn allure:report
mvn allure:serve
```

By default, test results are written to `target/allure-results`.

## Notes

- `BaseUiTestRunner` handles browser setup/teardown for TestNG tests.
- `CucumberHook` handles browser setup/teardown for Cucumber scenarios.
- ChromeDriver binaries are managed automatically by WebDriverManager.
- Dependency injection between Cucumber step classes is handled by PicoContainer.
- `DriverManager` centralises WebDriver creation and thread-safe access.
- `TestValueProvider` loads properties from `env.properties` at runtime.
- `BaseAllureListener` attaches screenshots and logs to Allure on test failure.
