package adapters;

import io.restassured.http.ContentType;
import models.ResponseBody;
import models.User;

import static io.restassured.RestAssured.given;

public class RegistrationAdapter extends MainAdapter {
    String url = "auth/v1/users/register";

    public ResponseBody post(User user, int expectedStatusCode) {
        body = gson.toJson(user);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("lang", "en")
                .body(body);
        response = post(url, requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            return gson.fromJson(response.asString().trim(), ResponseBody.class);
        } else {
            ResponseBody[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBody[].class);
            return responseBodyErrors[0];
        }
    }

}
