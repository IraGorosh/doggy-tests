package dev.baraboshkin.doggyfrictions.client;

import dev.baraboshkin.doggyfrictions.client.base.FrictionRestClient;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class SessionClient extends FrictionRestClient {
    private static final String SESSION_URI = BASE_URI + "Sessions/";

    public ValidatableResponse create(HashMap<String, String> session) {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .accept("*/*")
                .formParams(session).relaxedHTTPSValidation()
                .when()
                .post(SESSION_URI)
                .then().log().all();
    }

    public ValidatableResponse getAllSessions() {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .accept("*/*")
                .when()
                .get(SESSION_URI)
                .then().log().all();
    }

    public ValidatableResponse delete(String id) {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .accept("*/*")
                .when()
                .delete(SESSION_URI + id)
                .then().log().all();
    }

    public ValidatableResponse get(String id) {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .contentType("application/x-www-form-urlencoded")
                .accept("*/*")
                .when()
                .get(SESSION_URI + id)
                .then().log().all();
    }
}
