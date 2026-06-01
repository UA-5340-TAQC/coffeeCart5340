package org.coffeecart5340.ui;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.coffeecart5340.ui.enumData.CoffeeType;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Coffee Cart Application")
@Feature("Discount System")
@Owner("Dmytro Syadro")
@Tag("UI")
public class DiscountAfterThreeCupsTest extends BaseUiTestRunner {

    private MenuPage menuPage;

    private static final String coffee = CoffeeType.ESPRESSO.getCoffee();

    @Story("Discount appears after 3 cups")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a discount section appears automatically after adding three cups of the same coffee product to the cart.")
    @Issue("https://github.com/UA-5340-TAQC/coffeeCart5340/issues/48")
    @Tag("Regression")
    @Tag("Smoke")
    @Test(priority = 1)
    public void verifyDiscountAppearsAfterThreeCupsOnTheCartPage() {
        menuPage = new MenuPage(driver);
        CartPage cartPage = menuPage.clickCoffeeCup(coffee)
                .goToCartPage()
                .clickPlusButtonMultiply(2, coffee);

        softAssert.assertEquals(cartPage.getCartItemList().getAllItems().size(), 3,
                "The cart list does not contain 3 items after adding 2 cups of coffee to the cart");

        softAssert.assertTrue(cartPage.getCartItemList().getAllItems().stream().allMatch(
                item -> item.getItemName().equals(coffee)),
                "Not all items in the cart are the expected coffee product");

        softAssert.assertEquals(cartPage.getCartItemList().getItemByName(coffee).getQuantity(), 3,
                "The quantity of the coffee item in the cart is not 3 after adding 2 cups of coffee to the cart");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(cartPage.getDiscount().isDiscountMenuVisible(),
                "The discount component is not displayed after adding 3 cups of coffee to the cart");
        softAssert.assertAll();
    }

}