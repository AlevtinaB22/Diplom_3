package registration;

import apiuser.UserMetods;
import config.WebDriverFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.RegistrationPage;

import static config.Constants.URL_REGISTRATION;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class RegistrationTest {
    private final Faker faker = new Faker();
    private WebDriver webDriver;
    RegistrationPage registrationPage;
    UserMetods userMetods = new UserMetods();
    private final String name = faker.name().firstName();
    private final String email = faker.internet().emailAddress();
    //в поля паролей добавляется  + "" чтобы преобразовать их в тип данных String
    private final String password = faker.number().numberBetween(100000, 999999) + "";
    private final String passwordWrong = faker.number().numberBetween(1, 10) + "";
    private String token;
    LoginPage loginPage;

    @Before
    public void setUp() {
        webDriver = WebDriverFactory.getWebDriver(System.getProperty("browser", "yandex"));
        webDriver.manage().window().maximize();
        webDriver.get(URL_REGISTRATION);
        registrationPage = new RegistrationPage(webDriver);
        loginPage = new LoginPage(webDriver);
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("success registration")
    @Description("success registration with all correct data")
    public void successRegistration() {
        registrationCorrect();
        findTextEntry();
        clearData();
    }

    @Step("write data in fields")
    public void registrationCorrect() {
        registrationPage.registration(name, email, password);
    }

    @Step("find text \"Вход\"")
    public void findTextEntry() {
        loginPage.textEntryExist();
    }

    @Step("clear data")
    public void clearData() {
        ValidatableResponse usersResponce = userMetods
                .login(name, password, email)
                .statusCode(SC_OK)
                .assertThat()
                .body("success", equalTo(true));
        token = usersResponce.extract().body().path("accessToken");
        userMetods.
                delete(token).statusCode(SC_ACCEPTED)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("error in registration")
    @Description("Incorrect password if password < 6 numbers")
    public void registrationWithError() {
        registrationWithWrongPassword();
        findTextWrongPassword();
    }

    @Step("write data in fields")
    public void registrationWithWrongPassword() {
        registrationPage.registration(name, email, passwordWrong);
    }

    @Step("find text \"Некорректный пароль\"")
    public void findTextWrongPassword() {
        registrationPage.wrongPassword();
    }

    @After
    public void close() {
        webDriver.close();
    }
}
