package org.coffeecart5340.ui.components;

import org.coffeecart5340.ui.pages.CardPage;
import org.coffeecart5340.ui.pages.GitHubPage;
import org.coffeecart5340.ui.pages.MenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BaseComponent {

    @FindBy(xpath = ".//a[@aria-label='Menu page']")
    protected WebElement menuButton;

    @FindBy(xpath = ".//a[@aria-label='Cart page']")
    protected WebElement cartButton;

    @FindBy(xpath = ".//a[@aria-label='GitHub page']")
    protected WebElement githubButton;


    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public MenuPage clickMenuButton() {
        waitAndClickElement(menuButton);
        return new MenuPage(driver);
    }

    public CardPage clickCardButton() {
        waitAndClickElement(cartButton);
        return new CardPage(driver);
    }

    public GitHubPage clickGitHubButton() {
        waitAndClickElement(githubButton);
        return new GitHubPage(driver);
    }
}
