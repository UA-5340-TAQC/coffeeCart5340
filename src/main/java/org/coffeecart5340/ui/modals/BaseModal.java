package org.coffeecart5340.ui.modals;

import org.coffeecart5340.ui.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BaseModal extends Base {

    public BaseModal(WebDriver driver) {
        super(driver);
    }

    protected abstract WebElement getRootElement();

    public boolean isDisplayed() {
        return getRootElement().isDisplayed();
    }
}
