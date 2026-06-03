package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class MenuPageSteps {

    private final SharedContext context;
    private MenuPage menuPage;
    private CartPage cartPage;
    private final SoftAssert softAssert = new SoftAssert();

    public MenuPageSteps(SharedContext context) {
        this.context = context;
    }

    @Given("I am on the Menu page")
    public void iAmOnTheMenuPage() {
        menuPage = new MenuPage(context.driver);
    }

    @Given("the cart is empty with counter {string}")
    public void theCartIsEmptyWithCounter(String expectedText) {
        softAssert.assertEquals(
                menuPage.getHeader().getCartText(), expectedText,
                "Cart counter should be: " + expectedText
        );
    }

    @When("I click on the {string} coffee cup")
    public void iClickOnTheCoffeeCup(String coffeeName) {
        menuPage.clickCoffeeCup(coffeeName);
    }

    @Then("the cart counter updates to {string}")
    public void theCartCounterUpdatesTo(String expectedText) {
        softAssert.assertEquals(
                menuPage.getHeader().getCartText(), expectedText,
                "Cart counter should update to: " + expectedText
        );
    }

    @When("I click on the cart link {string}")
    public void iClickOnTheCartLink(String cartLinkText) {
        cartPage = menuPage.goToCartPage();
    }

    @Then("I am redirected to the Cart page")
    public void iAmRedirectedToTheCartPage() {
        softAssert.assertTrue(
                context.driver.getCurrentUrl().contains("/cart"),
                "Should be on /cart page"
        );
    }

    @Then("the cart contains the following item:")
    public void theCartContainsTheFollowingItem(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            var cartItem = cartPage.getCartItemByName(row.get("name"));
            softAssert.assertEquals(cartItem.getUnitDescText(), row.get("unitDesc"), "Unit desc mismatch");
            softAssert.assertEquals(cartItem.getTotalPrice(), Float.parseFloat(
                    row.get("total").replace("$", "")), "Total mismatch");
        }
        softAssert.assertAll();
    }
}