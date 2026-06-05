package org.coffeecart5340.cucumber.steps;

import io.cucumber.java.da.Men;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.Getter;
import org.coffeecart5340.cucumber.hooks.CucumberHook;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.CupCardComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.utils.TestValueProvider;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class MenuPageSteps {

    @Getter
    private final CucumberHook cucumberHook;

    private CupCardComponent targetCup;

    @Getter
    private static TestValueProvider testValueProvider = new TestValueProvider();

    public MenuPageSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    @Given("I am on the menu page")
    public void i_am_on_the_menu_page() {
        cucumberHook.getDriver().get(testValueProvider.getBaseUrl());
    }

    @Given("I have an empty cart")
    public void i_have_an_empty_cart() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewEmpty(), "cart preview should be empty before adding any items");
    }

    @When("I add {string} to cart")
    public void iAddToCart(String coffeeName) {
        new MenuPage(cucumberHook.getDriver()).clickCoffeeCup(coffeeName);
    }

    @When("I right-click on the {string} coffee cup")
    public void i_right_click_on_the_coffee_cup(String string) {
        new MenuPage(cucumberHook.getDriver()).getCupCardByName(string).rightClickCup();
    }

    @When("I click on the coffee cups:")
    public void i_click_on_the_coffee_cups(List<String> coffeeNames) {
        MenuPage menuPage = new MenuPage(cucumberHook.getDriver());
        for (String coffeeName : coffeeNames) {
            menuPage.clickCoffeeCup(coffeeName);
        }
    }

    @Then("I verify that the lucky modal day appears")
    public void i_verify_that_the_lucky_modal_day_appears() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getDiscountModal().isDiscountMenuVisible(), "Lucky day modal should be displayed after right-clicking on a coffee cup");
    }

    @When("I click on the Yes button")
    public void i_click_on_the_yes_button() {
        new MenuPage(cucumberHook.getDriver()).getDiscountModal().clickYesButton();
    }

    @Then("I verify that the lucky modal discount disapears")
    public void i_verify_that_the_lucky_modal_discount_disapears() {
        Assert.assertFalse(new MenuPage(cucumberHook.getDriver()).getDiscountModal().isDiscountMenuVisible(), "Lucky day modal should not be displayed after clicking the Yes button");
    }

    @When("I hover over the total checkout button")
    public void i_hover_over_the_total_checkout_button() {
        new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().hoverOverTotalButton();
    }

    @Then("I verify that checkout menu appears with added items")
    public void i_verify_that_checkout_menu_appears_with_added_items() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(), "Cart preview should be visible after hovering over the total checkout button");

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

    @When("I click on the {string} coffee cup")
    public void i_click_on_the_coffee_cup(String string) {
        new MenuPage(cucumberHook.getDriver()).clickCoffeeCup(string);
    }

    @Then("I verify that the coffee cup is added to the cart with quantity {int}")
    public void i_verify_that_the_coffee_cup_is_added_to_the_cart_with_quantity(Integer int1) {
        Assert.assertEquals((
                new MenuPage(cucumberHook.getDriver()).getHeader().getCartCount()),
                int1,
                "Cart badge count did not increase after adding an item");
    }

    @When("I click on the total checkout button")
    public void i_click_on_the_total_checkout_button() {
        new MenuPage(cucumberHook.getDriver()).getTotalButton().clickCheckoutButton();
    }
    @When("I refresh the page")
    public void i_refresh_the_page() {
        cucumberHook.getDriver().navigate().refresh();
    }

    @Then("I verify that the order confirmation message is displayed")
    public void i_verify_that_the_order_confirmation_message_is_displayed() {
        String expectedSuccessMessage = "Thanks for your purchase. Please check your email for payment.";
        Assert.assertEquals(
                new MenuPage(cucumberHook.getDriver()).getSnackbarText(),
                expectedSuccessMessage,
                "Success message text is incorrect or missing!"
        );

    }

    @Then("I verify that the + button is disabled for the {string} coffee cup")
    public void i_verify_that_the_button_is_disabled_for_the_coffee_cup(String string) {
        new CartPage(cucumberHook.getDriver()).getTotalButton().clickCheckoutButton();
        Assert.assertFalse(new MenuPage(cucumberHook.getDriver())
                .getCartPreviewItemByName(string).isPlusButtonAvailable(),
                "The + button should be disabled for the " + string + " coffee cup after purchase!");
    }

    @Then("I verify that {int} cups of coffee and {int} discounted Mocha are added to the cart")
    public void i_verify_that_cups_of_coffee_are_added_to_the_cart(Integer int1, Integer int2) {
        var previewItems = new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().getCartPreviewItems();
        var discountedMochaPreviewItem = previewItems.stream()
                .filter(item -> item.getItemName().contains("Mocha"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Discounted Mocha not found in cart preview"));

        var espressoPreviewItem = previewItems.stream()
                .filter(item -> item.getItemName().contains("Espresso"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Espresso not found in cart preview"));
        Assert.assertEquals(espressoPreviewItem.getItemAmount(), int1, "Espresso quantity should be 3 in preview");
        Assert.assertEquals(discountedMochaPreviewItem.getItemAmount(), int2, "Mocha quantity should be 1 in preview");
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
                softAssert.assertEquals(cup.getCupTitleText(), originalEnglishName, "Bug: The coffee '" + originalEnglishName + "' was accidentally translated!");
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
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(), "The cart preview must be visible after hovering!");
    }

    @When("I move the mouse cursor away from the {string} button and the popup area")
    public void moveCursorAwayFromTotalButton(String buttonName) {
        new MenuPage(cucumberHook.getDriver()).clickCoffeeCup("Mocha");
    }

    @Then("I verify the quick cart preview popup disappears from the screen")
    public void verifyCartPreviewDisappears() {
        Assert.assertFalse(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(), "The cart preview must NOT be visible after moving the cursor away!");
    }


    @And("the Cart preview is empty")
    public void theCartPreviewIsEmptyBefore() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewEmpty(), "cart preview should be empty before adding any items");
    }

    @When("I add {int} {string} to the cart")
    public void AddCuptoTheCart(String coffeeName, int quantity) {
        new MenuPage(cucumberHook.getDriver()).clickCupMultiply(coffeeName, quantity);
    }

    @Then("the cart counter should display {int}")
    public void CountDrink(int expectedCount) {
        Assert.assertEquals(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().getCartPreviewItemCount(), expectedCount, "Cart preview item count does not match expected quantity.");
    }

    @Then("the promotional banner should be displayed")
    public void PromoBannerEnabledCheck() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getDiscountModal().isDiscountMenuVisible(), "the promotional banner should be displayed");
    }


    @Then("the promotional banner should not be displayed")
    public void PromoBannerNotEnabled() {
        Assert.assertFalse(new MenuPage(cucumberHook.getDriver()).getDiscountModal().isDiscountMenuVisible(), "the promotional banner should not be displayed");
    }

    @When("I click the {string} button")
    public void clickButton(String buttonName) {
        if (buttonName.equals("Yes, of course!")) {
            new MenuPage(cucumberHook.getDriver()).getDiscountModal().clickYesButton();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (buttonName.equals("Nah, I'll skip")) {
            new MenuPage(cucumberHook.getDriver()).getDiscountModal().clickNoButton();
        }
    }

    @Then("the beverage list should be empty")
    public void DrinkListEmpty() {
        Assert.assertEquals(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().getCartPreviewItemCount(), 0, "the beverage list should be empty");
    }

    @When("I remove all {int} non-promotional items from the cart")
    public void RemoveNonPromotionalItems(int count) {
        List<CartPreviewComponent> items = new MenuPage(cucumberHook.getDriver()).getCartPreviews();
        new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().hoverOverTotalButton();
        for (int i = 0; i < count; i++) {
            items.get(1).clickMinus();
        }
    }

    @Then("I hover over the Total button")
    public void hoverOverTotalButton() {
        new MenuPage(cucumberHook.getDriver()).getTotalButton().hoverOverButton();
    }

    @When("the \"Total: $8.00\" button should be visible")
    public void TotalButtonCheck() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButton().isTotalButtonEnabled(), "the \"Total: $8.00\" button should be visible");
    }

    @Then("the cart preview should be displayed")
    public void MenuComponentVisible() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(), "the cart preview should be displayed");
    }

    @Then("the quick cart preview should contain {string}")
    public void PreviewContainsDrink(String coffeeName) {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().getCartPreviewItems().contains(coffeeName), "the quick cart preview should contain");
    }

    @When("I move the cursor away from the Total button and the preview area")
    public void MoveCursorAwayFromTotalButton() {
        new MenuPage(cucumberHook.getDriver()).clickCoffeeCup("Espresso");
    }

    @Then("the cart preview should not be displayed")
    public void MenuComponentNotVisible() {
        Assert.assertFalse(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewVisible(), "the cart preview should not be displayed");

    }

    @When("I accept the promotional offer")
    public void iAcceptThePromotionalOffer() {
        new MenuPage(cucumberHook.getDriver()).getDiscountModal().clickYesButton();
    }

    @Then("the cart preview contains a discounted promo cup")
    public void theCartPreviewContainsADiscountedPromoCup() {
        boolean found = new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().getCartPreviewItems().stream().anyMatch(item -> item.getItemName().toLowerCase().contains("discounted"));
        Assert.assertTrue(found,
                "Expected a discounted promo cup in the cart preview, but none was found.");
    }

    @Then("the header cart counter should display {int}")
    public void countDrink(int expectedCount) {
        Assert.assertEquals(
                new MenuPage(cucumberHook.getDriver()).getHeader().getCartCount(), expectedCount,
                "Cart counter in header does not match expected quantity."
        );
    }



    @Then("the total button should display {string}")
    public void totalButtonShouldDisplay(String expectedPrice) {
        double expected = Double.parseDouble(expectedPrice.replace("$", ""));
        double actual = new MenuPage(cucumberHook.getDriver()).getTotalButton().getTotalPrice();
        Assert.assertEquals(actual, expected, 0.01,
                "Total button should display " + expectedPrice + " but displayed $" + actual);
    }

    @Then("the cart preview should contain {int} items")
    public void cartPreviewShouldContainItems(int expectedCount) {
        int actualCount = new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().getCartPreviewItemCount();
        Assert.assertEquals(actualCount, expectedCount,
                "Cart preview should contain " + expectedCount + " items, but found " + actualCount);
    }

    @When("I remove {string} from the cart preview")
    public void removeFromCartPreview(String coffeeName) {
        MenuPage menuPage = new MenuPage(cucumberHook.getDriver());
        menuPage.getCartPreviewItemByName(coffeeName).clickMinus();
    }

    @When("I click the total button")
    public void clickTotalButton() {
        new MenuPage(cucumberHook.getDriver()).getTotalButton().clickCheckoutButton();
    }

    @Then("the payment details modal should appear")
    public void paymentDetailsModalShouldAppear() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getPaymentModal().isModalDisplayed(),
                "Payment details modal should appear");
    }

    @When("I fill name {string} in the payment form")
    public void fillNameInPaymentForm(String name) {
        new MenuPage(cucumberHook.getDriver()).getPaymentModal().enterName(name);
    }

    @When("I fill email {string} in the payment form")
    public void fillEmailInPaymentForm(String email) {
        new MenuPage(cucumberHook.getDriver()).getPaymentModal().enterEmail(email);
    }

    @When("I check the promotional messages checkbox")
    public void checkPromotionalMessagesCheckbox() {
        new MenuPage(cucumberHook.getDriver()).getPaymentModal().clickPromotionCheckbox();
    }

    @When("I submit the payment form")
    public void submitPaymentForm() {
        new MenuPage(cucumberHook.getDriver()).getPaymentModal().clickSubmitButton();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("a success message should appear")
    public void successMessageShouldAppear() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).isSnackbarVisible(),
                "Success message should appear");
    }

    @Then("the success message should be {string}")
    public void successMessageShouldBe(String expectedMessage) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String actualMessage = new MenuPage(cucumberHook.getDriver()).getSnackbarText();
        Assert.assertEquals(actualMessage, expectedMessage,
                "Success message should be: " + expectedMessage);
    }

}
