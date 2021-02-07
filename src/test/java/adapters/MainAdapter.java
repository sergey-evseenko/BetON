package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.PropertyManager;

import static io.restassured.RestAssured.given;
import static io.restassured.config.DecoderConfig.decoderConfig;

public class MainAdapter {

    Gson gson;
    PropertyManager props;
    Response response;
    String body;

    public MainAdapter() {
        props = new PropertyManager();
        RestAssured.baseURI = props.get("baseUrl");
        RestAssured.config = RestAssured.config().decoderConfig(decoderConfig().defaultContentCharset("UTF-8"));
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    public Response get(String url, int responseCode) {
        return given()
                .when()
                .get(url)
                .then()
                .statusCode(responseCode)
                .extract().response();
    }

    public Response getWithToken(String url, int responseCode, String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(url)
                .then()
                .statusCode(responseCode)
                .extract().response();
    }

    public Response getWithQueryParam(String url, int responseCode, String param, String paramValue) {
        return given()
                .queryParam(param, paramValue)
                .when()
                .get(url)
                .then()
                .statusCode(responseCode)
                .extract().response();
    }

    public Response getWithTokenAndPathParam(String url, int responseCode, String token, String param, String paramValue) {
        return given()
                .header("Authorization", "Bearer " + token)
                .pathParam(param, paramValue)
                .when()
                .get(url)
                .then()
                .statusCode(responseCode)
                .extract().response();
    }

    public Response post(String url, int responseCode, String body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .statusCode(responseCode)
                .extract().response();
    }

    public Response postWithHeader(String url, int responseCode, String body, String param, String paramValue) {
        return given()
                .contentType(ContentType.JSON)
                .header(param, paramValue)
                .body(body)
                .when()
                .post(url)
                .then()
                .statusCode(responseCode)
                .extract().response();
    }

    public Response put(String url, String token, String body, int responseCode) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .put(url)
                .then()
                .statusCode(responseCode)
                .extract().response();
    }
}
