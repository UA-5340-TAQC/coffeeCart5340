package org.coffeecart5340.ui;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.coffeecart5340.ui.components.CartItemComponent;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.TotalButtonMenuComponent;
import org.coffeecart5340.ui.pages.CartPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Cart")
public class TC01_AddCoffeeToCartTest extends BaseUiTestRunner {

    private MenuPage menuPage;

    @BeforeMethod
    public void setUp() {
        menuPage = new MenuPage(driver);
    }


    private static final String COFFEE_NAME = "Espresso";

    @Test(priority = 1)
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAddCoffeeToCart() {

        Allure.step("Step 1: Verify cart counter shows 'cart (0)' before adding item", () ->
            softAssert.assertEquals(
                    menuPage.getHeader().getCartText(),
                    "cart (0)",
                    "Cart counter should show 'cart (0)' before adding any item"
            ));

        Allure.step("Step 2: Click on " + COFFEE_NAME + " coffee cup", () ->
            menuPage.clickCoffeeCup(COFFEE_NAME));

        Allure.step("Step 3: Verify cart counter incremented to 'cart (1)'", () ->
                softAssert.assertEquals(
                menuPage.getHeader().getCartText(),
                "cart (1)",
                "Cart counter should show 'cart (1)' after adding: " + COFFEE_NAME
        ));

        Allure.step("Step 3.1: Verify cart preview via hover", () -> {
            TotalButtonMenuComponent totalButtonMenu = menuPage.getTotalButtonMenuComponent();
            totalButtonMenu.hoverOverTotalButton();

            softAssert.assertTrue(
                    totalButtonMenu.isCartPreviewVisible(),
                    "Cart preview should be visible after hovering"
            );
            softAssert.assertEquals(
                    totalButtonMenu.getCartPreviewItemCount(), 1,
                    "Cart preview should contain exactly 1 item"
            );

            CartPreviewComponent previewItem = menuPage.getCartPreviewItemByName(COFFEE_NAME);

            softAssert.assertEquals(
                previewItem.getItemName(), COFFEE_NAME,
                "Cart preview item name should be: " + COFFEE_NAME
            );

            softAssert.assertEquals(
                    previewItem.getItemAmount(), "x 1",
                    "Cart preview item amount should be: x 1"
            );
        });

        Allure.step("Step 4: Navigate to Cart page", () -> {
                    menuPage.goToCartPage();
                    softAssert.assertTrue(
                            driver.getCurrentUrl().contains("/cart"),
                            "Should navigate to /cart, but was: " + driver.getCurrentUrl()
                    );
                });
        Allure.step("Step 5: Verify cart contents on Cart page", () -> {
            CartPage cartPage = new CartPage(driver);
            CartItemComponent cartItem = cartPage.getCartItemList().getItemByName(COFFEE_NAME);


            softAssert.assertEquals(
                    cartItem.getItemName(), COFFEE_NAME,
                    "Cart item name should be: " + COFFEE_NAME
            );
            softAssert.assertEquals(
                    cartItem.getUnitDescText(), "$10.00 x 1",
                    "Cart item unit price should be: $10.00 x 1"
            );
            softAssert.assertEquals(
                    cartItem.getOneItemPrice(), 10.0f,
                    "Cart total should be: 10.0"
            );
            softAssert.assertEquals(
                    cartItem.getQuantity(), 1,
                    "Cart item quantity should be: 1"
            );
            softAssert.assertEquals(
                    cartItem.getTotalPrice(), 10.0f,
                    "Cart item total price should be: 10.0"
            );
            softAssert.assertTrue(
                    cartItem.getPlusButton().isDisplayed(),
                    "Increment (+) button should be visible for: " + COFFEE_NAME
            );
            softAssert.assertTrue(
                    cartItem.getMinusButton().isDisplayed(),
                    "Decrement (-) button should be visible for: " + COFFEE_NAME
            );
            softAssert.assertTrue(
                    cartItem.getDeleteButton().isDisplayed(),
                    "Delete (x) button should be visible for: " + COFFEE_NAME
            );
            });

        softAssert.assertAll();
    }
}

