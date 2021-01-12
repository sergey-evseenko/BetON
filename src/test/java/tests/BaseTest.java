package tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.ResponseBody;

public class BaseTest {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    Faker faker = new Faker();
    ResponseBody responseBody;
    String email = faker.internet().emailAddress();
    String password = faker.internet().password() + "Q!123";
    String repeatedEmail = email;
    String repeatedPassword = password;
    String phone = "+37529" + faker.number().digits(7);
    Boolean accept = true;
    String birthDate = "1989-07-06";
    Boolean dataTransferToBp = true;
    Boolean dataTransferToCashOn = true;
    String name = faker.name().firstName();
    String surname = faker.name().lastName();
    int nationalityId = 19;
    String title = "2";
    String partnerTrackingCode = "bwin";
    String userName = faker.lorem().characters(10);
}

