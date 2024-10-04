package entry;

import apiuser.UserMetods;
import config.WebDriverFactory;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.*;
import static config.Constants.*;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class EntryTest {
    private final Faker faker = new Faker();
    private WebDriver webDriver;
    UserMetods userMetods = new UserMetods();
    private final String name = faker.name().firstName();
    private final String email = faker.internet().emailAddress();
    //В переменную password в конце добавляется  + "" для того, чтобы преобразовать переменную в строку
    private final String password = faker.number().numberBetween(100000, 10000000) + "";
    private String token;
    PersonalArea personalArea;
    LoginPage loginPage;
    HomePage homePage;
    RegistrationPage registrationPage;
    Header header;
    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        userMetods.create(name, email, password);
        webDriver = WebDriverFactory.getWebDriver(System.getProperty("browser", "yandex"));
        webDriver.manage().window().maximize();
        registrationPage = new RegistrationPage(webDriver);
        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
        personalArea = new PersonalArea(webDriver);
        header = new Header(webDriver);
    }
    @Test
    public void loginFromHomePage() {
        webDriver.get(BASE_URL);
        clickButtonGoToAccount();
        login();
        checkText();
        goToPersonArea();
        logout();
    }

    @Step("Click to button \"Войти в аккаунт\"")
    public void clickButtonGoToAccount() {
       homePage.entryButton();
    }

    @Step("Click on button to personal area")
    public void login() {
       loginPage.login(email,password);
    }

    @Step("Check - title \"Соберите бургер\" on page")
    public void checkText() {
        homePage.checkMakeBurger();
    }
    @Step("Check - title \"Profile\" on page")
    public void goToPersonArea() {
        header.personalAreaButton();
    }
    @Step("Logout from sistem")
    public void logout() {
        personalArea.logout();
    }

    @Test
    public void loginFromPersonalArea() {
        webDriver.get(BASE_URL);
        goToPersonArea();
        login();
        checkText();
        goToPersonArea();
        logout();
    }
    @Test
    public void loginFromRegistrationPage() {
        webDriver.get(URL_REGISTRATION);
        clickButtonEntry();
        login();
        checkText();
        goToPersonArea();
        logout();
    }

    @Step("Click to button \"Личный кабинет\"")
    public void clickButtonEntry() {
    registrationPage.entry();
    }

    @Test
    public void loginFromRestorePassword() {
        webDriver.get(URL_LOGIN);
        clickButtonRestorePassword();
        clickButtonEntry();
        login();
        checkText();
        goToPersonArea();
        logout();
    }
    @Step("Click button restore password")
    public void clickButtonRestorePassword() {
        loginPage.restorePassword();
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
