package tests;


import com.github.javafaker.Faker;
import models.TermsAndConditionDto;
import models.User;
import models.UserProfileDto;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class UserFactory extends BaseTest {
    Faker faker = new Faker();
    User user;
    UserProfileDto userProfileDto;
    TermsAndConditionDto termsAndConditionDton;

    public User getUser(String email, String password, String phone, String repeatedEmail, String repeatedPassword, Boolean accept, String birthDate, Boolean dataTransferToBp, Boolean dataTransferToCashOn, String name, String surname, int nationalityId, int title, String userName) throws FileNotFoundException {
        user = gson.fromJson(new FileReader("src/test/resources/Data/userForRegistration.json"), User.class);

        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRepeatedEmail(repeatedEmail);
        user.setRepeatedPassword(repeatedPassword);

        //setting "termsAndConditionDton" object
        termsAndConditionDton = user.getTermsAndConditionDto();
        termsAndConditionDton.setAccept(accept);
        user.setTermsAndConditionDto(termsAndConditionDton);

        //setting "userProfileDto" object
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setBirthDate(birthDate);
        userProfileDto.setDataTransferToBp(dataTransferToBp);
        userProfileDto.setDataTransferToCashOn(dataTransferToCashOn);
        userProfileDto.setName(name);
        userProfileDto.setSurname(surname);
        userProfileDto.setNationalityId(nationalityId);
        userProfileDto.setPassportNumber(faker.idNumber().valid());
        userProfileDto.setTitle(title);
        user.setUserProfileDto(userProfileDto);

        user.setUserName(userName);

        return user;
    }

}
