package org.coffeecart5340.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.coffeecart5340.ui.components.CartPreviewComponent;
import org.coffeecart5340.ui.components.TotalButtonMenuComponent;
import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class PromoTest extends BaseUiTestRunner{
    private MenuPage menuPage;

   @Test
    public void PromoCheckTest(){
       menuPage = new MenuPage(driver);
       for (int i = 0; i < 3; i++)
       {
            menuPage.clickCoffeeCup("Espresso");
       }
       boolean isDiscountVisible=menuPage.getDiscountModal().isDiscountModalVisible();
       Assert.assertTrue(isDiscountVisible,"the discount banner must be visible");
       menuPage.getDiscountModal().clickYesButton();

       List<CartPreviewComponent> items = menuPage.getCartPreviews();
       int countItems = 0;

       for (CartPreviewComponent item : items) {
           countItems += item.getItemAmount();
       }

       Assert.assertEquals(countItems, 4, "The count of items must be 4");

       menuPage.getTotalButtonMenuComponent().hoverOverTotalButton();
       for (int i = 0; i < 3; i++){
           items.get(1).clickMinus();
       }
       Assert.assertTrue(items.isEmpty(), "The items count must be 0");
   }
}
