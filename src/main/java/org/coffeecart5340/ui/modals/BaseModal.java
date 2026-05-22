package org.coffeecart5340.ui.modals;

import org.coffeecart5340.ui.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public abstract class BaseModal extends Base{

    protected WebElement rootElement;

    public BaseModal(WebDriver driver) {
        super(driver);
    }
    public BaseModal(WebDriver driver, WebElement rootElement) {
        super(driver);
        this.rootElement = rootElement;
        PageFactory.initElements(new DefaultElementLocatorFactory(rootElement), this);
    }

    public BaseModal(WebElement rootElement){
        super(((RemoteWebElement)rootElement).getWrappedDriver());
        this.rootElement = rootElement;
        PageFactory.initElements(new DefaultElementLocatorFactory(rootElement), this);
    }

    protected abstract WebElement getRootElement();

    public boolean isDisplayed(){
        return getRootElement().isDisplayed();
    }
}
