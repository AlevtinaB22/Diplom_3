package personalarea;

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
import pages.Header;
import pages.HomePage;
import pages.LoginPage;
import pages.PersonalArea;

import static config.Constants.*;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class PersonalAreaTest {
    private final Faker faker = new Faker();
    private WebDriver webDriver;
    UserMetods userMetods = new UserMetods();
    private final String name = faker.name().firstName();
    private final String email = faker.internet().emailAddress();
    private final String password = faker.number().numberBetween(123456, 1234569999) + "";
    private String token;
    PersonalArea personalArea;
    LoginPage loginPage;
    HomePage homePage;
    Header header;

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        userMetods.create(name, email, password);
        webDriver = WebDriverFactory.getWebDriver(System.getProperty("browser", "yandex"));
        webDriver.manage().window().maximize();
        webDriver.get(URL_LOGIN);
        loginPage = new LoginPage(webDriver);
        personalArea = new PersonalArea(webDriver);
        header = new Header(webDriver);
        homePage = new HomePage(webDriver);
    }

    @Test
    public void goToPersonalArea() {
        login();
        toPersonalArea();
        checkTitleProfile();
        logout();
    }

    @Step("Login in site")
    public void login() {
        loginPage.login(email, password);
    }

    @Step("Click on button to personal area")
    public void toPersonalArea() {
        header.personalAreaButton();
    }

    @Step("Check - title \"Профиль\" on page")
    public void checkTitleProfile() {
        personalArea.titleProfileExist();
    }

    @Step("Logout from sistem")
    public void logout() {
        personalArea.logout();
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
