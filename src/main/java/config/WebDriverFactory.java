package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {
    public static WebDriver getWebDriver(String browserType) {
        if (browserType.equalsIgnoreCase("yandex")) {
            System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\yandexdriver.exe");
            return new ChromeDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }
    }
}
