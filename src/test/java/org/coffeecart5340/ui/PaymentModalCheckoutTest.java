package org.coffeecart5340.ui;

import io.qameta.allure.*;
import org.coffeecart5340.ui.modals.PaymentModal;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Payment Modal validations and submission")
public class PaymentModalCheckoutTest extends BaseUiTestRunner {

    private static final String SUCCESS_MESSAGE = "Thanks for your purchase. Please check your email for payment.";

    private MenuPage menuPage;
    private PaymentModal paymentModal;

    @BeforeMethod
    public void setupCartAndOpenModal() {
        menuPage = new MenuPage(driver);
        menuPage.clickCoffeeCup("Espresso");
        menuPage.goToCartPage();
        menuPage.getTotalButton().clickCheckoutButton();
        paymentModal = menuPage.getPaymentModal();
    }

    @Test(description = "Verify payment form validation and successful submission without promo")
    public void testPaymentFormValidations() {
        paymentModal.clickSubmit();
        Assert.assertFalse(paymentModal.getNameValue().isEmpty(),
                "Expected browser validation error on empty Name field.");

        paymentModal.enterName("John Doe");
        paymentModal.clickSubmit();
        Assert.assertFalse(paymentModal.getEmailValidationMessage().isEmpty(),
                "Expected browser validation error on empty Email field.");

        paymentModal.enterEmail("invalid-email");
        paymentModal.clickSubmit();
        Assert.assertFalse(paymentModal.getEmailValidationMessage().isEmpty(),
                "Expected browser validation error on invalid Email format.");

        paymentModal.fillPaymentDetailsAndSubmit("John Doe", "john@example.com", false);
        String snackbarText = menuPage.getSnackbarText();
        Assert.assertTrue(snackbarText.contains(SUCCESS_MESSAGE),
                "Expected success snackbar message to appear after valid checkout.");
    }

    @Test(description = "Verify successful checkout with promotional checkbox selected")
    public void testPaymentFormWithPromoCheckbox() {
        paymentModal.fillPaymentDetailsAndSubmit("Jane Doe", "jane@example.com", true);

        String snackbarText = menuPage.getSnackbarText();
        Assert.assertTrue(snackbarText.contains(SUCCESS_MESSAGE),
                "Expected success snackbar message to appear after valid checkout with promo.");
    }
}