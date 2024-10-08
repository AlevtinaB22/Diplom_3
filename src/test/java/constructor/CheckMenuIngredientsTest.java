package constructor;

import config.WebDriverFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import static config.Constants.BASE_URL;

public class CheckMenuIngredientsTest {
    private WebDriver webDriver;
    HomePage homePage;

    @Before
    public void setUp() {
        webDriver = WebDriverFactory.getWebDriver(System.getProperty("browser", "yandex"));
        webDriver.manage().window().maximize();
        webDriver.get(BASE_URL);
        homePage = new HomePage(webDriver);
    }
    @Test
    public void checkBun() {
        homePage.menuSouceClick();
        homePage.menuBunClick();
        // метод returnMenuActiveSection возвращает текст активной секции
        Assert.assertEquals("Булки", homePage.returnMenuActiveSection());
    }

   @Test
    public void checkSouce() {
       homePage.menuSouceClick();
        // метод returnMenuActiveSection возвращает текст активной секции
        Assert.assertEquals("Соусы", homePage.returnMenuActiveSection());
    }

  @Test
    public void checkFilling() {
        homePage.menuFillingClick();
        // метод returnMenuActiveSection возвращает текст активной секции
        Assert.assertEquals("Начинки", homePage.returnMenuActiveSection());
    }
    @After
    public void close(){
        webDriver.quit();
    }
}
