package adapters;

import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import static io.restassured.RestAssured.given;

public class CookiesProvider {

    private static String betSlipId;

    private CookiesProvider() {
    }

    public static String getBetSlipId() {
        if (StringUtils.isEmpty(betSlipId)) {
            Response response = given()
                    .queryParam("langIso", "de")
                    .when()
                    .get("sport-events/v1/catalogs/allSports")
                    .then()
                    .statusCode(200)
                    .extract().response();

            betSlipId = response.getDetailedCookies().get("betSlipId").getValue();
        }
        return betSlipId;
    }
}

