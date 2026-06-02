package org.coffeecart5340.ui;

import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class PromoDisagreeButtonTest extends BaseUiTestRunner {
    private MenuPage menuPage;

    @Test
    public void PromoDisagreeTest() {
        menuPage = new MenuPage(driver);

        menuPage.clickCoffeeCup("Espresso");
        menuPage.clickCoffeeCup("Cappuccino");
        menuPage.clickCoffeeCup("Espresso Macchiato");

        List<CartPreviewComponent> items = menuPage.getCartPreviews();
        int countItems = 0;

        for (CartPreviewComponent item : items) {
            countItems += item.getItemAmount();
        }

        Assert.assertEquals(countItems, 3, "The count of items must be 3");

        boolean isDiscountVisible=menuPage.getDiscountModal().isDiscountMenuVisible();
        Assert.assertTrue(isDiscountVisible,"the discount banner must be visible");
        menuPage.getDiscountModal().clickNoButton();

        boolean isDiscountNotVisible=menuPage.getDiscountModal().isDiscountMenuVisible();
        Assert.assertFalse(isDiscountNotVisible,"the discount banner must not be visible");

        items = menuPage.getCartPreviews();
        countItems = 0;

        for (CartPreviewComponent item : items) {
            countItems += item.getItemAmount();
        }

        Assert.assertEquals(countItems, 3, "The count of items must be 3");




    }
}
