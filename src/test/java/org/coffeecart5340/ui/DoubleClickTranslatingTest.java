package org.coffeecart5340.ui;

import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;

public class DoubleClickTranslatingTest extends BaseUiTestRunner{
    private MenuPage menuPage;

    @Test(priority = 1)
    public void verifyThatDoubleClickingOnACoffeeTitleTranslatesItToChinese() {
        menuPage = new MenuPage(driver);

        var espressoCup = menuPage.getCupCardByName("Espresso");

        softAssert.assertEquals(espressoCup.getCupTitleText(), "Espresso");

        espressoCup.translateCupTitle();

        softAssert.assertEquals(
                espressoCup.getCupTitleText(),
                "特浓咖啡",
                "Title did not translate to Chinese!"
        );

        espressoCup.translateCupTitle();

        softAssert.assertEquals(
                espressoCup.getCupTitleText(),
                "Espresso",
                "Title did not translate back to English!"
        );

        var allCups = menuPage.getAllCupCards();

        for (var cup : allCups) {
            String originalEnglishName = cup.getCupName();

            if (!originalEnglishName.equals("Espresso")) {
                softAssert.assertEquals(
                        cup.getCupTitleText(),
                        originalEnglishName,
                        "Bug: The coffee '" + originalEnglishName + "' was accidentally translated!"
                );
            }
        }

        driver.navigate().refresh();

        softAssert.assertAll();

    }
}
