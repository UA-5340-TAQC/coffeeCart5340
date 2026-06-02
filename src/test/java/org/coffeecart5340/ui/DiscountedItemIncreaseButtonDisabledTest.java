package org.coffeecart5340.ui;

import org.coffeecart5340.ui.components.CartItemComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

public class DiscountedItemIncreaseButtonDisabledTest extends BaseUiTestRunner {

    @Test
    public void verifyIncreaseQuantityButtonIsDisabledForDiscountedItems() {
        String productName = "Espresso";
        String discountedProductName = "(Discounted) Mocha";

        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickCupMultiply(productName, 3);

        softAssert.assertTrue(
                menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Discount offer should be displayed after adding 3 Espresso cups"
        );

        menuPage.getDiscountModal().clickYesButton();

        softAssert.assertTrue(
                menuPage.getHeader().getCartText().contains("4"),
                "Cart quantity should be updated to 4 after accepting the discount offer"
        );

        CartPage cartPage = menuPage.goToCartPage();

        softAssert.assertTrue(
                cartPage.cartListIsDisplayed(),
                "Cart list should be displayed on the Cart page"
        );

        CartItemComponent discountedItem = cartPage.getCartItemList()
                .getItemByName(discountedProductName);

        softAssert.assertEquals(
                discountedItem.getItemName(),
                discountedProductName,
                "Discounted item should be displayed on the Cart page"
        );

        softAssert.assertFalse(
                discountedItem.getPlusButton().isEnabled(),
                "Increase quantity button should be disabled for discounted item"
        );

        softAssert.assertAll();
    }
}
