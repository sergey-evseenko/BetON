package tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.ResponseBody;
import models.User;

public class BaseTest {
    protected Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    protected String token;
    protected ResponseBody responseBody;
    protected User user;
    protected Faker faker = new Faker();
}

