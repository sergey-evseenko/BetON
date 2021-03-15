package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import utils.DataReader;

import static io.restassured.RestAssured.given;

public class MainAdapter {

    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    User user = new DataReader().get("userForLogin.json", User.class);
    Response response;
    RequestSpecification requestSpec;
    String body;
    static String token;
    static String betSlipId;

    public MainAdapter() {
        token = TokenProvider.getToken(user);
        betSlipId = CookiesProvider.getBetSlipId();
    }

    public Response get(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return given()
                .spec(requestSpec)
                .when()
                .get(url)
                .then()
                .statusCode(expectedStatusCode)
                .extract().response();
    }

    public Response post(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return given()
                .spec(requestSpec)
                .when()
                .post(url)
                .then()
                .statusCode(expectedStatusCode)
                .extract().response();
    }

    public Response put(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return given()
                .spec(requestSpec)
                .when()
                .put(url)
                .then()
                .statusCode(expectedStatusCode)
                .extract().response();
    }

    public Response delete(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return given()
                .spec(requestSpec)
                .when()
                .delete(url)
                .then()
                .statusCode(expectedStatusCode)
                .extract().response();
    }
}
