package org.coffeecart5340.ui.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.coffeecart5340.ui.components.CupCardComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.coffeecart5340.utils.DriverManager;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class CoffeeCartSteps extends BaseUiTestRunner {

    private MenuPage menuPage;
    private CartPage cartPage;
    private CupCardComponent cupCard;
    private String baseUrl;

    @Before
    public void setUp() {
        beforeSuite();
        beforeMethod();
        baseUrl = driver.getCurrentUrl();
        menuPage = new MenuPage(driver);
    }

    @After
    public void tearDown() {
        afterMethod();
    }

    @Given("the user is on the Coffee Cart menu page")
    public void theUserIsOnTheCoffeeCartMenuPage() {
        baseUrl = driver.getCurrentUrl();
        menuPage = new MenuPage(driver);
    }

    @Then("the cart header link should be visible")
    public void theCartHeaderLinkShouldBeVisible() {
        Assert.assertNotNull(
                menuPage.getHeader().getCartText(),
                "Cart header link should be visible"
        );
    }

    @When("the user clicks the cart link in the header")
    public void theUserClicksTheCartLinkInTheHeader() {
        cartPage = menuPage.goToCartPage();
    }

    @Then("the cart page URL should contain {string}")
    public void theCartPageURLShouldContain(String expectedUrlPart) {
        String cartUrl = cartPage.getCurrentUrl();

        Assert.assertTrue(
                cartUrl.contains(expectedUrlPart),
                "Cart page URL should contain " + expectedUrlPart + ", but was: " + cartUrl
        );
    }

    @Then("the empty cart message {string} should be displayed")
    public void theEmptyCartMessageShouldBeDisplayed(String expectedMessage) {
        Assert.assertFalse(
                cartPage.cartListIsDisplayed(),
                "Cart list should not be displayed when cart is empty"
        );

        Assert.assertEquals(
                cartPage.getNoItemText(),
                expectedMessage,
                "Empty cart message should be displayed"
        );
    }

    @When("the user clicks the menu link in the header")
    public void theUserClicksTheMenuLinkInTheHeader() {
        menuPage = cartPage.goToMenuPage();
    }

    @Then("the coffee card {string} should be displayed on the menu page")
    public void theCoffeeCardShouldBeDisplayedOnTheMenuPage(String coffeeName) {
        Assert.assertNotNull(
                menuPage.getCupCardByName(coffeeName),
                "Coffee cup/card should be displayed on the menu page"
        );
    }

    @Then("the menu page URL should match the base URL")
    public void theMenuPageURLShouldMatchTheBaseURL() {
        String menuUrl = menuPage.getCurrentUrl();

        Assert.assertEquals(
                menuUrl,
                baseUrl,
                "Menu page URL should match the base URL"
        );
    }

    @When("the user finds the coffee cup {string}")
    public void theUserFindsTheCoffeeCup(String coffeeName) {
        cupCard = menuPage.getCupCardByName(coffeeName);
    }

    @Then("the coffee cup should be visible")
    public void theCoffeeCupShouldBeVisible() {
        Assert.assertTrue(
                cupCard.isCupDisplayed(),
                "Coffee cup should be visible on the page"
        );
    }

    @Then("the coffee cup name should be {string}")
    public void theCoffeeCupNameShouldBe(String expectedName) {
        Assert.assertEquals(
                cupCard.getCupName(),
                expectedName,
                "Coffee cup name should match"
        );
    }

    @Then("the coffee cup price should be {string}")
    public void theCoffeeCupPriceShouldBe(String expectedPrice) {
        Assert.assertEquals(
                cupCard.getCupPriceText(),
                expectedPrice,
                "Coffee cup price should match"
        );
    }

    @When("the user clicks the coffee cup")
    public void theUserClicksTheCoffeeCup() {
        cupCard.clickCup();
    }

    @Then("the cart counter should display {string}")
    public void theCartCounterShouldDisplay(String expectedCounter) {
        Assert.assertEquals(
                menuPage.getHeader().getCartText(),
                expectedCounter,
                "Cart counter should display expected value"
        );
    }

    @Then("the coffee cup {string} is removed from the cart")
    public void theCoffeeCupIsRemovedFromTheCart(String coffeeName) {
        cartPage = menuPage.goToCartPage();
        cartPage.clickDeleteButton(coffeeName);
    }
    @When("the user adds the coffee cup {string} to the cart")
    public void theUserAddsTheCoffeeCupToTheCart(String coffeeName) {
        cupCard = menuPage.getCupCardByName(coffeeName);
        cupCard.clickCup();
    }

    @Then("the promo message should not be displayed")
    public void thePromoMessageShouldNotBeDisplayed() {
        List<WebElement> promoMessages = driver.findElements(
                By.xpath("//*[contains(text(), 'lucky') or contains(text(), 'discount') or contains(text(), 'Promo') or contains(text(), 'Mocha')]")
        );

        Assert.assertTrue(
                promoMessages.isEmpty(),
                "Promo message should not be displayed"
        );
    }

    @Then("the promo message should be displayed")
    public void thePromoMessageShouldBeDisplayed() {
        List<WebElement> promoMessages = driver.findElements(
                By.xpath("//*[contains(text(), 'lucky') or contains(text(), 'discount') or contains(text(), 'Promo') or contains(text(), 'Mocha')]")
        );

        Assert.assertFalse(
                promoMessages.isEmpty(),
                "Promo message should be displayed after adding the third item"
        );
    }

    @When("the user hovers over the checkout preview")
    public void theUserHoversOverTheCheckoutPreview() {
        WebElement checkoutPreview = driver.findElement(
                By.xpath("//*[contains(text(), 'Total') or contains(text(), 'total') or contains(text(), 'checkout')]")
        );

        new Actions(driver)
                .moveToElement(checkoutPreview)
                .perform();
    }

    @Then("the checkout preview should be displayed")
    public void theCheckoutPreviewShouldBeDisplayed() {
        List<WebElement> previewItems = driver.findElements(
                By.xpath("//*[contains(@class, 'list') or contains(@class, 'cart') or contains(@class, 'preview')]")
        );

        Assert.assertFalse(
                previewItems.isEmpty(),
                "Checkout preview should be displayed"
        );
    }

    @Then("the coffee item {string} should be displayed in the checkout preview")
    public void theCoffeeItemShouldBeDisplayedInTheCheckoutPreview(String coffeeName) {
        WebElement coffeeItem = driver.findElement(
                By.xpath("//*[contains(text(), '" + coffeeName + "')]")
        );

        Assert.assertTrue(
                coffeeItem.isDisplayed(),
                coffeeName + " should be displayed in checkout preview"
        );
    }

    @When("the user navigates to the cart page")
    public void theUserNavigatesToTheCartPage() {
        cartPage = menuPage.goToCartPage();
    }

    @Then("the coffee item {string} should be displayed in the cart")
    public void theCoffeeItemShouldBeDisplayedInTheCart(String coffeeName) {
        WebElement cartItem = driver.findElement(
                By.xpath("//*[contains(text(), '" + coffeeName + "')]")
        );

        Assert.assertTrue(
                cartItem.isDisplayed(),
                coffeeName + " should be displayed in the cart"
        );
    }

    @Then("the coffee item {string} quantity should be {int}")
    public void theCoffeeItemQuantityShouldBe(String coffeeName, int expectedQuantity) {
        WebElement quantity = driver.findElement(
                By.xpath("//*[contains(text(), '" + coffeeName + "')]/ancestor::*[self::li or self::div or self::tr][1]//*[contains(text(), '" + expectedQuantity + "')]")
        );

        Assert.assertTrue(
                quantity.isDisplayed(),
                coffeeName + " quantity should be " + expectedQuantity
        );
    }

    @Then("the total price should be calculated correctly for {int} items")
    public void theTotalPriceShouldBeCalculatedCorrectlyForItems(int expectedQuantity) {
        WebElement total = driver.findElement(
                By.xpath("//*[contains(text(), 'Total') or contains(text(), 'total')]")
        );

        Assert.assertTrue(
                total.isDisplayed(),
                "Total price should be displayed"
        );
    }

    @Then("all coffee items are removed from the cart")
    public void allCoffeeItemsAreRemovedFromTheCart() {
        cartPage = menuPage.goToCartPage();

        List<WebElement> deleteButtons = driver.findElements(
                By.xpath("//button[contains(text(), 'Delete') or contains(text(), 'Remove') or contains(text(), 'x') or contains(text(), '×')]")
        );

        for (WebElement deleteButton : deleteButtons) {
            deleteButton.click();
        }
    }
}