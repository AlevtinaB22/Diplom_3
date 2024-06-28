package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {

    private final WebDriver driver;
    private final By nameLocator = By.xpath("//label[text()='Имя']/../input");
    private final By emailLocator = By.xpath("//label[text()='Email']/../input");
    private final By passwordLocator = By.xpath("//input[@type='password']/../input");
    private final By buttonRegistrationLocator = By.xpath("//button[text()='Зарегистрироваться']");
    private final By wrongPasswordErrorLocator = By.xpath("//p[text()='Некорректный пароль']");
    private final By entryButtonLocator = By.xpath("//*[text()='Войти']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void registration(String name, String email, String password) {
        WebElement nameFiels = driver.findElement(nameLocator);
        nameFiels.sendKeys(name);
        WebElement emailFiels = driver.findElement(emailLocator);
        emailFiels.sendKeys(email);
        WebElement passwordFiels = driver.findElement(passwordLocator);
        passwordFiels.sendKeys(password);
        WebElement buttonRegistrationFiels = driver.findElement(buttonRegistrationLocator);
        buttonRegistrationFiels.click();
    }

    public void wrongPassword() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(wrongPasswordErrorLocator));
    }

    public void entry() {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(entryButtonLocator));
        WebElement entry = driver.findElement(entryButtonLocator);
        entry.click();

    }
}
