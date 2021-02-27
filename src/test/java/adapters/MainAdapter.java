package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class MainAdapter {

    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    Response response;
    RequestSpecification requestSpec;
    String body;
    static String token;

    public MainAdapter() {
        token = TokenProvider.getToken();
    }

    public Response get(String url, RequestSpecification requestSpec, int expectedStatusCode) {
        return given()
                .spec(requestSpec)
                .log().all()
                .when()
                .get(url)
                .then()
                .log().body()
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
