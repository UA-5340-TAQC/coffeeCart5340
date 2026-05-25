package org.coffeecart5340.ui.modals;

import org.coffeecart5340.ui.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public abstract class BaseModal extends Base{

    protected abstract WebElement getRootElement();

    public BaseModal(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed(){
        return getRootElement().isDisplayed();
    }
}
