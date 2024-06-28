package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Header {
    private final WebDriver driver;
    private final By personalAreaButtonLocator = By.xpath("//p[text()='Личный Кабинет']");
    private final By logoButtonLocator = By.xpath("//*[@class='AppHeader_header__logo__2D0X2']//*[@xmlns]");
    private final By constructorLocator = By.xpath("//p[text()='Конструктор']");

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    public void personalAreaButton() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(personalAreaButtonLocator));
        WebElement entry = driver.findElement(personalAreaButtonLocator);
        entry.click();
    }

    public void logoClick() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(logoButtonLocator));
        WebElement logoClick = driver.findElement(logoButtonLocator);
        logoClick.click();
    }

    public void constructorButtonClick() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(constructorLocator));
        WebElement entry = driver.findElement(constructorLocator);
        entry.click();
    }
}
