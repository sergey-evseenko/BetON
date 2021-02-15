package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.DataReader;
import utils.PropertyManager;

import static io.restassured.RestAssured.given;
import static io.restassured.config.DecoderConfig.decoderConfig;

public class MainAdapter {

    Gson gson;
    PropertyManager props;
    Response response;
    String body;
    DataReader data;
    RequestSpecification requestSpec;

    public MainAdapter() {
        data = new DataReader();
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

}
