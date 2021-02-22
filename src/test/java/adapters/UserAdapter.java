package adapters;

import io.restassured.http.ContentType;
import models.*;

import static io.restassured.RestAssured.given;
import static tests.BaseTest.token;

public class UserAdapter extends MainAdapter {
    String url = "auth/v1/users";

    public ResponseBetOn confirmEmail(String code) {
        response = get(url + "/confirm/email/" + code, 400);
        ResponseBetOn[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBetOn[].class);
        return responseBodyErrors[0];
    }

    public UserInfo getUserInfoWithValidToken() {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "/me", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), UserInfo.class);
    }

    public ResponseBetOn getUserInfoWithExpiredToken() {
        String expiredToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJxd2VydHk4Nzk1MiIsInNjb3BlIjpbInJlYWQsd3JpdGUiXSwiZXh0ZXJuYWxJZCI6OTAwLCJleHAiOjE1ODg2ODE3MTUsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI2Y2Y5MDkwNC1kOTY3LTRjMWItOGIyYi0zZDJhODY0NDIzNGQiLCJjbGllbnRfaWQiOiJjbGllbnRJZCJ9.Au631x0A3nop9VrQLziKpHO48nJX9493p4Jczwyuy2RqmoHu2DhwY0O-odyXcUjdHxZMti8ewqpf_HuhO7OpAnJa_zZz64X83e9dTzNeGwcKjINJEnl49-an6Ev1xqgvXUYtGSMDhfPOegqBA7GkmZcOW5XG45OJS89YvM4z_f254YPiKL94FCmfomB3CKEQNzHRID0kjxpVb1_iM8Q_sn6fKGh7pTHXatd_MhPa6f3nSg5zwkEPkXc7bBNBE1TNyDvgLocP_sC2TMX3rOrLg5HIhN7UmG7jdNgdv6qzS05b5VXkg10az0u3tIJHHBCnZHXqOpdetbjoP7K4HGHHFQ";
        requestSpec = given()
                .header("Authorization", "Bearer " + expiredToken);
        response = get(url + "/me", requestSpec, 401);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public ResponseBetOn getInfoWithoutToken() {
        response = get(url + "/me", 401);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public UserInfo put(User user) {
        body = gson.toJson(user);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), UserInfo.class);
    }

    public ResponseBetOn putInvalidData(User user) {
        body = gson.toJson(user);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url, requestSpec, 400);
        ResponseBetOn[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBetOn[].class);
        return responseBodyErrors[0];
    }

    public ResponseBetOn putInvalidBirthDate(User user) {
        body = gson.toJson(user);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url, requestSpec, 400);
        return gson.fromJson(response.asString().trim(), ResponseBetOn.class);
    }

    public ResponseBetOn putEmail(Email email, int responseCode) {
        body = gson.toJson(email);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url + "/update-email", requestSpec, responseCode);
        if (responseCode == 400) {
            ResponseBetOn[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBetOn[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public ResponseBetOn putPassword(Password password, int responseCode) {
        body = gson.toJson(password);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url + "/update-pwd", requestSpec, responseCode);
        if (responseCode == 400) {
            ResponseBetOn[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBetOn[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public String checkPassword(String password) {
        body = String.format("{\"password\" : \"%s\"}", password);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = post(url + "/check-pwd", requestSpec, 200);
        return response.asString();
    }

    public ResponseBetOn checkNullPassword() {
        requestSpec = given()
                .contentType(ContentType.JSON)
                .body("{}");
        response = post(url + "/check-pwd", requestSpec, 400);
        ResponseBetOn[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBetOn[].class);
        return responseBodyErrors[0];
    }

    public ResponseBetOn putSecurityOption(SecurityOption securityOption, int responseCode) {
        body = gson.toJson(securityOption);
        requestSpec = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body);
        response = put(url + "/security", requestSpec, responseCode);
        if (responseCode == 400) {
            ResponseBetOn[] responseBodyErrors = gson.fromJson(response.asString().trim(), ResponseBetOn[].class);
            return responseBodyErrors[0];
        } else {
            return null;
        }
    }

    public SecurityOption getSecurityOption() {
        requestSpec = given()
                .header("Authorization", "Bearer " + token);
        response = get(url + "/security", requestSpec, 200);
        return gson.fromJson(response.asString().trim(), SecurityOption.class);
    }
}
