package adapters;

import models.Page;
import models.ResponseBody;

import static io.restassured.RestAssured.given;

public class ContentAdapter extends MainAdapter {
    String url = "content/v1/content/document";

    public Page getDocumentByName(String name, Boolean withChild) {
        requestSpec = given()
                .queryParam("lang", "en")
                .queryParam("name", name)
                .queryParam("with_child", String.valueOf(withChild));
        response = get(url, requestSpec, 200);
        return gson.fromJson(response.asString().trim(), Page.class);
    }

    public ResponseBody validateParams(String name, String lang, boolean withChild, int expectedStatusCode) {
        if ((name != null) && (lang != null)) {
            requestSpec = given()
                    .queryParam("lang", lang)
                    .queryParam("name", name)
                    .queryParam("with_child", String.valueOf(withChild));
        }
        if (name == null) {
            requestSpec = given()
                    .queryParam("lang", lang)
                    .queryParam("with_child", String.valueOf(withChild));
        }
        if (lang == null) {
            requestSpec = given()
                    .queryParam("name", name)
                    .queryParam("with_child", String.valueOf(withChild));
        }

        response = get(url, requestSpec, expectedStatusCode);
        return gson.fromJson(response.asString().trim(), ResponseBody.class);
    }
}
