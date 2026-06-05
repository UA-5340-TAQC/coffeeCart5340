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

public class CartPageSteps {


    private final CucumberHook cucumberHook;

    public CartPageSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    private MenuPage menuPage() {
        return new MenuPage(cucumberHook.getDriver());
    }

    private CartPage cartPage() {
        return new CartPage(cucumberHook.getDriver());
    }


    @Given("I am on the cart page")
    public void i_am_on_the_cart_page() {
        menuPage().goToCartPage();
    }

    @Then("I verify that the quantity increases by {int} next to the Cart button")
    public void i_verify_that_the_quantity_increases_by_next_to_the_cart_button(int number) {
        Assert.assertEquals(menuPage().getHeader().getCartCount(),
                number,
                "Cart badge count did not increase after adding an item");
    }

    @Then("I verify that the cart is empty")
    public void i_verify_that_the_cart_is_empty() {
        CartPage cartPage = cartPage();
        Assert.assertFalse(cartPage.cartListIsDisplayed(), "Cart list should be empty");
        Assert.assertEquals(cartPage.getNoItemText(), "No coffee, go add some.");
    }

    @Given("the cart preview is empty before adding any items")
    public void theCartPreviewIsEmptyBeforeAddingItems() {
        Assert.assertTrue(menuPage().getTotalButtonMenuComponent().isCartPreviewEmpty(),
                "Cart preview should be empty before adding any items.");
    }

    @When("the user adds {int} {string} cups")
    public void theUserAddsCups(int quantity, String coffeeName) {
        menuPage().clickCupMultiply(coffeeName, quantity);
    }

    @Then("the total price is successfully updated to {string}")
    public void theTotalPriceIsSuccessfullyUpdated(String expectedTotal) {
        double expectedPrice = Double.parseDouble(expectedTotal);
        double actualPrice = menuPage().getTotalButton().getTotalPrice();
        Assert.assertEquals(actualPrice, expectedPrice, 0.01,
                "Total price did not update correctly after adding items.");
    }

    @When("the user hovers over the Total button")
    public void theUserHoversOverTheTotalButton() {
        menuPage().getTotalButtonMenuComponent().hoverOverTotalButton();
    }

    @Then("the cart preview becomes visible")
    public void theCartPreviewBecomesVisible() {
        Assert.assertTrue(menuPage().getTotalButtonMenuComponent().isCartPreviewVisible(),
                "Cart preview should be visible after hovering over Total button.");
    }

    @And("the cart preview is no longer empty")
    public void theCartPreviewIsNoLongerEmpty() {
        Assert.assertFalse(menuPage().getTotalButtonMenuComponent().isCartPreviewEmpty(),
                "Cart preview should not be empty after adding items.");
    }

    @And("the {string} coffee is displayed in the cart preview")
    public void theCoffeeIsDisplayedInTheCartPreview(String coffeeName) {
        Assert.assertTrue(menuPage().getTotalButtonMenuComponent().isItemInCartPreview(coffeeName),
                "Expected coffee item is not displayed in cart preview.");
    }

    @And("the quantity of {string} in the preview matches the expected quantity of {int}")
    public void theQuantityMatchesExpected(String coffeeName, int expectedQuantity) {
        Assert.assertEquals(menuPage().getTotalButtonMenuComponent().getItemQuantityByName(coffeeName),
                expectedQuantity,
                "Cart preview item count does not match expected quantity.");
    }

    @When("I open the cart page")
    public void OpenCartPage() {
        menuPage().goToCartPage();
    }

    @Then("the cart should contain {string}")
    public void DrinkInCartPage(String coffeeName) {
        CartItemListComponent itemList = cartPage().getCartItemList();
        var names = itemList.getAllItemNames();

        Assert.assertTrue(names.contains(coffeeName),
                "The element must be " + coffeeName);
    }

    @When("I remove {string} from the cart")
    public void RemoveItemFromCart(String coffeeName) {
        cartPage().getCartItemList().getItemByName(coffeeName).clickMinusButton();
    }

    @And("the cart should be empty")
    public void EmptyCartCheck() {
        Assert.assertEquals(cartPage().getNoItemText(),
                "No coffee, go add some.", "the cart should be empty");
    }

