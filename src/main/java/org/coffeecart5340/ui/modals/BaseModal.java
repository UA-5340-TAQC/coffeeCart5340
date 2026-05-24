package org.coffeecart5340.ui.modals;

import org.coffeecart5340.ui.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class BaseModal extends Base {

    protected abstract WebElement getRootElement();

    public BaseModal(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isDisplayed(){
        return getRootElement().isDisplayed();
    }
}
