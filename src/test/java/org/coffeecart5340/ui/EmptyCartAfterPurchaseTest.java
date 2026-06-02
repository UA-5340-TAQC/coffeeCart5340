package org.coffeecart5340.ui;

import org.coffeecart5340.ui.modals.PaymentModal;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

public class EmptyCartAfterPurchaseTest extends BaseUiTestRunner {
    private MenuPage menuPage;
    private PaymentModal paymentModal;
    private CartPage cartPage;

    @Test(priority = 1)
    public void verifyThatAfterCompletingASuccessfulPurchaseInThePaymentDetailsModalTheCartBecomesEmpty() {
        menuPage = new MenuPage(driver);

        String testName = "test";
        String testEmail = "test@gmail.com";

        menuPage.clickCoffeeCup("Espresso");
        menuPage.clickCoffeeCup("Espresso Macchiato");

        menuPage.getTotalButtonMenuComponent().clickTotalButton();

        paymentModal = new PaymentModal(driver);

        paymentModal.fillPaymentDetailsAndSubmit(testName,testEmail,true);

        String expectedSuccessMessage = "Thanks for your purchase. Please check your email for payment.";

        softAssert.assertEquals(
                menuPage.getSnackbarText(),
                expectedSuccessMessage,
                "Success message text is incorrect or missing!"
        );

        softAssert.assertEquals(menuPage.getHeader().getCartCount(), 0);

        menuPage.goToCartPage();

        cartPage = new CartPage(driver);

        softAssert.assertFalse(cartPage.cartListIsDisplayed(), "Cart list should be empty");

        softAssert.assertEquals(cartPage.getNoItemText(), "No coffee, go add some.");

        softAssert.assertAll();
    }
}