    @Then("I verify that the {string} coffee cup is added to the cart with quantity {int}")
    public void i_verify_that_the_coffee_cup_is_added_to_the_cart_with_quantity(String string, Integer int1) {
        Assert.assertEquals((int) int1, cartPage().getCartItemList().getItemByName(string).getQuantity(),
                "Item quantity does not match expected value");
    }

    @Then("I verify that the total price is updated to {double}")
    public void i_verify_that_the_total_price_is_updated_to(Double double1) {
        Assert.assertEquals(cartPage().getCartItemList().getCalculatedTotalPrice(), CoffeeType.ESPRESSO.getPrice(),
                "Total price is incorrect");
    }

    @Then("I verify that item is present in the list of items in the cart")
    public void i_verify_that_item_is_present_in_the_list_of_items_in_the_cart() {
        Assert.assertTrue(cartPage().getCartItemList().getAllItemNames().contains("Espresso"),
                "Espresso is missing from the cart items list");
    }

    @Then("I verify that added {int} types of coffee including discounted Mocha are present in the cart")
    public void i_verify_that_added_types_of_coffee_including_discounted_mocha_are_present_in_the_cart(Integer int1) {
        var cartItems = cartPage().getCartItemList().getAllItems();
        Assert.assertEquals(cartItems.size(), int1, "There should be exactly 2 distinct item types in the main cart list");

        var discountedMochaCartItem = cartPage().getCartItemList().getItemByName("(Discounted) Mocha");
        var espressoCartItem = cartPage().getCartItemList().getItemByName("Espresso");

        Assert.assertNotNull(discountedMochaCartItem, "Discounted Mocha should be present in the cart list");
        Assert.assertNotNull(espressoCartItem, "Espresso should be present in the cart list");
    }

    @Then("I verify that the total checkout is counted correctly with {double} discount for the Mocha")
    public void i_verify_that_the_total_checkout_is_counted_correctly_with_discount_for_the_mocha(Double discount) {
        double expectedTotal = CoffeeType.ESPRESSO.getPrice() * 3 + CoffeeType.MOCHA.getPrice() * discount;
        Assert.assertEquals(cartPage().getCartItemList().getCalculatedTotalPrice(), expectedTotal,
                "Total price calculation is incorrect");
    }

    @Then("I verify that the adding feature is disabled for the {string} coffee cup on the Cart page")
    public void i_verify_that_the_adding_feature_is_disabled_for_the_coffee_cup_on_the_cart_page(String string) {
        Assert.assertFalse(cartPage().getCartItemList().getItemByName(string).isPlusButtonAvailable(),
                "Plus button should be disabled for " + string + " in the cart");
    }


    @Then("the cart item {string} displays unit description {string}")
    public void cartItemDisplaysUnitDescription(String itemName, String expectedUnitDesc) {
        Assert.assertEquals(cartPage().getCartItemList().getItemByName(itemName).getUnitDescText(),
                expectedUnitDesc,
                "Unit description mismatch for: " + itemName);
    }

    @Then("the cart item {string} displays subtotal {string}")
    public void cartItemDisplaysSubtotal(String itemName, String expectedSubtotal) {
        float expected = Float.parseFloat(expectedSubtotal.replace("$", "").trim());
        Assert.assertEquals(cartPage().getCartItemList().getItemByName(itemName).getTotalPrice(),
                expected,
                "Subtotal mismatch for: " + itemName);
    }

    @Then("the cart total displays {string}")
    public void cartTotalDisplays(String expectedTotalText) {
        Assert.assertEquals(cartPage().getTotalButton().getCheckoutButtonText(),
                expectedTotalText,
                "Cart total button text mismatch");
    }

    @When("I click the plus button {int} times for {string}")
    public void clickPlusButtonTimes(int times, String itemName) {
        cartPage().clickPlusButtonMultiply(times, itemName);
    }

    @When("I click the minus button {int} times for {string}")
    public void clickMinusButtonTimes(int times, String itemName) {
        cartPage().clickMinusButtonMultiply(times, itemName);
    }

