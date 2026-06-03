package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.en.*;
import lombok.Getter;
import org.coffeecart5340.cucumber.hooks.CucumberHook;
import org.coffeecart5340.ui.components.CupCardComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class MenuPageSteps {

    @Getter
    private final CucumberHook cucumberHook;

    private CupCardComponent targetCup;

    public MenuPageSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    @Given("I am on the menu page")
    public void i_am_on_the_menu_page() {
        cucumberHook.getDriver().get("/");
    }

    @Given("I have an empty cart")
    public void i_have_an_empty_cart() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewEmpty(),
                "cart preview should be empty before adding any items");
    }

    @When("I right-click on the {string} coffee cup")
    public void i_right_click_on_the_coffee_cup(String string) {
        new MenuPage(cucumberHook.getDriver()).getCupCardByName(string)
                .rightClickCup();
    }

    @When("I click on the coffee cups:")
    public void i_click_on_the_coffee_cups(List<String> coffeeNames) {
        MenuPage menuPage = new MenuPage(cucumberHook.getDriver());
        for(String coffeeName : coffeeNames){
            menuPage.clickCoffeeCup(coffeeName);
        }
    }

    @Then("I verify that the lucky modal day appears")
    public void i_verify_that_the_lucky_modal_day_appears() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getDiscountModal().isDiscountMenuVisible(),
                "Lucky day modal should be displayed after right-clicking on a coffee cup");
    }

    @When("I click on the Yes button")
    public void i_click_on_the_yes_button() {
        new MenuPage(cucumberHook.getDriver()).getDiscountModal().clickYesButton();
    }
    @Then("I verify that the lucky modal discount disapears")
    public void i_verify_that_the_lucky_modal_discount_disapears() {
        Assert.assertFalse(new MenuPage(cucumberHook.getDriver()).getDiscountModal().isDiscountMenuVisible(),
                "Lucky day modal should not be displayed after clicking the Yes button");
    }
    @When("I hover over the total checkout button")
    public void i_hover_over_the_total_checkout_button() {
        new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().hoverOverTotalButton();
    }
    @Then("I verify that checkout menu appears with added items")
    public void i_verify_that_checkout_menu_appears_with_added_items() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(),
                "Cart preview should be visible after hovering over the total checkout button");

    }

    @When("I navigate to the cart page")
    public void i_navigate_to_the_page() {
        new MenuPage(cucumberHook.getDriver()).goToCartPage();
    }

    @When("I locate a specific coffee item {string} on the menu")
    public void locateSpecificCoffeeItem(String coffeeName) {
        targetCup = new MenuPage(cucumberHook.getDriver()).getCupCardByName(coffeeName);
    }

    @Then("I verify the initial language of the coffee title is {string}")
    public void verifyInitialLanguageOfCoffeeTitle(String expectedTitle) {
        Assert.assertEquals(targetCup.getCupTitleText(), expectedTitle, "Initial coffee title is incorrect!");
    }

    @When("I perform a double-click action exactly on the text of the coffee title")
    @When("I double-click again on the text of same coffee title")
    public void performDoubleClickAction() {
        targetCup.translateCupTitle();
    }

    @Then("I verify the language of the clicked coffee title immediately changes to {string}")
    public void verifyLanguageOfClickedCoffeeTitleChangesTo(String expectedTitle) {
        Assert.assertEquals(targetCup.getCupTitleText(), expectedTitle, "The coffee title did not translate correctly!");
    }

    @And("I check the titles of the other coffee items on the menu remain in English")
    public void checkOtherTitlesRemainEnglish() {
        SoftAssert softAssert = new SoftAssert();
        List<CupCardComponent> allCups = new MenuPage(cucumberHook.getDriver()).getAllCupCards();

        for (CupCardComponent cup : allCups) {
            String originalEnglishName = cup.getCupName();

            if (targetCup != null && !originalEnglishName.equals(targetCup.getCupName())) {
                softAssert.assertEquals(
                        cup.getCupTitleText(),
                        originalEnglishName,
                        "Bug: The coffee '" + originalEnglishName + "' was accidentally translated!"
                );
            }
        }
        softAssert.assertAll();
    }

    @When("I click on any coffee cup {string} on the menu to add it to the cart")
    public void clickOnCoffeeCupToAdd(String coffeeName) {
        new MenuPage(cucumberHook.getDriver()).clickCoffeeCup(coffeeName);
    }

    @Then("I verify the appearance of the checkout button {string}")
    public void verifyAppearanceOfCheckoutButton(String expectedPriceText) {
        double expectedPrice = Double.parseDouble(expectedPriceText.replaceAll("[^0-9.]", ""));
        double actualPrice = new MenuPage(cucumberHook.getDriver()).getTotalButton().getTotalPrice();

        Assert.assertEquals(actualPrice, expectedPrice, "The total price on the button is incorrect.");
    }

    @When("I move the mouse cursor over the {string} button without clicking")
    public void hoverOverTotalButton(String buttonName) {
        new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().hoverOverTotalButton();
    }

    @Then("I verify the appearance and contents of the quick cart preview showing added item")
    public void verifyCartPreviewIsVisible() {
        Assert.assertTrue(
                new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(),
                "The cart preview must be visible after hovering!"
        );
    }

    @When("I move the mouse cursor away from the {string} button and the popup area")
    public void moveCursorAwayFromTotalButton(String buttonName) {
        new MenuPage(cucumberHook.getDriver()).clickCoffeeCup("Mocha");
    }

    @Then("I verify the quick cart preview popup disappears from the screen")
    public void verifyCartPreviewDisappears() {
        Assert.assertFalse(
                new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(),
                "The cart preview must NOT be visible after moving the cursor away!"
        );
    }

}
