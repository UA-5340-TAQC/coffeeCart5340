package org.coffeecart5340.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.coffeecart5340.ui.components.CartItemComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@Feature("Cart")
public class TC02_CartItemQuantityTest extends BaseUiTestRunner {

    private static final String COFFEE_NAME = "Espresso";

    private CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        MenuPage menuPage = new MenuPage(driver);
        menuPage.clickCoffeeCup(COFFEE_NAME);
        cartPage = menuPage.goToCartPage();
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCartItemQuantity() {

        Allure.step("Step 1: Verify initial cart state — Espresso x1, subtotal $10.00", () -> {
            CartItemComponent cartItem = cartPage.getCartItemByName(COFFEE_NAME);

            softAssert.assertEquals(
                    cartItem.getUnitDescText(), "$10.00 x 1",
                    "Initial unit description should be: $10.00 x 1"
            );
            softAssert.assertEquals(
                    cartItem.getTotalPrice(), 10.0f,
                    "Initial item subtotal should be: $10.00"
            );
        });

        Allure.step("Step 2: Click '+' twice — quantity should become x3, subtotal $30.00", () -> {
            cartPage.clickPlusButtonMultiply(2, COFFEE_NAME);
            CartItemComponent cartItem = cartPage.getCartItemByName(COFFEE_NAME);

            softAssert.assertEquals(
                    cartItem.getUnitDescText(), "$10.00 x 3",
                    "Unit description should be: $10.00 x 3 after clicking '+' twice"
            );
            softAssert.assertEquals(
                    cartItem.getTotalPrice(), 30.0f,
                    "Item subtotal should be: $30.00 after clicking '+' twice"
            );
        });

        Allure.step("Step 3: Verify cart total at bottom updates to $30.00", () ->
                softAssert.assertEquals(
                        cartPage.getTotalButton().getTotalPrice(), new BigDecimal("30.00"),
                        "Cart total should be: $30.00"
                )
        );

        Allure.step("Step 4: Click '-' once — quantity should become x2, subtotal $20.00", () -> {
            cartPage.clickMinusButtonByName(COFFEE_NAME);
            CartItemComponent cartItem = cartPage.getCartItemByName(COFFEE_NAME);

            softAssert.assertEquals(
                    cartItem.getUnitDescText(), "$10.00 x 2",
                    "Unit description should be: $10.00 x 2 after clicking '-' once"
            );
            softAssert.assertEquals(
                    cartItem.getTotalPrice(), 20.0f,
                    "Item subtotal should be: $20.00 after clicking '-' once"
            );
        });

        Allure.step("Step 5: Verify cart total at bottom decreases to $20.00", () ->
                softAssert.assertEquals(
                        cartPage.getTotalButton().getTotalPrice(), new BigDecimal("20.00"),
                        "Cart total should be: $20.00"
                )
        );


        softAssert.assertAll();
    }
}
