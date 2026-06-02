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
public class AddEspressoToCartTest extends BaseUiTestRunner {

    private static final String COFFEE_NAME    = "Espresso";
    private static final String EXPECTED_TOTAL = "Total: $10.00";

    private MenuPage menuPage;

    @BeforeMethod
    public void setUp() {
        menuPage = new MenuPage(driver);
    }

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAddingEspressoToCartUpdatesCartContentsAndTotal() {

        Allure.step("Step 1: Click Espresso add-to-cart button", () ->
                menuPage.clickCoffeeCup(COFFEE_NAME)
        );

        Allure.step("Step 2: Navigate to Cart page", () -> {
            CartPage cartPage = menuPage.goToCartPage();

            softAssert.assertTrue(
                    driver.getCurrentUrl().contains("/cart"),
                    "Should be redirected to /cart, but was: " + driver.getCurrentUrl()
            );

            Allure.step("Step 3: Verify cart contains Espresso x1", () -> {
                CartItemComponent cartItem = cartPage.getCartItemList().getItemByName(COFFEE_NAME);

                softAssert.assertEquals(
                        cartItem.getItemName(), COFFEE_NAME,
                        "Cart should contain item: " + COFFEE_NAME
                );
                softAssert.assertEquals(
                        cartItem.getQuantity(), 1,
                        "Espresso quantity should be 1"
                );
                softAssert.assertEquals(
                        cartItem.getUnitDescText(), "$10.00 x 1",
                        "Unit description should be: $10.00 x 1"
                );
            });

            Allure.step("Step 4: Verify total is $10.00", () ->
                    softAssert.assertEquals(
                            cartPage.getTotalButton().getTotalPrice(),
                            new BigDecimal("10.00"),
                            "Cart total should be: $10.00"
                    )
            );

            Allure.step("Step 5: Verify checkout button is visible and enabled", () -> {
                softAssert.assertTrue(
                        cartPage.getTotalButton().getCheckoutButton().isDisplayed(),
                        "Total/Checkout button should be visible"
                );
                softAssert.assertTrue(
                        cartPage.getTotalButton().getCheckoutButton().isEnabled(),
                        "Total/Checkout button should be enabled"
                );
                softAssert.assertEquals(
                        cartPage.getTotalButton().getCheckoutButton().getText(),
                        EXPECTED_TOTAL,
                        "Total button text should be: " + EXPECTED_TOTAL
                );
            });
        });

        softAssert.assertAll();
    }
}