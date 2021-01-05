package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseTest {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
}

