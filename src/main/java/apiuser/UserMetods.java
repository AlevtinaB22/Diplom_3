package apiuser;

import io.restassured.response.ValidatableResponse;

import static config.Constants.*;
import static io.restassured.RestAssured.given;

public class UserMetods {
    public ValidatableResponse create(String name, String email, String password) {
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setEmail(email);
        userRequest.setPassword(password);
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(userRequest)
                .when()
                .post(API_FOR_CREATE_USER)
                .then();
    }

    public ValidatableResponse login(String name, String password, String email) {
        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        userRequest.setPassword(password);
        userRequest.setEmail(email);
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(userRequest)
                .when()
                .post(URL_LOGIN_API)
                .then();
    }

    public ValidatableResponse delete(String token) {
        String newToken = "";
        if (token.contains("Bearer ")) {
            newToken = token.replace("Bearer ", "");
        } else {
            newToken = token;
        }
        return given()
                .auth().oauth2(newToken)
                .when()
                .delete(API_FOR_DELETE_USER)
                .then();
    }
}
