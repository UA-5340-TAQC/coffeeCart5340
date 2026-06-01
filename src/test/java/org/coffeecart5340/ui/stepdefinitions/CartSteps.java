package org.coffeecart5340.ui.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.utils.DriverManager;
import org.testng.Assert;

import java.math.BigDecimal;

public class CartSteps {

    private MenuPage menuPage;

    public CartSteps() {
        menuPage = new MenuPage(DriverManager.getDriver());
    }

    @Given("the cart preview is empty before adding any items")
    public void theCartPreviewIsEmptyBeforeAddingItems() {
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewEmpty(),
                "Cart preview should be empty before adding any items.");
    }

    @When("the user adds {int} {string} cups")
    public void theUserAddsCups(int quantity, String coffeeName) {
        menuPage.clickCupMultiply(coffeeName, quantity);
    }

    @Then("the total price is successfully updated to {string}")
    public void theTotalPriceIsSuccessfullyUpdated(String expectedTotal) {
        BigDecimal expectedPrice = new BigDecimal(expectedTotal);
        Assert.assertEquals(menuPage.getTotalButton().getTotalPrice(), expectedPrice,
                "Total price did not update correctly after adding items.");
    }

    @When("the user hovers over the Total button")
    public void theUserHoversOverTheTotalButton() {
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
    }

    @Then("the cart preview becomes visible")
    public void theCartPreviewBecomesVisible() {
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(),
                "Cart preview should be visible after hovering over Total button.");
    }

    @And("the cart preview is no longer empty")
    public void theCartPreviewIsNoLongerEmpty() {
        Assert.assertFalse(menuPage.getTotalButtonMenuComponent().isCartPreviewEmpty(),
                "Cart preview should not be empty after adding items.");
    }

    @And("the {string} coffee is displayed in the cart preview")
    public void theCoffeeIsDisplayedInTheCartPreview(String coffeeName) {
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isItemInCartPreview(coffeeName),
                "Expected coffee item is not displayed in cart preview.");
    }

    @And("the quantity of {string} in the preview matches the expected quantity of {int}")
    public void theQuantityMatchesExpected(String coffeeName, int expectedQuantity) {
        Assert.assertEquals(menuPage.getTotalButtonMenuComponent().getItemQuantityByName(coffeeName), expectedQuantity,
                "Cart preview item count does not match expected quantity.");
    }
}