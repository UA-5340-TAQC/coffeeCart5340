package org.coffeecart5340.ui;

import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class CartPreviewTest extends BaseUiTestRunner {
    private MenuPage menuPage;

    @Test
    public void CartPreviewCheck(){
        menuPage = new MenuPage(driver);

        menuPage.clickCoffeeCup("Mocha");
        var price = menuPage.getTotalButton().getTotalPrice();
        Assert.assertEquals(price,8.0,"the price must be 8.00$ .");
        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(), "The cart preview must be visible");
        menuPage.clickCoffeeCup("Mocha");
        Assert.assertFalse(menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(), "The cart preview must not be visible");

    }
}
