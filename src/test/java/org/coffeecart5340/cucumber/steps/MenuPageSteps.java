package org.coffeecart5340.cucumber.steps;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.Getter;
import org.coffeecart5340.cucumber.hooks.CucumberHook;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.CupCardComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class MenuPageSteps {

    @Getter
    private final CucumberHook cucumberHook;

    private CupCardComponent targetCup;
    private String firstPromoCup;
    private String secondPromoCup;

    public MenuPageSteps(CucumberHook cucumberHook) {
        this.cucumberHook = cucumberHook;
    }

    @Given("I am on the menu page")
    public void i_am_on_the_menu_page() {
        cucumberHook.getDriver().get(new org.coffeecart5340.utils.TestValueProvider().getBaseUrl());
    }

    @Given("I have an empty cart")
    public void i_have_an_empty_cart() {
        Assert.assertTrue(new MenuPage(cucumberHook.getDriver()).getTotalButtonMenuComponent().isCartPreviewEmpty(), "cart preview should be empty before adding any items");
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
    public void ClickPromoButton(String choice) {
        if (choice == "Yes, of course!") {
            new MenuPage(cucumberHook.getDriver()).getDiscountModal().clickYesButton();
        } else new MenuPage(cucumberHook.getDriver()).getDiscountModal().clickNoButton();
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

    @When("I dynamically accept the first promotional offer")
    public void iDynamicallyAcceptTheFirstPromotionalOffer() {
        MenuPage menuPage = new MenuPage(cucumberHook.getDriver());
        String promoText = menuPage.getDiscountModal().getDiscountText();
        firstPromoCup = extractCoffeeNameFromPromo(promoText);
        menuPage.getDiscountModal().clickYesButton();
    }

    @When("I dynamically accept the second promotional offer")
    public void iDynamicallyAcceptTheSecondPromotionalOffer() {
        MenuPage menuPage = new MenuPage(cucumberHook.getDriver());
        String promoText = menuPage.getDiscountModal().getDiscountText();
        secondPromoCup = extractCoffeeNameFromPromo(promoText);
        menuPage.getDiscountModal().clickYesButton();
    }

    @Then("the first saved promotional cup is displayed in the cart preview")
    public void theFirstSavedPromoCupIsDisplayed() {
        verifyPromoCupInPreview(firstPromoCup, "First");
    }

    @Then("the second saved promotional cup is displayed in the cart preview")
    public void theSecondSavedPromoCupIsDisplayed() {
        verifyPromoCupInPreview(secondPromoCup, "Second");
    }

    /**
     * Reusable helper to verify a dynamic cup name using local SoftAssert
     */
    private void verifyPromoCupInPreview(String promoCupName, String order) {
        MenuPage menuPage = new MenuPage(cucumberHook.getDriver());
        List<String> previewItemNames = menuPage.getTotalButtonMenuComponent().getCartPreviewItems()
                .stream()
                .map(CartPreviewComponent::getItemName)
                .toList();

        SoftAssert softAssert = new SoftAssert();
        boolean found = previewItemNames.stream().anyMatch(name -> name.contains(promoCupName));
        softAssert.assertTrue(found, order + " promo cup (" + promoCupName + ") is missing from the cart preview.");
        softAssert.assertAll();
    }

    /**
     * Regex extractor incorporating AI improvements
     */
    private String extractCoffeeNameFromPromo(String promoText) {
        Pattern pattern = Pattern.compile("extra (.*?) for", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(promoText);

        if (matcher.find()) {
            String extractedName = matcher.group(1).trim();
            return extractedName.replace("cup of ", "").trim();
        }

        Assert.fail("Could not extract coffee name from promotional text: " + promoText);
        return null;
    }
}
