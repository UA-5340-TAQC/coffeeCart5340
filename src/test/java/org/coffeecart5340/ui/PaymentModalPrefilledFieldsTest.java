package org.coffeecart5340.ui;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.coffeecart5340.ui.modals.PaymentModal;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Coffee Cart Application")
@Feature("Payment Modal Prefilled Fields")
@Tag("UI")
@Owner("Dmytro Syadro")
public class PaymentModalPrefilledFieldsTest extends BaseUiTestRunner {
    private MenuPage menuPage;

    @DataProvider(name = "PrefilledData")
    public static Object[][] prefilledData(){
        return new Object[][]{
                {"Espresso", "Test", "TestEmail"},
                {"Cappuccino", "Test", "TestEmail3"},
                {"Americano", "Test", "TestEmail4"}
        };
    }

    @Description("Verify that payment modal fields remain pre-filled after closing and reopening the modal.Also verify that all entered values are cleared after page refresh.")
    @Severity(SeverityLevel.NORMAL)
    @Issue("https://github.com/UA-5340-TAQC/coffeeCart5340/issues/10")
    @Tag("Regression")
    @Test(priority = 1, dataProvider = "PrefilledData")
    public void verifyThatAllDetailFieldsRemainPreFilledAfterClosingThePaymentModal(String coffeeName, String name, String email){
        menuPage = new MenuPage(driver);
        menuPage.clickCoffeeCup(coffeeName)
                .getTotalButton()
                .clickCheckoutButton()
                .enterName(name)
                .enterEmail(email)
                .clickPromotionCheckbox()
                .clickCloseButton();

        menuPage.getTotalButton().clickCheckoutButton();

        softAssert.assertEquals(menuPage.getPaymentModal().getNameValue(), name,
                "The name field is not pre-filled with the previously entered value");

        softAssert.assertEquals(menuPage.getPaymentModal().getEmailValue(), email,
                "The email field is not pre-filled with the previously entered value");

        softAssert.assertTrue(menuPage.getPaymentModal().isPromotionCheckboxChecked(),
                "The promotional messages checkbox is not checked as previously selected");

        softAssert.assertAll();
        softAssert =  new SoftAssert();

        menuPage.getPaymentModal().clickCloseButton();
        driver.navigate().refresh();

        menuPage.getTotalButton().clickCheckoutButton();

        softAssert.assertTrue(menuPage.getPaymentModal().getNameValue().isEmpty(),
                "The name field is not empty after refreshing the page");

        softAssert.assertTrue(menuPage.getPaymentModal().getEmailValue().isEmpty(),
                "The email field is not empty after refreshing the page");

        softAssert.assertFalse(menuPage.getPaymentModal().isPromotionCheckboxChecked(),
                "The promotional messages checkbox is not unchecked after refreshing the page");
        softAssert.assertAll();
    }
}

