package org.coffeecart5340.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@Feature("Cart Hover Preview")
public class TotalButtonCartPreviewTest extends BaseUiTestRunner {

    @Test
    @Description("Verify that hovering over the Total button shows the cart preview with correct items")
    public void testTotalButtonPreviewOnHover() {
        MenuPage menuPage = new MenuPage(driver);

        String expectedCoffee = "Espresso";
        int expectedQuantity = 2;
        BigDecimal expectedTotal = new BigDecimal("20.00");

        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewEmpty(), "Cart preview should be empty before adding any items.");

        menuPage.clickCupMultiply(expectedCoffee, expectedQuantity);
        Assert.assertEquals(menuPage.getTotalButton().getTotalPrice(), expectedTotal, "Total price did not update correctly after adding items.");

        menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isCartPreviewVisible(), "Cart preview should be visible after hovering over Total button.");
        Assert.assertFalse(menuPage.getTotalButtonMenuComponent().isCartPreviewEmpty(), "Cart preview should not be empty after adding items.");

        Assert.assertTrue(menuPage.getTotalButtonMenuComponent().isItemInCartPreview(expectedCoffee), "Expected coffee item is not displayed in cart preview.");
        Assert.assertEquals(menuPage.getTotalButtonMenuComponent().getItemQuantityByName(expectedCoffee), expectedQuantity, "Cart preview item count does not match expected quantity.");
    }
}
