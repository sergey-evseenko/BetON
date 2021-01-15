package tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.ResponseBody;
import models.User;

public class BaseTest {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    String token;
    ResponseBody responseBody;
    User user;
    Faker faker = new Faker();
}

