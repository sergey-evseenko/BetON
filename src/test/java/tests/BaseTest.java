package tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.ResponseBody;
import models.TermsAndConditionDto;
import models.User;
import models.UserProfileDto;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class BaseTest {
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    User user = gson.fromJson(new FileReader("src/test/resources/Data/userForRegistration.json"), User.class);
    TermsAndConditionDto termsAndConditionDton = user.getTermsAndConditionDto();
    UserProfileDto userProfileDto = user.getUserProfileDto();

    Faker faker = new Faker();
    ResponseBody responseBody;
    String email = faker.internet().emailAddress();
    String password = faker.internet().password() + "Q!123";
    String repeatedEmail = email;
    String repeatedPassword = password;
    String phone = "+37529" + faker.number().digits(7);
    Boolean accept = termsAndConditionDton.getAccept();
    String birthDate = userProfileDto.getBirthDate();
    Boolean dataTransferToBp = userProfileDto.getDataTransferToBp();
    Boolean dataTransferToCashOn = userProfileDto.getDataTransferToCashOn();
    String name = faker.name().firstName();
    String surname = faker.name().lastName();
    int nationalityId = userProfileDto.getNationalityId();
    int title = userProfileDto.getTitle();
    String partnerTrackingCode = user.getPartnerTrackingCode();
    String userName = faker.lorem().characters(10);

    public BaseTest() throws FileNotFoundException {
    }
}

