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

    @DataProvider(name = "CoffeeCartData")
    public static Object[][] coffeeCartData() {
        return new Object[][]{
                {"Espresso", "x 1", "$10.00 x 1", 10.0f, 1, 10.0f}
        };
    }


    @Test(priority = 1, dataProvider = "CoffeeCartData")
    public void verifyAddCoffeeToCart(
            String coffeeName,
            String expectedPreviewAmount,
            String expectedUnitDesc,
            float expectedUnitPrice,
            int expectedQuantity,
            float expectedTotal) {

        menuPage = new MenuPage(driver);

        softAssert.assertEquals(
                menuPage.getHeader().getCartText(),
                "cart (0)",
                "Cart counter should show 'cart (0)' before adding any item"
        );

        menuPage.clickCoffeeCup(coffeeName);

        softAssert.assertEquals(
                menuPage.getHeader().getCartText(),
                "cart (1)",
                "Cart counter should show 'cart (1)' after adding: " + coffeeName
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

        CartPreviewComponent previewItem = menuPage.getCartPreviewItemByName(coffeeName);

        softAssert.assertEquals(
            previewItem.getItemName(), coffeeName,
            "Cart preview item name should be: " + coffeeName
        );
        softAssert.assertEquals(
                previewItem.getItemAmount(), expectedPreviewAmount,
                "Cart preview item amount should be: " + expectedPreviewAmount
        );

        CartPage cartPage = menuPage.goToCartPage();

        softAssert.assertTrue(
            driver.getCurrentUrl().contains("/cart"),
            "Should navigate to /cart, but was: " + driver.getCurrentUrl()
        );

        CartItemComponent cartItem = cartPage.getCartItemList().getItemByName(coffeeName);

        softAssert.assertEquals(
                cartItem.getItemName(), coffeeName,
                "Cart item name should be: " + coffeeName
        );
        softAssert.assertEquals(
                cartItem.getUnitDescText(), expectedUnitDesc,
                "Cart item unit price should be: " + expectedUnitDesc
        );
        softAssert.assertEquals(
                cartItem.getOneItemPrice(), expectedUnitPrice,
                "Cart total should be: " + expectedUnitPrice
        );
        softAssert.assertEquals(
                cartItem.getQuantity(),expectedQuantity,
                "Increment (+) button should be visible for: " + expectedQuantity
        );
        softAssert.assertEquals(
                cartItem.getTotalPrice(), expectedTotal,
                "Decrement (-) button should be visible for: " + expectedTotal
        );
        softAssert.assertTrue(
                cartItem.getPlusButton().isDisplayed(),
                "Increment (+) button should be visible for: " + coffeeName
        );
        softAssert.assertTrue(
                cartItem.getMinusButton().isDisplayed(),
                "Decrement (-) button should be visible for: " + coffeeName
        );
        softAssert.assertTrue(
                cartItem.getDeleteButton().isDisplayed(),
                "Delete (x) button should be visible for: " + coffeeName
        );

        softAssert.assertAll();
}
}

