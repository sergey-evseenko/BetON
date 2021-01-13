package adapters;

import io.restassured.http.ContentType;
import models.ResponseBody;
import models.ValidateField;

import static io.restassured.RestAssured.given;

public class FieldValidationAdapter extends MainAdapter {

    public ResponseBody[] post(ValidateField validateField) {
        response = given()
                .contentType(ContentType.JSON)
                .body(gson.toJson(validateField))
                .when()
                .post("auth/v1/users/field/validate")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), ResponseBody[].class);
    }
}
