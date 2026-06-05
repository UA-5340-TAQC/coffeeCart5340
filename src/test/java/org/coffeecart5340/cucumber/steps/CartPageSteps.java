package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.coffeecart5340.cucumber.hooks.CucumberHook;
import org.coffeecart5340.ui.components.CartItemListComponent;
import org.coffeecart5340.ui.enumData.CoffeeType;
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
        BigDecimal expectedPrice = new BigDecimal(expectedTotal);
        Assert.assertEquals(new MenuPage(cucumberHook.getDriver()).getTotalButton().getTotalPrice(), expectedPrice,
                "Total price did not update correctly after adding items.");
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
    @Then("I verify that the {string} coffee cup is added to the cart with quantity {int}")
    public void i_verify_that_the_coffee_cup_is_added_to_the_cart_with_quantity(String string, Integer int1) {
        Assert.assertEquals((int) int1,
                new CartPage(cucumberHook.getDriver()).getCartItemList().getItemByName(string).getQuantity(), "Item quantity does not match expected value");
    }

    @Then("I verify that the total price is updated to {double}")
    public void i_verify_that_the_total_price_is_updated_to(Double double1) {
        Assert.assertEquals(new CartPage(cucumberHook.getDriver()).getCartItemList().getCalculatedTotalPrice(), CoffeeType.ESPRESSO.getPrice(),
                "Total price is incorrect");
    }

    @Then("I verify that item is present in the list of items in the cart")
    public void i_verify_that_item_is_present_in_the_list_of_items_in_the_cart() {
        Assert.assertTrue(
                new CartPage(cucumberHook.getDriver()).getCartItemList().getAllItemNames().contains("Espresso"),
                "Espresso is missing from the cart items list"
        );
    }

    @Then("I verify that added {int} types of coffee including discounted Mocha are present in the cart")
    public void i_verify_that_added_types_of_coffee_including_discounted_mocha_are_present_in_the_cart(Integer int1) {
        var cartItems = new CartPage(cucumberHook.getDriver()).getCartItemList().getAllItems();
        Assert.assertEquals(cartItems.size(),
                int1,
                "There should be exactly 2 distinct item types in the main cart list");

        var discountedMochaCartItem = new CartPage(cucumberHook.getDriver()).getCartItemList().getItemByName("(Discounted) Mocha");
        var espressoCartItem = new CartPage(cucumberHook.getDriver()).getCartItemList().getItemByName("Espresso");

        Assert.assertNotNull(discountedMochaCartItem, "Discounted Mocha should be present in the cart list");
        Assert.assertNotNull(espressoCartItem, "Espresso should be present in the cart list");
    }

    @Then("I verify that the total checkout is counted correctly with {double} discount for the Mocha")
    public void i_verify_that_the_total_checkout_is_counted_correctly_with_discount_for_the_mocha(Double discount) {
        double expectedTotal = CoffeeType.ESPRESSO.getPrice() * 3 + CoffeeType.MOCHA.getPrice() * discount;
        Assert.assertEquals(
                new CartPage(cucumberHook.getDriver()).getCartItemList().getCalculatedTotalPrice(),
                expectedTotal,
                "Total price calculation is incorrect"
        );
    }

    @Then("I verify that the adding feature is disabled for the {string} coffee cup on the Cart page")
    public void i_verify_that_the_adding_feature_is_disabled_for_the_coffee_cup_on_the_cart_page(String string) {
        Assert.assertFalse(
                new CartPage(cucumberHook.getDriver()).getCartItemList().getItemByName(string).isPlusButtonAvailable(),
                "Plus button should be disabled for " + string + " in the cart"
        );
    }





    @Then("the cart item {string} displays unit description {string}")
    public void cartItemDisplaysUnitDescription(String itemName, String expectedUnitDesc) {
        Assert.assertEquals(
                new CartPage(cucumberHook.getDriver())
                        .getCartItemList().getItemByName(itemName).getUnitDescText(),
                expectedUnitDesc,
                "Unit description mismatch for: " + itemName
        );
    }

    @Then("the cart item {string} displays subtotal {string}")
    public void cartItemDisplaysSubtotal(String itemName, String expectedSubtotal) {
        float expected = Float.parseFloat(expectedSubtotal.replace("$", "").trim());
        Assert.assertEquals(
                new CartPage(cucumberHook.getDriver())
                        .getCartItemList().getItemByName(itemName).getTotalPrice(),
                expected,
                "Subtotal mismatch for: " + itemName
        );
    }

    @Then("the cart total displays {string}")
    public void cartTotalDisplays(String expectedTotalText) {
        Assert.assertEquals(
                new CartPage(cucumberHook.getDriver())
                        .getTotalButton().getCheckoutButton().getText(),
                expectedTotalText,
                "Cart total button text mismatch"
        );
    }

    @When("I click the plus button {int} times for {string}")
    public void clickPlusButtonTimes(int times, String itemName) {
        new CartPage(cucumberHook.getDriver()).clickPlusButtonMultiply(times, itemName);
    }

    @When("I click the minus button {int} times for {string}")
    public void clickMinusButtonTimes(int times, String itemName) {
        new CartPage(cucumberHook.getDriver()).clickMinusButtonMultiply(times, itemName);
    }

    @Then("the cart item {string} has increment button visible")
    public void cartItemHasIncrementButton(String itemName) {
        Assert.assertTrue(
                new CartPage(cucumberHook.getDriver())
                        .getCartItemList().getItemByName(itemName).getPlusButton().isDisplayed(),
                "Increment (+) button not visible for: " + itemName
        );
    }

    @Then("the cart item {string} has decrement button visible")
    public void cartItemHasDecrementButton(String itemName) {
        Assert.assertTrue(
                new CartPage(cucumberHook.getDriver())
                        .getCartItemList().getItemByName(itemName).getMinusButton().isDisplayed(),
                "Decrement (-) button not visible for: " + itemName
        );
    }

    @Then("the cart item {string} has delete button visible")
    public void cartItemHasDeleteButton(String itemName) {
        Assert.assertTrue(
                new CartPage(cucumberHook.getDriver())
                        .getCartItemList().getItemByName(itemName).getDeleteButton().isDisplayed(),
                "Delete (x) button not visible for: " + itemName
        );
    }

    @Then("the Total button is visible")
    public void totalButtonIsVisible() {
        Assert.assertTrue(
                new CartPage(cucumberHook.getDriver())
                        .getTotalButton().getCheckoutButton().isDisplayed(),
                "Total button should be visible"
        );
    }

    @Then("the Total button is enabled")
    public void totalButtonIsEnabled() {
        Assert.assertTrue(
                new CartPage(cucumberHook.getDriver())
                        .getTotalButton().getCheckoutButton().isEnabled(),
                "Total button should be enabled"
        );
    }

    @When("the user clicks the checkout button")
    public void userClicksCheckoutButton() {
        new CartPage(cucumberHook.getDriver()).getTotalButton().clickCheckoutButton();
    }

    @Then("the payment modal is visible")
    public void paymentModalIsVisible() {
        Assert.assertTrue(
                new CartPage(cucumberHook.getDriver()).isPaymentModalDisplayed(),
                "Payment modal should be visible"
        );
    }

    @Then("the payment modal is not visible")
    public void paymentModalIsNotVisible() {
        Assert.assertFalse(
                new CartPage(cucumberHook.getDriver()).isPaymentModalDisplayed(),
                "Payment modal should not be visible"
        );
    }

    @Then("the payment modal title is {string}")
    public void paymentModalTitleIs(String expectedTitle) {
        Assert.assertEquals(
                new CartPage(cucumberHook.getDriver()).getPaymentModalTitle(),
                expectedTitle,
                "Payment modal title mismatch"
        );
    }

    @Then("the payment modal close button is visible")
    public void paymentModalCloseButtonIsVisible() {
        Assert.assertTrue(
                new CartPage(cucumberHook.getDriver()).isPaymentModalCloseButtonDisplayed(),
                "Payment modal close button should be visible"
        );
    }

    @When("the user closes the payment modal")
    public void userClosesPaymentModal() {
        new CartPage(cucumberHook.getDriver()).clickPaymentModalCloseButton();
    }

}
