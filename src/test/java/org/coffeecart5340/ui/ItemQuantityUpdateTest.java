package org.coffeecart5340.ui;

import io.qameta.allure.*;
import org.coffeecart5340.ui.components.CartItemComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class ItemQuantityUpdateTest extends BaseUiTestRunner {

    private static final String Coffee_name = "Espresso";

    @Test
    @Description("TC-12 - Verify that adding the same coffee multiple times updates quantity in Cart")
    public void verifyUpdatesQuantityInCart() {
        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickCoffeeCup(Coffee_name);

        String cartTextAfterFirstAdd = menuPage.getHeader().getCartText();
        softAssert.assertTrue(
                cartTextAfterFirstAdd.contains("1"),
                "Cart counter should display 1 after adding first Espresso, but was: " + cartTextAfterFirstAdd
        );

        menuPage.clickCoffeeCup(Coffee_name);

        String cartTextAfterSecondAdd = menuPage.getHeader().getCartText();
        softAssert.assertTrue(
                cartTextAfterSecondAdd.contains("2"),
                "Cart counter should display 2 after adding second Espresso, but was: " + cartTextAfterSecondAdd
        );

        CartPage cartPage = menuPage.goToCartPage();

        softAssert.assertTrue(
                cartPage.cartListIsDisplayed(),
                "Cart list should be displayed after adding items"
        );
        softAssert.assertTrue(
                cartPage.getCartItemList().getAllItemNames().contains(Coffee_name),
                Coffee_name + " should be present in the cart"
        );

        CartItemComponent espressoItem = cartPage.getCartItemList().getItemByName(Coffee_name);
        softAssert.assertEquals(
                espressoItem.getQuantity(),
                2,
                "Espresso quantity should be 2"
        );

        float itemPrice = espressoItem.getOneItemPrice();
        float expectedTotal = itemPrice * 2;
        float actualTotalInCart = espressoItem.getTotalPrice();

        softAssert.assertEquals(
                actualTotalInCart,
                expectedTotal,
                0.01f,
                "Item total price should equal unit price × quantity. Expected: "
                        + expectedTotal + ", Actual: " + actualTotalInCart
        );

        BigDecimal checkoutTotal = cartPage.getTotalButton().getTotalPrice();
        BigDecimal expectedCheckoutTotal = BigDecimal.valueOf(expectedTotal)
                .setScale(2, RoundingMode.HALF_UP);

        softAssert.assertEquals(
                checkoutTotal.setScale(2, RoundingMode.HALF_UP),
                expectedCheckoutTotal,
                "Checkout total button should display correct total price"
        );

        softAssert.assertAll();

        cartPage.clickDeleteButton(Coffee_name);
    }
}