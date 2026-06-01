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
}