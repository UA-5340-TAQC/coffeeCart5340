package org.coffeecart5340.ui;

import io.qameta.allure.*;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;


public class HeaderNavigationTest extends BaseUiTestRunner {

    private static final String CART_URL = "/cart";

    @Test
    @Description("TC-14 - Verify that user can navigate between Menu and Cart pages using header links")
    public void verifyNavigation() {
        String baseUrl = driver.getCurrentUrl();
        MenuPage menuPage = new MenuPage(driver);

        Allure.step("Step 1: Verify that the Cart header link is visible on the Menu page", () -> {
            softAssert.assertNotNull(
                    menuPage.getHeader().getCartText(),
                    "Cart header link should be visible"
            );
        });

        Allure.step("Step 2: Click on the cart link in the header to navigate to the Cart page");
        CartPage cartPage = menuPage.goToCartPage();

        Allure.step("Step 3: Verify the Cart page URL and empty state UI", () -> {
            String cartUrl = cartPage.getCurrentUrl();
            softAssert.assertTrue(
                    cartUrl.contains(CART_URL),
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
        });

        Allure.step("Step 4: Click on the menu link in the header to return to the Menu page");
        MenuPage returnedMenuPage = cartPage.goToMenuPage();

        Allure.step("Step 5: Verify the Menu page UI, URL, and assert all", () -> {
            softAssert.assertNotNull(
                    returnedMenuPage.getCupCardByName("Espresso"),
                    "Coffee cups/cards should be displayed on the menu page"
            );

            String menuUrl = returnedMenuPage.getCurrentUrl();
            softAssert.assertEquals(
                    menuUrl,
                    baseUrl,
                    "Menu page URL should match the base URL. Expected: " + baseUrl + ", Actual: " + menuUrl
            );

            softAssert.assertAll();
        });
    }
}