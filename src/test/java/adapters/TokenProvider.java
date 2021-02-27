package adapters;

import io.restassured.RestAssured;
import utils.PropertyManager;

import static io.restassured.config.DecoderConfig.decoderConfig;

public class TokenProvider {

    private static String token;

    private TokenProvider() {
    }

    /*
    public static String getToken() {
        if (StringUtils.isEmpty(token)) {
            User user = new DataReader().get("userForLogin.json", User.class);
            RestAssured.baseURI = new PropertyManager().get("baseUrl");
            RestAssured.config = RestAssured.config().decoderConfig(decoderConfig().defaultContentCharset("UTF-8"));

            RequestSpecification requestSpec = given()
                    .formParam("username", user.getUserName())
                    .formParam("password", user.getPassword())
                    .formParam("grant_type", "password")
                    .formParam("response_type", "token")
                    .formParam("client_id", "clientId")
                    .formParam("client_secret", "secret");
            Response response = given()
                    .spec(requestSpec)
                    .when()
                    .post("auth/v1/oauth/token/beton")
                    .then()
                    .statusCode(200)
                    .extract().response();

            token = response.path("access_token");
        }
        return token;
    }

     */
    public static String getToken() {
        RestAssured.baseURI = new PropertyManager().get("baseUrl");
        RestAssured.config = RestAssured.config().decoderConfig(decoderConfig().defaultContentCharset("UTF-8"));
        return null;
    }
}
