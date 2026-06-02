package org.coffeecart5340.ui;

import io.qameta.allure.*;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;
import java.util.List;

public class PromoDrinkTest extends BaseUiTestRunner {

    private static final List<String> Three_coffees = List.of("Espresso", "Espresso Macchiato", "Cappuccino");

    @Test
    @Description("TC-31 - Verify that promo drink is added to cart after clicking agree button")
    public void verifyPromoDrink() {
        MenuPage menuPage = new MenuPage(driver);

        for (String coffeeName : Three_coffees) {
            menuPage.clickCoffeeCup(coffeeName);
        }

        String cartTextAfterThreeAdds = menuPage.getHeader().getCartText();
        softAssert.assertTrue(
                cartTextAfterThreeAdds.contains("3"),
                "Cart counter should display 3 after adding three coffees, but was: " + cartTextAfterThreeAdds
        );

        softAssert.assertTrue(
                menuPage.getDiscountModal().isDiscountMenuVisible(),
                "Promotional pop-up should appear after adding the third coffee item"
        );

        menuPage.getDiscountModal().clickYesButton();

        String cartTextAfterPromo = menuPage.getHeader().getCartText();
        softAssert.assertTrue(
                cartTextAfterPromo.contains("4"),
                "Cart counter should display 4 after accepting promo Mocha, but was: " + cartTextAfterPromo
        );

        softAssert.assertAll();
    }
}