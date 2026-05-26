package org.coffeecart5340.ui;

import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

public class DiscountAfterThreeCupsTest extends BaseUiTestRunner {

    private MenuPage menuPage;

    @Test(priority = 1)
    public void VerifyDiscountAppearsAfterThreeCupsOnTheCartPage() {
        menuPage = new MenuPage(driver);
        CartPage cartPage = menuPage.clickCoffeeCup("Espresso")
                .goToCartPage()
                .clickPlusButtonMultiply(2, "Espresso");

        softAssert.assertTrue(menuPage.getDiscountModal().isDiscountMenuVisible(), "The discount component is not displayed after adding 3 cups of coffee to the cart");
        softAssert.assertAll();
    }

}