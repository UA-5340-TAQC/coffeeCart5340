package org.coffeecart5340.ui.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.coffeecart5340.ui.components.TotalButtonMenuComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Coffee Cart Application")
@Feature("Total Button Menu Component")
public class TotalButtonMenuComponentTest extends BaseUiTestRunner {

    private MenuPage menuPage;
    private TotalButtonMenuComponent totalMenuComponent;

    @BeforeMethod
    public void setUpTest() {
        menuPage = new MenuPage(driver);
        totalMenuComponent = menuPage.getTotalButtonMenuComponent();
        totalMenuComponent.hoverOverTotalButton();
    }

    @Test
    @Story("Cart preview visibility when cart is empty")
    @Description("Verify that cart preview is not visible when no items added to cart")
    public void testCartPreviewNotVisibleWhenCartEmpty() {
        boolean isPreviewPresent = driver.findElements(By.cssSelector("ul.cart-preview.show")).size() > 0;

        Assert.assertFalse(isPreviewPresent,
                "Cart preview should not be visible when cart is empty");
    }
}