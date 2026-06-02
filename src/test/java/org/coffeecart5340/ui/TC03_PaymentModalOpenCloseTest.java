package org.coffeecart5340.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Cart")
public class TC03_PaymentModalOpenCloseTest extends BaseUiTestRunner {

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
    public void verifyThatTotalButtonOpensPaymentDetailsModal() {

        Allure.step("Step 1: Click Total button - modal should appear with title 'Payment details'", () -> {
            cartPage.getTotalButton().clickCheckoutButton();

            softAssert.assertTrue(
                    cartPage.isPaymentModalDisplayed(),
                    "Payment modal should be visible after clicking Total button"
            );
            softAssert.assertEquals(
                    cartPage.getPaymentModalTitle(), "Payment details",
                    "Modal title should be: 'Payment details'"
            );
        });

        Allure.step("Step 2: Verify close button '×' is visible", () ->
                softAssert.assertTrue(
                        cartPage.isPaymentModalCloseButtonDisplayed(),
                        "Close button '×' should be visible on the modal"
                )
        );

        Allure.step("Step 3: Click close button - modal should disappear", () -> {
            cartPage.clickPaymentModalCloseButton();

            softAssert.assertFalse(
                    cartPage.isPaymentModalDisplayed(),
                    "Payment modal should not be visible after clicking '×'"
            );
        });

        Allure.step("Step 4: Click Total button again - modal should reopen", () -> {
            cartPage.getTotalButton().clickCheckoutButton();

            softAssert.assertTrue(
                    cartPage.isPaymentModalDisplayed(),
                    "Payment modal should reopen after clicking Total button again"
            );
        });

        Allure.step("Step 5: Verify modal overlays the cart page", () -> {
            softAssert.assertTrue(
                    cartPage.isPaymentModalDisplayed(),
                    "Modal should be displayed on top of cart content"
            );
            softAssert.assertEquals(
                    cartPage.getPaymentModalTitle(), "Payment details",
                    "Modal title should still be: 'Payment details'"
            );
            softAssert.assertTrue(
                    cartPage.isPaymentModalCloseButtonDisplayed(),
                    "Close button should be visible while modal overlays the page"
            );
        });

        softAssert.assertAll();
    }
}
