package adapters;

import models.ResponseBetOn;
import org.apache.commons.lang3.StringUtils;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class OutrightAdapter extends MainAdapter {
    String url = "sport-events/v1/outrights/";

    public <T> T getOutright(String outrightId, String langIso, String path, Class<T> type) {
        requestSpec = given()
                .pathParam("outrightId", outrightId)
                .queryParam("langIso", langIso);
        response = get(url + path + "/{outrightId}", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), type);
    }

    public ResponseBetOn validateParams(String path, String id, String langIso, int expectedStatusCode, String responseMessage) {
        if (langIso != null) {
            requestSpec = given()
                    .pathParam("id", id)
                    .queryParam("langIso", langIso);
        } else {
            requestSpec = given()
                    .pathParam("id", id);
        }
        response = get(url + path + "/{id}", requestSpec, expectedStatusCode);
        if (StringUtils.isNotEmpty(responseMessage)) {
            return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
        } else {
            assertEquals(response.asString().trim(), "[]", "invalid response");
            return null;
        }
    }
}
