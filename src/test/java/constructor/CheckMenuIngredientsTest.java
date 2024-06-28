package constructor;

import apiuser.UserMetods;
import config.WebDriverFactory;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.LoginPage;
import pages.PersonalArea;

import static config.Constants.URL_LOGIN;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class CheckMenuIngredientsTest {
    private final Faker faker = new Faker();
    private WebDriver webDriver;
    UserMetods userMetods = new UserMetods();
    private final String name = faker.name().firstName();
    private final String email = faker.internet().emailAddress();
    //к полю пароля добавляется + "" для того, чтобы преобразовать в тип String
    private final String password = faker.number().numberBetween(123456, 1234569999) + "";
    private String token;
    PersonalArea personalArea;
    LoginPage loginPage;
    HomePage homePage;

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        userMetods.create(name, email, password);
        webDriver = WebDriverFactory.getWebDriver(System.getProperty("browser", "yandex"));
        webDriver.manage().window().maximize();
        webDriver.get(URL_LOGIN);
        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
        personalArea = new PersonalArea(webDriver);
    }

    @Test
    public void checkMenu() {
        login();
        checkBun();
        checkSouce();
        checkFilling();
    }

    @Step("Login in site")
    public void login() {
        loginPage.login(email, password);
    }

    @Step("Check go to bun in constructor")
    public void checkBun() {
        // метод returnMenuActiveSection возвращает текст активной секции
        Assert.assertEquals("Булки", homePage.returnMenuActiveSection());
    }

    @Step("Check go to souce in constructor")
    public void checkSouce() {
        homePage.menuSouceClick();
        // метод returnMenuActiveSection возвращает текст активной секции
        Assert.assertEquals("Соусы", homePage.returnMenuActiveSection());
    }

    @Step("Check go to filling in constructor")
    public void checkFilling() {
        homePage.menuFillingClick();
        // метод returnMenuActiveSection возвращает текст активной секции
        Assert.assertEquals("Начинки", homePage.returnMenuActiveSection());
    }

    @After
    public void close() {
        ValidatableResponse usersResponce = userMetods
                .login(name, password, email)
                .statusCode(SC_OK)
                .assertThat()
                .body("success", equalTo(true));
        token = usersResponce.extract().body().path("accessToken");
        userMetods.
                delete(token).statusCode(SC_ACCEPTED)
                .body("success", equalTo(true));
        webDriver.close();
    }
}
