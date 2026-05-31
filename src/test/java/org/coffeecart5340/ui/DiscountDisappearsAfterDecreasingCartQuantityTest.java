package org.coffeecart5340.ui;

import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.TotalButtonMenuComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

public class DiscountDisappearsAfterDecreasingCartQuantityTest extends BaseUiTestRunner {

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

        softAssert.assertTrue(
                item.hasQuantity(3),
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
}
