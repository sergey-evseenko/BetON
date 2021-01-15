package tests;

import models.Address;
import models.TermsAndConditionDto;
import models.User;
import models.UserProfileDto;

import java.io.FileNotFoundException;


public class UserFactory extends BaseTest {
    User user;
    TermsAndConditionDto termsAndConditionDto;
    UserProfileDto userProfileDto;
    Address address;
    Address[] addresses;

    String email = faker.internet().emailAddress();
    String password = faker.internet().password() + "Q!123";
    String phone = "+37529" + faker.number().digits(7);
    Boolean accept = true;
    String city = faker.address().city();
    //TODO faker.address().countryCode() - doesn't work. Need to investigate
    String countryCode = "BY";
    String homeNumber = faker.address().buildingNumber();
    String postalCode = faker.address().zipCode();
    String street = faker.address().streetAddress();
    String birthDate = faker.number().numberBetween(1989, 2000) + "-01-01";
    Boolean dataTransferToBp = true;
    Boolean dataTransferToCashOn = true;
    String name = faker.name().firstName();
    String surname = faker.name().lastName();
    int nationalityId = faker.number().numberBetween(1, 10);
    String passportNumber = faker.idNumber().valid();
    String phoneCode = "+" + faker.number().numberBetween(1, 1000);
    int title = faker.number().numberBetween(1, 10);
    String userName = faker.lorem().characters(10);
    String partnerTrackingCode = null;

    public UserFactory() throws FileNotFoundException {
    }

    public User getNewUser() {
        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRepeatedEmail(email);
        user.setRepeatedPassword(password);
        termsAndConditionDto = new TermsAndConditionDto();
        termsAndConditionDto.setAccept(accept);
        user.setTermsAndConditionDto(termsAndConditionDto);
        address = new Address();
        address.setCity(city);
        address.setCountryCode(countryCode);
        address.setHomeNumber(homeNumber);
        address.setPostalCode(postalCode);
        address.setStreet(street);
        userProfileDto = new UserProfileDto();
        addresses = new Address[1];
        addresses[0] = address;
        userProfileDto.setAddresses(addresses);
        userProfileDto.setBirthDate(birthDate);
        userProfileDto.setDataTransferToBp(dataTransferToBp);
        userProfileDto.setDataTransferToCashOn(dataTransferToCashOn);
        userProfileDto.setName(name);
        userProfileDto.setSurname(surname);
        userProfileDto.setNationalityId(nationalityId);
        userProfileDto.setPassportNumber(passportNumber);
        userProfileDto.setPhoneCode(phoneCode);
        userProfileDto.setTitle(title);
        user.setUserProfileDto(userProfileDto);
        user.setUserName(userName);
        user.setPartnerTrackingCode(partnerTrackingCode);
        return user;
    }
}
