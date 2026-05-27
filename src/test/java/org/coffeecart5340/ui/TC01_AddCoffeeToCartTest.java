package org.coffeecart5340.ui;

import org.coffeecart5340.ui.components.CartItemComponent;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.TotalButtonMenuComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TC01_AddCoffeeToCartTest extends BaseUiTestRunner {

    private MenuPage menuPage;

    private static final String COFFEE_NAME = "Espresso";

    @Test(priority = 1)
    public void verifyAddCoffeeToCart() {

        menuPage = new MenuPage(driver);

        softAssert.assertEquals(
                menuPage.getHeader().getCartText(),
                "cart (0)",
                "Cart counter should show 'cart (0)' before adding any item"
        );

        menuPage.clickCoffeeCup(COFFEE_NAME);

        softAssert.assertEquals(
                menuPage.getHeader().getCartText(),
                "cart (1)",
                "Cart counter should show 'cart (1)' after adding: " + COFFEE_NAME
        );

        TotalButtonMenuComponent totalButtonMenu = menuPage.getTotalButtonMenuComponent();
        totalButtonMenu.hoverOverTotalButton();

        softAssert.assertTrue(
                totalButtonMenu.isCartPreviewVisible(),
                "Cart preview should be visible after hovering"
        );
        softAssert.assertEquals(
                totalButtonMenu.getCartPreviewItemCount(), 1,
                "Cart preview should contain exactly 1 item"
        );

        CartPreviewComponent previewItem = menuPage.getCartPreviewItemByName(COFFEE_NAME);

        softAssert.assertEquals(
            previewItem.getItemName(), COFFEE_NAME,
            "Cart preview item name should be: " + COFFEE_NAME
        );
        softAssert.assertEquals(
                previewItem.getItemAmount(), "x 1",
                "Cart preview item amount should be: x 1"
        );

        CartPage cartPage = menuPage.goToCartPage();

        softAssert.assertTrue(
            driver.getCurrentUrl().contains("/cart"),
            "Should navigate to /cart, but was: " + driver.getCurrentUrl()
        );

        CartItemComponent cartItem = cartPage.getCartItemList().getItemByName(COFFEE_NAME);

        softAssert.assertEquals(
                cartItem.getItemName(), COFFEE_NAME,
                "Cart item name should be: " + COFFEE_NAME
        );
        softAssert.assertEquals(
                cartItem.getUnitDescText(), "$10.00 x 1",
                "Cart item unit price should be: $10.00 x 1"
        );
        softAssert.assertEquals(
                cartItem.getOneItemPrice(), 10.0f,
                "Cart total should be: 10.0"
        );
        softAssert.assertEquals(
                cartItem.getQuantity(), 1,
                "Cart item quantity should be: 1"
        );
        softAssert.assertEquals(
                cartItem.getTotalPrice(), 10.0f,
                "Cart item total price should be: 10.0"
        );
        softAssert.assertTrue(
                cartItem.getPlusButton().isDisplayed(),
                "Increment (+) button should be visible for: " + COFFEE_NAME
        );
        softAssert.assertTrue(
                cartItem.getMinusButton().isDisplayed(),
                "Decrement (-) button should be visible for: " + COFFEE_NAME
        );
        softAssert.assertTrue(
                cartItem.getDeleteButton().isDisplayed(),
                "Delete (x) button should be visible for: " + COFFEE_NAME
        );

        softAssert.assertAll();
}
}

