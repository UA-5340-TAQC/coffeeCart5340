package org.coffeecart5340.ui;

import io.qameta.allure.*;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;


public class HeaderNavigationTest extends BaseUiTestRunner {

    private static final String Cart_Url = "/cart";

    @Test
    @Description("TC-14 - Verify that user can navigate between Menu and Cart pages using header links")
    public void verifyNavigation() {
        String baseUrl = driver.getCurrentUrl();
        MenuPage menuPage = new MenuPage(driver);

        softAssert.assertNotNull(
                menuPage.getHeader().getCartText(),
                "Cart header link should be visible"
        );

        CartPage cartPage = menuPage.goToCartPage();

        String cartUrl = cartPage.getCurrentUrl();
        softAssert.assertTrue(
                cartUrl.contains(Cart_Url),
                "Cart page URL should contain '/cart', but was: " + cartUrl
        );

        softAssert.assertFalse(
                cartPage.cartListIsDisplayed(),
                "Cart list should not be displayed when cart is empty"
        );
        softAssert.assertEquals(
                cartPage.getNoItemText(),
                "No coffee, go add some.",
                "Empty cart message should be displayed"
        );

        menuPage = cartPage.goToMenuPage();

        softAssert.assertNotNull(
                menuPage.getCupCardByName("Espresso"),
                "Coffee cups/cards should be displayed on the menu page"
        );

        String menuUrl = menuPage.getCurrentUrl();
        softAssert.assertEquals(
                menuUrl,
                baseUrl,
                "Menu page URL should match the base URL. Expected: " + baseUrl + ", Actual: " + menuUrl
        );

        softAssert.assertAll();
    }
}