package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PersonalArea {
    private final WebDriver driver;
    private final By titleProfileLocator = By.xpath("//a[text()='Профиль']");
    private final By logoutLocator = By.xpath("//button[text()='Выход']");

    public PersonalArea(WebDriver driver) {
        this.driver = driver;
    }

    public void titleProfileExist() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(titleProfileLocator));
    }

    public void logout() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(logoutLocator));
        WebElement entry = driver.findElement(logoutLocator);
        entry.click();
    }
}
