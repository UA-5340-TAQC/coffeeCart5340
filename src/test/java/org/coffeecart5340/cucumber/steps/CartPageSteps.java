package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.coffeecart5340.cucumber.hooks.CucumberHook;
import org.coffeecart5340.ui.components.CartItemListComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;

import java.math.BigDecimal;

public class CartPageSteps {


    private final CucumberHook cucumberHook;

    public CartPageSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }


    @Given("I am on the cart page")
    public void i_am_on_the_cart_page() {
        new MenuPage(cucumberHook.getDriver()).goToCartPage();
    }

    @Then("I verify that the quantity increases by {int} next to the Cart button")
    public void i_verify_that_the_quantity_increases_by_next_to_the_cart_button(int number) {
        Assert.assertEquals(
                new MenuPage(cucumberHook.getDriver()).getHeader().getCartCount(),
                number,
                "Cart badge count did not increase after adding an item"
        );
    }

    @Then("I verify that the cart is empty")
    public void i_verify_that_the_cart_is_empty() {
        CartPage cartPage = new CartPage(cucumberHook.getDriver());
        Assert.assertFalse(cartPage.cartListIsDisplayed(), "Cart list should be empty");
        Assert.assertEquals(cartPage.getNoItemText(), "No coffee, go add some.");
    }

    @Given("the cart preview is empty before adding any items")
    public void theCartPreviewIsEmptyBeforeAddingItems() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewEmpty(),
                "Cart preview should be empty before adding any items.");
    }

    @When("the user adds {int} {string} cups")
    public void theUserAddsCups(int quantity, String coffeeName) {
        new MenuPage(cucumberHook.getDriver()).clickCupMultiply(coffeeName, quantity);
    }

    @Then("the total price is successfully updated to {string}")
    public void theTotalPriceIsSuccessfullyUpdated(String expectedTotal) {
        double expectedPrice = Double.parseDouble(expectedTotal);
        Assert.assertEquals(new MenuPage(cucumberHook.getDriver()).getTotalButton().getTotalPrice(), expectedPrice,
                "Total price did not update correctly after adding items."
        );
    }

    @When("the user hovers over the Total button")
    public void theUserHoversOverTheTotalButton() {
        new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().hoverOverTotalButton();
    }

    @Then("the cart preview becomes visible")
    public void theCartPreviewBecomesVisible() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(),
                "Cart preview should be visible after hovering over Total button.");
    }

    @And("the cart preview is no longer empty")
    public void theCartPreviewIsNoLongerEmpty() {
        Assert.assertFalse(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewEmpty(),
                "Cart preview should not be empty after adding items.");
    }

    @And("the {string} coffee is displayed in the cart preview")
    public void theCoffeeIsDisplayedInTheCartPreview(String coffeeName) {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isItemInCartPreview(coffeeName),
                "Expected coffee item is not displayed in cart preview.");
    }

    @And("the quantity of {string} in the preview matches the expected quantity of {int}")
    public void theQuantityMatchesExpected(String coffeeName, int expectedQuantity) {
        Assert.assertEquals(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().getItemQuantityByName(coffeeName), expectedQuantity,
                "Cart preview item count does not match expected quantity.");
    }

    @When("I open the cart page")
    public void OpenCartPage() {
        new MenuPage(cucumberHook.getDriver()).goToCartPage();
    }

    @Then("the cart should contain {string}")
    public void DrinkInCartPage(String coffeeName) {
        CartItemListComponent itemList = new CartPage(cucumberHook.getDriver()).getCartItemList();
        var names = itemList.getAllItemNames();

        Assert.assertTrue(names.contains(coffeeName), "The element must be " + coffeeName);
    }

    @When("I remove {string} from the cart")
    public void RemoveItemFromCart(String coffeeName) {
        new CartPage(cucumberHook.getDriver()).getCartItemList().getItemByName(coffeeName).clickMinusButton();
    }

    @And("the cart should be empty")
    public void EmptyCartCheck() {
        Assert.assertEquals(new CartPage(cucumberHook.getDriver()).getNoItemText(), "No coffee, go add some.", "the cart should be empty");
    }

    @And("the quantity of {string} in the cart should be {int}")
    public void theQuantityOfItemInCartShouldBe(String coffeeName, int expectedQuantity) {
        int actualQuantity = new CartPage(cucumberHook.getDriver()).getCartItemList().getItemByName(coffeeName).getQuantity();
        Assert.assertEquals(actualQuantity, expectedQuantity,
                "Quantity of '" + coffeeName + "' in cart does not match expected " + expectedQuantity);
    }

    @Then("the cart item {string} quantity should be {int}")
    public void theCartItemQuantityShouldBe(String coffeeName, int expectedQuantity) {
        theQuantityOfItemInCartShouldBe(coffeeName, expectedQuantity);
    }

    @When("I navigate back to the menu page")
    public void iNavigateBackToTheMenuPage() {
        new CartPage(cucumberHook.getDriver()).goToMenuPage();
    }

    @Then("the total price on the menu page should be {string}")
    public void theTotalPriceOnMenuPageShouldBe(String expectedTotal) {
        double expected = Double.parseDouble(expectedTotal);
        Assert.assertEquals(
                new MenuPage(cucumberHook.getDriver()).getTotalButton().getTotalPrice(),
                expected,
                "Total price does not match expected $" + expectedTotal);
    }
}
