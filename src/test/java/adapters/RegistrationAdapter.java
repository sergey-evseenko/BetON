package adapters;

import io.restassured.http.ContentType;
import models.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class RegistrationAdapter extends MainAdapter {
    String url = "auth/v1/users/register";

    public Response post(User user, int expectedStatusCode) {
        body = gson.toJson(user);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("lang", "en")
                .body(body);
        response = post(url, requestSpec, expectedStatusCode);
        if (expectedStatusCode == 200) {
            return gson.fromJson(response.asString().trim(), Response.class);
        } else {
            Response[] responseBodyErrors = gson.fromJson(response.asString().trim(), Response[].class);
            return responseBodyErrors[0];
        }
    }

}