    @Then("the cart item {string} has increment button visible")
    public void cartItemHasIncrementButton(String itemName) {
        Assert.assertTrue(cartPage().getCartItemList().getItemByName(itemName).isPlusButtonDisplayed(),
                "Increment (+) button not visible for: " + itemName);
    }

    @Then("the cart item {string} has decrement button visible")
    public void cartItemHasDecrementButton(String itemName) {
        Assert.assertTrue(cartPage().getCartItemList().getItemByName(itemName).isMinusButtonDisplayed(),
                "Decrement (-) button not visible for: " + itemName);
    }

    @Then("the cart item {string} has delete button visible")
    public void cartItemHasDeleteButton(String itemName) {
        Assert.assertTrue(cartPage().getCartItemList().getItemByName(itemName).isDeleteButtonDisplayed(),
                "Delete (x) button not visible for: " + itemName);
    }

    @Then("the Total button is visible")
    public void totalButtonIsVisible() {
        Assert.assertTrue(cartPage().getTotalButton().isCheckoutButtonDisplayed(),
                "Total button should be visible");
    }

    @Then("the Total button is enabled")
    public void totalButtonIsEnabled() {
        Assert.assertTrue(cartPage().getTotalButton().isCheckoutButtonEnabled(),
                "Total button should be enabled");
    }

    @When("the user clicks the checkout button")
    public void userClicksCheckoutButton() {
        cartPage().getTotalButton().clickCheckoutButton();
    }

    @Then("the payment modal is visible")
    public void paymentModalIsVisible() {
        Assert.assertTrue(cartPage().isPaymentModalDisplayed(),
                "Payment modal should be visible");
    }

    @Then("the payment modal is not visible")
    public void paymentModalIsNotVisible() {
        Assert.assertFalse(cartPage().isPaymentModalDisplayed(),
                "Payment modal should not be visible");
    }

    @Then("the payment modal title is {string}")
    public void paymentModalTitleIs(String expectedTitle) {
        Assert.assertEquals(cartPage().getPaymentModalTitle(), expectedTitle,
                "Payment modal title mismatch");
    }

    @Then("the payment modal close button is visible")
    public void paymentModalCloseButtonIsVisible() {
        Assert.assertTrue(cartPage().isPaymentModalCloseButtonDisplayed(),
                "Payment modal close button should be visible");
    }

    @When("the user closes the payment modal")
    public void userClosesPaymentModal() {
        cartPage().clickPaymentModalCloseButton();
    }

    @And("the quantity of {string} in the cart should be {int}")
    public void theQuantityOfItemInCartShouldBe(String coffeeName, int expectedQuantity) {
        int actualQuantity = cartPage().getCartItemList().getItemByName(coffeeName).getQuantity();
        Assert.assertEquals(actualQuantity, expectedQuantity,
                "Quantity of '" + coffeeName + "' in cart does not match expected " + expectedQuantity);
    }

    @Then("the cart item {string} quantity should be {int}")
    public void theCartItemQuantityShouldBe(String coffeeName, int expectedQuantity) {
        theQuantityOfItemInCartShouldBe(coffeeName, expectedQuantity);
    }

    @When("I navigate back to the menu page")
    public void iNavigateBackToTheMenuPage() {
        cartPage().goToMenuPage();
    }

    @Then("the total price on the menu page should be {string}")
    public void theTotalPriceOnMenuPageShouldBe(String expectedTotal) {
        double expected = Double.parseDouble(expectedTotal);
        Assert.assertEquals(menuPage().getTotalButton().getTotalPrice(), expected,
                "Total price does not match expected $" + expectedTotal);
    }

    @When("I navigate to the menu page")
    public void iNavigateToTheMenuPage() {
        cartPage().goToMenuPage();
    }

    @Then("the quantity of {string} on the cart page should be {int}")
    public void theQuantityOnTheCartPageShouldBe(String coffeeName, int expectedQuantity) {
        int actualQuantity = cartPage().getCartItemList().getItemByName(coffeeName).getQuantity();
        Assert.assertEquals(actualQuantity, expectedQuantity, "Cart quantity mismatch for " + coffeeName);
    }
}
