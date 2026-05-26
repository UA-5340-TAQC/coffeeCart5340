package org.coffeecart5340.ui;

import io.qameta.allure.*;
import org.coffeecart5340.ui.components.PaymentModalComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Payment Modal validations and submission")
public class PaymentModalCheckoutTest extends BaseUiTestRunner {

    @Test(description = "Verify payment form validation and successful submission without promo")
    public void testPaymentFormValidations() {
        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickCoffeeCup("Espresso");
        menuPage.goToCartPage();
        menuPage.getTotalButton().clickCheckoutButton();

        PaymentModalComponent paymentModal = menuPage.getPaymentModal();

        paymentModal.clickSubmit();
        Assert.assertFalse(paymentModal.getNameValidationMessage().isEmpty(),
                "Expected browser validation error on empty Name field.");

        paymentModal.enterName("John Doe");
        paymentModal.clickSubmit();
        Assert.assertFalse(paymentModal.getEmailValidationMessage().isEmpty(),
                "Expected browser validation error on empty Email field.");

        paymentModal.enterName("John Doe");
        paymentModal.enterEmail("invalid-email");
        paymentModal.clickSubmit();
        Assert.assertFalse(paymentModal.getEmailValidationMessage().isEmpty(),
                "Expected browser validation error on invalid Email format.");

        paymentModal.fillPaymentDetailsAndSubmit("John Doe", "john@example.com", false);
        String snackbarText = menuPage.getSnackbarText();
        Assert.assertTrue(snackbarText.contains("Thanks for your purchase. Please check your email for payment."),
                "Expected success snackbar message to appear after valid checkout.");
    }

    @Test(description = "Verify successful checkout with promotional checkbox selected")
    public void testPaymentFormWithPromoCheckbox() {
        MenuPage menuPage = new MenuPage(driver);
        menuPage.clickCoffeeCup("Espresso");
        menuPage.goToCartPage();

        menuPage.getTotalButton().clickCheckoutButton();

        PaymentModalComponent paymentModal = menuPage.getPaymentModal();

        paymentModal.fillPaymentDetailsAndSubmit("Jane Doe", "jane@example.com", true);

        String snackbarText = menuPage.getSnackbarText();
        Assert.assertTrue(snackbarText.contains("Thanks for your purchase. Please check your email for payment."),
                "Expected success snackbar message to appear after valid checkout with promo.");
    }
}