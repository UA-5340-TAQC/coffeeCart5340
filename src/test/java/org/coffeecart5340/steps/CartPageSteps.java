package org.coffeecart5340.steps;

import io.cucumber.java.en.*;
import org.coffeecart5340.hooks.CucumberHook;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;

public class CartPageSteps {

    private MenuPage menuPage;
    private CartPage cartPage;
    private CucumberHook cucumberHook;

    public CartPageSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
        menuPage = new MenuPage(cucumberHook.getDriver());
        cartPage = new CartPage(cucumberHook.getDriver());
    }



    @Given("I am on the cart page")
    public void i_am_on_the_cart_page() {
        menuPage.goToCartPage();
    }

    @Then("I verify that the quantity increases by {int} next to the Cart button")
    public void i_verify_that_the_quantity_increases_by_next_to_the_cart_button(int number) {
        Assert.assertEquals(
                menuPage.getHeader().getCartCount(),
                number,
                "Cart badge count did not increase after adding an item"
        );
    }

    @Then("I verify that the cart is empty")
    public void i_verify_that_the_cart_is_empty() {
        Assert.assertFalse(cartPage.cartListIsDisplayed(), "Cart list should be empty");
        Assert.assertEquals(cartPage.getNoItemText(), "No coffee, go add some.");
    }
}
