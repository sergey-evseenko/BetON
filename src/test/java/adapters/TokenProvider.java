package adapters;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.apache.commons.lang3.StringUtils;
import utils.PropertyManager;

import static io.restassured.RestAssured.given;
import static io.restassured.config.DecoderConfig.decoderConfig;

public class TokenProvider {

    private static String token;

    private TokenProvider() {
    }

    public static String getToken(User user) {
        if (StringUtils.isEmpty(token)) {
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
}
