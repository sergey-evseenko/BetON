package adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.PropertyManager;

import static io.restassured.config.DecoderConfig.decoderConfig;

public class MainAdapter {

    Gson gson;
    PropertyManager props;
    Response response;

    public MainAdapter() {
        props = new PropertyManager();
        RestAssured.baseURI = props.get("baseUrl");
        RestAssured.config = RestAssured.config().decoderConfig(decoderConfig().defaultContentCharset("UTF-8"));
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

    }
}