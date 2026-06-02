package org.coffeecart5340.ui;

import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

public class AddingExtraDiscountedMochaTest extends BaseUiTestRunner {
    private MenuPage menuPage;
    private CartPage cartPage;

    @Test(priority = 1)
    public void verifyImpossibilityOfAddingExtraDiscountedMochaCoffeeAfterAdding3CupsOfCoffeeToTheCart() {
        menuPage = new MenuPage(driver);

        var espressoPrice = menuPage.getCupCardByName("Espresso").getCupPrice();
        var mochaPrice = menuPage.getCupCardByName("Mocha").getCupPrice();
        var discount = 0.5;

        menuPage.clickCupMultiply("Espresso", 3);

        menuPage.getDiscountModal().clickYesButton();

        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();

        var previewItems = menuPage.getTotalButtonMenuComponent().getCartPreviewItems();

        var discountedMochaPreviewItem = previewItems.stream()
                .filter(item -> item.getItemName().contains("Mocha"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Discounted Mocha not found in cart preview"));

        softAssert.assertFalse(
                discountedMochaPreviewItem.isPlusButtonAvailable(),
                "The '+' button should not be displayed/enabled for the discounted Mocha in the cart preview"
        );

        softAssert.assertEquals(previewItems.size(), 2, "There should be exactly 2 distinct item types in the cart preview");

        var espressoPreviewItem = previewItems.stream()
                .filter(item -> item.getItemName().contains("Espresso"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Espresso not found in cart preview"));

        softAssert.assertEquals(espressoPreviewItem.getItemAmount(), 3, "Espresso quantity should be 3 in preview");
        softAssert.assertEquals(discountedMochaPreviewItem.getItemAmount(), 1, "Mocha quantity should be 1 in preview");

        menuPage.goToCartPage();

        cartPage = new CartPage(driver);

        var cartItems = cartPage.getCartItemList().getAllItems();
        softAssert.assertEquals(cartItems.size(), 2, "There should be exactly 2 distinct item types in the main cart list");

        var discountedMochaCartItem = cartPage.getCartItemList().getItemByName("(Discounted) Mocha");
        var espressoCartItem = cartPage.getCartItemList().getItemByName("Espresso");

        softAssert.assertNotNull(discountedMochaCartItem, "Discounted Mocha should be present in the cart list");
        softAssert.assertNotNull(espressoCartItem, "Espresso should be present in the cart list");

        double expectedTotal = (espressoPrice * 3) + (mochaPrice * discount);
        softAssert.assertEquals(
                cartPage.getCartItemList().getCalculatedTotalPrice(),
                expectedTotal,
                "Total price calculation is incorrect"
        );

        softAssert.assertFalse(
                discountedMochaCartItem.isPlusButtonAvailable(),
                "The '+' button should not be displayed/enabled for the discounted Mocha on the Cart Page"
        );

        cartPage.getCartItemList().clearCart();

        softAssert.assertAll();
    }
}
