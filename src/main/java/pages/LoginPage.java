package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final By textEntry = By.xpath("//h2[text()='Вход']");
    private final By restorePasswordLocator = By.xpath("//a[text()='Восстановить пароль']");
    private final By emailLocator = By.xpath("//label[text()='Email']/../input");
    private final By passwordLocator = By.xpath("//input[@type='password']/../input");
    private final By buttonEntryLocator = By.xpath("//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void textEntryExist() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(textEntry));
    }

    public void restorePassword() {
        WebElement restorePasswordButton = driver.findElement(restorePasswordLocator);
        restorePasswordButton.click();
    }

    public void login(String email, String password) {
        WebElement emailFiels = driver.findElement(emailLocator);
        emailFiels.sendKeys(email);
        WebElement passwordFiels = driver.findElement(passwordLocator);
        passwordFiels.sendKeys(password);
        WebElement buttonRegistrationFiels = driver.findElement(buttonEntryLocator);
        buttonRegistrationFiels.click();
    }
}
