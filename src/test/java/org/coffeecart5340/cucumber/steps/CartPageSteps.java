package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.en.*;
import org.coffeecart5340.cucumber.hooks.CucumberHook;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;

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
}
