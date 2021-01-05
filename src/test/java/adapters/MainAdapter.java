package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import utils.PropertyManager;

public class MainAdapter {

    Gson gson;
    PropertyManager props;

    public MainAdapter() {
        props = new PropertyManager();
        RestAssured.baseURI = props.get("baseUrl");
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
