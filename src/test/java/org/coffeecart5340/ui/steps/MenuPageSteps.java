package org.coffeecart5340.ui.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.coffeecart5340.ui.components.CupCardComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.utils.DriverManager;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class MenuPageSteps {

    private final MenuPage menuPage = new MenuPage(DriverManager.getDriver());

    private CupCardComponent targetCup;

    @When("I locate a specific coffee item {string} on the menu")
    public void locateSpecificCoffeeItem(String coffeeName) {
        targetCup = menuPage.getCupCardByName(coffeeName);
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
        List<CupCardComponent> allCups = menuPage.getAllCupCards();

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
        menuPage.clickCoffeeCup(coffeeName);
    }

    @Then("I verify the appearance of the checkout button {string}")
    public void verifyAppearanceOfCheckoutButton(String expectedPriceText) {
        double expectedPrice = Double.parseDouble(expectedPriceText.replaceAll("[^0-9.]", ""));
        double actualPrice = menuPage.getTotalButton().getTotalPrice();

        Assert.assertEquals(actualPrice, expectedPrice, "The total price on the button is incorrect.");
    }

    @When("I move the mouse cursor over the {string} button without clicking")
    public void hoverOverTotalButton(String buttonName) {
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
    }

    @Then("I verify the appearance and contents of the quick cart preview showing added item")
    public void verifyCartPreviewIsVisible() {
        Assert.assertTrue(
                menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(),
                "The cart preview must be visible after hovering!"
        );
    }

    @When("I move the mouse cursor away from the {string} button and the popup area")
    public void moveCursorAwayFromTotalButton(String buttonName) {
        menuPage.clickCoffeeCup("Mocha");
    }

    @Then("I verify the quick cart preview popup disappears from the screen")
    public void verifyCartPreviewDisappears() {
        Assert.assertFalse(
                menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(),
                "The cart preview must NOT be visible after moving the cursor away!"
        );
    }
}