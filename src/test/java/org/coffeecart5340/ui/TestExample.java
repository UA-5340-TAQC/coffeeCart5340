package org.coffeecart5340.ui;

import org.coffeecart5340.ui.pages.MenuPage;
import org.coffeecart5340.ui.testrunners.BaseUiTestRunner;
import org.testng.annotations.Test;


    public class TestExample extends BaseUiTestRunner {

        @Test
        public void headerTest() {
            MenuPage menuPage = new MenuPage(driver);
            menuPage.getHeader()
                    .clickCardButton()
                    .getHeader()
                    .clickGitHubButton()
                    .getHeader()
                    .clickMenuButton();

        }
    }

