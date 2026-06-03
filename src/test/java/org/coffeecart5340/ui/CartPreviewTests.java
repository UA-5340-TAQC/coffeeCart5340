package org.coffeecart5340.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.TotalButtonMenuComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Feature("Cart Hover Preview Dropdown")
public class CartPreviewTests extends BaseUiTestRunner {

    private static final String FIRST_COFFEE_NAME = "Espresso";
    private static final String SECOND_COFFEE_NAME = "Americano";

    @Test(priority = 1)
    public void CartPreviewCheck() {
        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickCoffeeCup("Mocha");
        var price = menuPage.getTotalButton().getTotalPrice();
        Assert.assertEquals(price, 8.0, "the price must be 8.00$ .");

        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(), "The cart preview must be visible");

        menuPage.clickCoffeeCup("Mocha");
        Assert.assertFalse(menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(), "The cart preview must not be visible");
    }

    @Test(priority = 2)
    @Description("TC-057: Verify promotional offers trigger after 3 initial cups and 2 subsequent cups, and validate cart preview updates")
    public void verifyCartPreviewUpdatesAndPromotionalOffers() {
        MenuPage menuPage = new MenuPage(driver);

        // --- Step 1: Add 3 cups and verify promo triggers (Precondition Check) ---
        menuPage.clickCupMultiply(FIRST_COFFEE_NAME, 3);

        assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Precondition failed: The first promotional pop-up did not appear after adding 3 cups.");

        // --- Step 2: Verify presence in cart via preview ---
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        List<String> previewItemNames = getPreviewItemNames(menuPage);

        softAssert.assertTrue(previewItemNames.contains(FIRST_COFFEE_NAME),
                FIRST_COFFEE_NAME + " is missing from the cart preview.");

        // --- Step 3: Accept first promo (Dynamic text extraction) ---
        String firstPromoText = menuPage.getDiscountModal().getDiscountText();
        String firstPromoCup = extractCoffeeNameFromPromo(firstPromoText);

        menuPage.getDiscountModal().clickYesButton();

        // --- Step 4: Add 2 more cups and verify second promo triggers ---
        menuPage.clickCupMultiply(SECOND_COFFEE_NAME, 2);

        assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(),
                "The second promotional pop-up did not appear after adding 2 additional cups (reaching 6 items total).");

        // --- Step 5: Verify presence of second batch in cart via preview ---
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        previewItemNames = getPreviewItemNames(menuPage);

        softAssert.assertTrue(previewItemNames.contains(SECOND_COFFEE_NAME),
                SECOND_COFFEE_NAME + " is missing from the cart preview.");

        boolean foundFirstPromo = previewItemNames.stream().anyMatch(name -> name.contains(firstPromoCup));
        softAssert.assertTrue(foundFirstPromo,
                "First promo cup (" + firstPromoCup + ") is missing from the cart preview.");

        // --- Step 6: Accept second promo ---
        String secondPromoText = menuPage.getDiscountModal().getDiscountText();
        String secondPromoCup = extractCoffeeNameFromPromo(secondPromoText);

        menuPage.getDiscountModal().clickYesButton();

        // --- Step 7: Verify second promo cup in preview ---
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        List<String> finalPreviewItemNames = getPreviewItemNames(menuPage);

        boolean foundSecondPromo = finalPreviewItemNames.stream().anyMatch(name -> name.contains(secondPromoCup));
        softAssert.assertTrue(foundSecondPromo,
                "Second promo cup (" + secondPromoCup + ") is missing from the cart preview.");

        softAssert.assertAll();
    }

    @Test(priority = 3)
    @Description("Verify that hovering over the Total button shows the cart preview with correct items")
    @Flaky
    public void testTotalButtonPreviewOnHover() {
        MenuPage menuPage = new MenuPage(driver);

        String expectedCoffee = "Espresso";
        int expectedQuantity = 2;
        double expectedTotal = 20.00;

        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewEmpty(), "Cart preview should be empty before adding any items.");

        menuPage.clickCupMultiply(expectedCoffee, expectedQuantity);
        Assert.assertEquals(menuPage.getTotalButton().getTotalPrice(), expectedTotal, "Total price did not update correctly after adding items.");

        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(), "Cart preview should be visible after hovering over Total button.");
        Assert.assertFalse(menuPage.getTotalButtonMenuComponent().isCartPreviewEmpty(), "Cart preview should not be empty after adding items.");

        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isItemInCartPreview(expectedCoffee), "Expected coffee item is not displayed in cart preview.");
        Assert.assertEquals(menuPage.getTotalButtonMenuComponent().getItemQuantityByName(expectedCoffee), expectedQuantity, "Cart preview item count does not match expected quantity.");
    }

    @Test
    public void verifyDiscountIsNoLongerAvailableAfterDecreasingCartQuantity() {
        String productName = "Espresso";

        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickCupMultiply(productName, 3);

        softAssert.assertTrue(
                menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Discount should appear after adding 3 Espresso cups"
        );

        softAssert.assertEquals(
                menuPage.getHeader().getCartText(),
                "cart (3)",
                "Cart quantity should be 3 after adding Espresso three times"
        );

        TotalButtonMenuComponent totalButtonMenu = menuPage.getTotalButtonMenuComponent();
        totalButtonMenu.hoverOverTotalButton();

        softAssert.assertTrue(
                totalButtonMenu.isCartPreviewVisible(),
                "Cart preview should be visible after hovering over Total button"
        );

        CartPreviewComponent item = totalButtonMenu.getCartPreviewItems()
                .stream()
                .filter(cartItem -> cartItem.getItemName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart preview item not found: " + productName));

        softAssert.assertEquals(
                item.getItemAmount(), 3,
                "Espresso quantity should be 3 in cart preview"
        );

        item.clickMinus();

        softAssert.assertEquals(
                menuPage.getHeader().getCartText(),
                "cart (2)",
                "Cart quantity should be decreased to 2"
        );

        softAssert.assertFalse(
                menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Discount should disappear after decreasing quantity"
        );

        softAssert.assertAll();
    }
    
    /**
     * Helper method to dynamically extract the coffee name from texts like:
     * "It's your lucky day! Get an extra cup of Mocha for $4?"
     */
    private String extractCoffeeNameFromPromo(String promoText) {
        Pattern pattern = Pattern.compile("extra (.*?) for", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(promoText);

        if (matcher.find()) {
            String extractedName = matcher.group(1).trim();
            return extractedName.replace("cup of ", "").trim();
        }

        fail("Could not extract coffee name from promotional text: " + promoText);
        return null;
    }

    /**
     * Helper method to safely get a list of string names from the Cart Preview Components
     */
    private List<String> getPreviewItemNames(MenuPage menuPage) {
        return menuPage.getTotalButtonMenuComponent().getCartPreviewItems()
                .stream()
                .map(CartPreviewComponent::getItemName)
                .toList();
    }
}