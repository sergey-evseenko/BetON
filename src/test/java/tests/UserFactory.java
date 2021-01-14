package tests;

import models.User;

import java.io.FileNotFoundException;

public class UserFactory extends BaseTest {

    public UserFactory() throws FileNotFoundException {
    }

    public User getUser(String email, String password, String phone, String repeatedEmail, String repeatedPassword, Boolean accept, String birthDate, Boolean dataTransferToBp, Boolean dataTransferToCashOn, String name, String surname, int nationalityId, int title, String userName) throws FileNotFoundException {

        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRepeatedEmail(repeatedEmail);
        user.setRepeatedPassword(repeatedPassword);

        //setting "termsAndConditionDton" object
        termsAndConditionDton.setAccept(accept);
        user.setTermsAndConditionDto(termsAndConditionDton);

        //setting "userProfileDto" object
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
