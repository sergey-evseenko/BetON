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
    String token1;
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

        //get token
        /*

        User user = data.get("userForLogin.json", User.class);

        requestSpec = given()
                .formParam("username", user.getUserName())
                .formParam("password", user.getPassword())
                .formParam("grant_type", "password")
                .formParam("response_type", "token")
                .formParam("client_id", "clientId")
                .formParam("client_secret", "secret");

        models.Response response = gson.fromJson(post("auth/v1/oauth/token/beton", requestSpec, 200).asString().trim(), models.Response.class);
        token1 = response.getAccessToken();

         */
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
