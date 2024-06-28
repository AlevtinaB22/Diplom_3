package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final WebDriver driver;
    private final By entryLocator = By.xpath("//button[text()='Войти в аккаунт']");
    private final By makeBurgerTitleLocator = By.xpath("//h1[text()='Соберите бургер']");
    private final By menuBunClickLocator = By.xpath("//span[text()='Булки']");
    private final By menuSouceClickLocator = By.xpath("//span[text()='Соусы']");
    private final By menuFillingClickLocator = By.xpath("//span[text()='Начинки']");
    private String menuActivSectionLocator = "//*[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']";

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void entryButton() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(entryLocator));
        WebElement entry = driver.findElement(entryLocator);
        entry.click();
    }

    public void checkMakeBurger() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(makeBurgerTitleLocator));
    }

    public void menuBunClick() {
        WebElement bun = driver.findElement(menuBunClickLocator);
        bun.click();
    }

    public void menuSouceClick() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(menuSouceClickLocator));
        WebElement souce = driver.findElement(menuSouceClickLocator);
        souce.click();
    }

    public void menuFillingClick() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(menuFillingClickLocator));
        WebElement filling = driver.findElement(menuFillingClickLocator);
        filling.click();
    }

    public String returnMenuActiveSection() {
        new WebDriverWait(driver, 8)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(menuActivSectionLocator)));
        WebElement actionSectionText = driver.findElement(By.xpath(menuActivSectionLocator + "//span"));
        return actionSectionText.getText();
    }
}
