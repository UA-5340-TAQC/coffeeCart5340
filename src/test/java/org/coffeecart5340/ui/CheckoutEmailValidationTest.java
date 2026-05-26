package org.coffeecart5340.ui;

import org.coffeecart5340.ui.modals.PaymentModal;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckoutEmailValidationTest extends BaseUiTestRunner {

    private MenuPage menuPage;
    private PaymentModal paymentModal;
    private CartPage cartPage;

    @DataProvider(name = "invalidEmailProvider")
    public static Object[][] invalidEmailProvider(){
        return new Object[][]{
                {"kkkk", "kkkk"},
                {"qqqq", "qqqq"},
                {"kkkk", "qqqq.com"},
                {"kkkk", "..33424#$#"}
        };
    }

    @BeforeMethod
    public void setUp(){
        menuPage = new MenuPage(driver);
        cartPage = menuPage.clickCoffeeCup("Espresso")
                .goToCartPage();
    }

    @Test(priority = 2, dataProvider = "invalidEmailProvider")
    public void VerifyValidationMessageForInvalidEmailDuringCheckout(String name, String email){

        cartPage.getTotalButton()
                .clickCheckoutButton()
                .enterName(name)
                .enterEmail(email)
                .clickPromotionCheckbox()
                .clickSubmitButton();

        paymentModal = cartPage.getPaymentModal();

        softAssert.assertTrue(paymentModal.isPromotionCheckboxChecked(),
                "The promotional messages checkbox is not checked");
        softAssert.assertEquals(paymentModal.getEmailValidationMessage(),
                "Please include an '@' in the email address. '" + email + "' is missing an '@'.", "The email validation message is not correct");
        softAssert.assertAll();
    }
}
