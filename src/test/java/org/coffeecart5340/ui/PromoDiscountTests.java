package org.coffeecart5340.ui;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.enumData.CoffeeType;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.testng.Assert.*;

@Epic("Coffee Cart Application")
@Feature("Promo Discount")
public class PromoDiscountTests extends BaseUiTestRunner {

    private static final String MOCHA = "Mocha";
    private static final String ESPRESSO = CoffeeType.ESPRESSO.getCoffee();
    private static final String CAPPUCCINO = "Cappuccino";
    private static final String ESPRESSO_MACCHIATO = "Espresso Macchiato";
    private static final List<String> THREE_COFFEES = List.of(ESPRESSO, ESPRESSO_MACCHIATO, CAPPUCCINO);
    private static final double DISCOUNT_RATE = 0.5;

    private MenuPage menuPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        menuPage = new MenuPage(driver);
    }

    @Story("Promotional offer re-triggers")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    @Description("TC-056: Verify promotional offer triggers again if cart quantity falls below threshold and reaches multiple of 3 again.")
    @Test(priority = 1)
    public void verifyPromotionalOfferTriggersAgainAfterCartQuantityDrop() {
        menuPage.clickCupMultiply(MOCHA, 3);

        assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Precondition failed: Promotional pop-up did not appear after adding 3 cups.");

        menuPage.getDiscountModal().clickNoButton();

        assertFalse(menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Precondition failed: Discount modal did not close after clicking 'No'.");

        cartPage = menuPage.goToCartPage();

        int initialQuantity = cartPage.getCartItemList().getItemByName(MOCHA).getQuantity();
        assertEquals(initialQuantity, 3,
                "Precondition failed: Cart does not have 3 items after dismissing the promo.");

        cartPage.clickMinusButtonByName(MOCHA);

        int updatedQuantity = cartPage.getCartItemList().getItemByName(MOCHA).getQuantity();
        softAssert.assertEquals(updatedQuantity, 2,
                "The cart quantity did not decrease to 2 after clicking the minus button.");

        MenuPage returnedMenuPage = cartPage.goToMenuPage();
        returnedMenuPage.clickCoffeeCup(ESPRESSO);

        softAssert.assertTrue(returnedMenuPage.getDiscountModal().isDiscountMenuVisible(),
                "The promotional pop-up did not trigger again after reaching 3 items in the cart.");

        softAssert.assertAll();
    }

    @Story("Promo discount accepted")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    @Description("Verify that accepting the promo discount adds a free Mocha to the cart and total item count becomes 4.")
    @Test(priority = 2)
    public void verifyPromoItemIsAddedAfterAcceptingDiscount() {
        menuPage.clickCupMultiply(ESPRESSO, 3);

        assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(),
                "The discount banner must be visible after adding 3 cups.");

        menuPage.getDiscountModal().clickYesButton();

        int totalCount = menuPage.getCartPreviews().stream()
                .mapToInt(CartPreviewComponent::getItemAmount)
                .sum();

        assertEquals(totalCount, 4, "The count of items must be 4 after accepting promo.");

        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();

        List<CartPreviewComponent> items = menuPage.getCartPreviews();
        for (int i = 0; i < 3; i++) {
            items.get(1).clickMinus();
        }

        assertTrue(items.isEmpty(), "The items count must be 0 after removing all.");
    }

    @Story("Promo discount declined")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    @Description("Verify that declining the promo discount closes the modal and cart count remains unchanged.")
    @Test(priority = 3)
    public void verifyCartRemainsUnchangedAfterDecliningPromo() {
        menuPage.clickCoffeeCup(ESPRESSO);
        menuPage.clickCoffeeCup(CAPPUCCINO);
        menuPage.clickCoffeeCup(ESPRESSO_MACCHIATO);

        int countBefore = menuPage.getCartPreviews().stream()
                .mapToInt(CartPreviewComponent::getItemAmount)
                .sum();
        assertEquals(countBefore, 3, "The count of items must be 3 before declining promo.");

        assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(),
                "The discount banner must be visible.");

        menuPage.getDiscountModal().clickNoButton();

        assertFalse(menuPage.getDiscountModal().isDiscountMenuVisible(),
                "The discount banner must not be visible after declining.");

        int countAfter = menuPage.getCartPreviews().stream()
                .mapToInt(CartPreviewComponent::getItemAmount)
                .sum();
        assertEquals(countAfter, 3, "The count of items must remain 3 after declining promo.");
    }

    @Story("Promo drink added to cart")
    @Severity(SeverityLevel.NORMAL)
    @Tag("Regression")
    @Description("TC-31: Verify that promo drink is added to cart after clicking agree button.")
    @Test(priority = 4)
    public void verifyPromoDrinkIsAddedToCartAfterClickingAgree() {
        for (String coffeeName : THREE_COFFEES) {
            menuPage.clickCoffeeCup(coffeeName);
        }

        softAssert.assertTrue(menuPage.getHeader().getCartText().contains("3"),
                "Cart counter should display 3 after adding three coffees.");

        softAssert.assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Promotional pop-up should appear after adding the third coffee item.");

        menuPage.getDiscountModal().clickYesButton();

        softAssert.assertTrue(menuPage.getHeader().getCartText().contains("4"),
                "Cart counter should display 4 after accepting promo Mocha.");

        softAssert.assertAll();
    }

    @Story("Discounted Mocha restrictions")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("Regression")
    @Description("Verify that adding a discounted Mocha via promo cannot be increased beyond 1, and total price is calculated correctly.")
    @Issue("11")
    @Test(priority = 1)
    @Muted
    public void verifyImpossibilityOfAddingExtraDiscountedMochaAfterPromo() {
        var espressoPrice = menuPage.getCupCardByName(ESPRESSO).getCupPrice();
        var mochaPrice = menuPage.getCupCardByName(MOCHA).getCupPrice();

        menuPage.clickCupMultiply(ESPRESSO, 3);
        menuPage.getDiscountModal().clickYesButton();
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();

        var previewItems = menuPage.getTotalButtonMenuComponent().getCartPreviewItems();

        var discountedMochaPreview = previewItems.stream()
                .filter(item -> item.getItemName().contains(MOCHA))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Discounted Mocha not found in cart preview"));

        var espressoPreview = previewItems.stream()
                .filter(item -> item.getItemName().contains(ESPRESSO))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Espresso not found in cart preview"));

        softAssert.assertFalse(discountedMochaPreview.isPlusButtonAvailable(),
                "The '+' button should not be enabled for the discounted Mocha in cart preview.");
        softAssert.assertEquals(previewItems.size(), 2,
                "There should be exactly 2 distinct item types in the cart preview.");
        softAssert.assertEquals(espressoPreview.getItemAmount(), 3,
                "Espresso quantity should be 3 in preview.");
        softAssert.assertEquals(discountedMochaPreview.getItemAmount(), 1,
                "Mocha quantity should be 1 in preview.");

        cartPage = menuPage.goToCartPage();

        var cartItems = cartPage.getCartItemList().getAllItems();
        softAssert.assertEquals(cartItems.size(), 2,
                "There should be exactly 2 distinct item types in the cart list.");

        var discountedMochaCart = cartPage.getCartItemList().getItemByName("(Discounted) Mocha");
        var espressoCart = cartPage.getCartItemList().getItemByName(ESPRESSO);

        softAssert.assertNotNull(discountedMochaCart, "Discounted Mocha should be present in the cart.");
        softAssert.assertNotNull(espressoCart, "Espresso should be present in the cart.");

        double expectedTotal = (espressoPrice * 3) + (mochaPrice * DISCOUNT_RATE);
        softAssert.assertEquals(cartPage.getCartItemList().getCalculatedTotalPrice(), expectedTotal,
                "Total price calculation is incorrect.");

        softAssert.assertFalse(discountedMochaCart.isPlusButtonAvailable(),
                "The '+' button should not be enabled for the discounted Mocha on the cart page.");

        cartPage.getCartItemList().clearCart();
        softAssert.assertAll();
    }

    @Story("Discount appears after 3 cups")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a discount section appears automatically after adding three cups of the same coffee to the cart.")
    @Issue("48")
    @Tag("Regression")
    @Tag("Smoke")
    @Test(priority = 1)
    public void verifyDiscountAppearsAfterThreeCupsOnTheCartPage() {
        cartPage = menuPage.clickCoffeeCup(ESPRESSO)
                .goToCartPage()
                .clickPlusButtonMultiply(2, ESPRESSO);

        softAssert.assertEquals(cartPage.getCartItemList().getAllItems().size(), 3,
                "The cart list does not contain 3 items.");
        softAssert.assertTrue(cartPage.getCartItemList().getAllItems().stream()
                        .allMatch(item -> item.getItemName().equals(ESPRESSO)),
                "Not all items in the cart are the expected coffee product.");
        softAssert.assertEquals(cartPage.getCartItemList().getItemByName(ESPRESSO).getQuantity(), 3,
                "The quantity of the coffee item in the cart is not 3.");
        softAssert.assertTrue(cartPage.getDiscount().isDiscountMenuVisible(),
                "The discount component is not displayed after adding 3 cups.");

        softAssert.assertAll();
    }
}