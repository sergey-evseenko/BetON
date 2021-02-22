package tests.registration;

import models.Address;
import models.TermsAndConditionDto;
import models.User;
import models.UserProfileDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;
import tests.UserFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class RegistrationTest extends BaseTest {

    UserProfileDto userProfileDto;
    TermsAndConditionDto termsAndConditionDto;

    @BeforeMethod
    public void getNewUser() {
        user = new UserFactory().getNewUser();
        userProfileDto = user.getUserProfileDto();
        termsAndConditionDto = user.getTermsAndConditionDto();
    }

    @Test(description = "registration")
    public void registration() {
        responseBetOn = registrationAdapter.post(user, 200);
        assertEquals(responseBetOn.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(responseBetOn.getAccessToken(), null, "Invalid access token");
    }

    @Test(description = "Registration with Partner Tracking Code")
    public void registrationWithPartnerTrackingCode() {
        user.setPartnerTrackingCode("bwin");
        responseBetOn = registrationAdapter.post(user, 200);
        assertEquals(responseBetOn.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(responseBetOn.getAccessToken(), null, "Invalid access token");
    }

    @DataProvider(name = "Invalid emails")
    public Object[][] invalidEmails() {
        return new Object[][]{
                //invalid format
                {"qwerty", "ER0001", "Wrong value format"},
                //not unique value
                {"qwerty77270@gmail.com", "ER0002", "Not unique value"},
                //empty filed
                {"", "ER0001", "Wrong value format"},
                //is null
                {null, "ER0004", "Field is mandatory"},
                //emails are not equals
                {"qwerty98356@gmail.com", "ER0005", "Values are mismatch"},
                //not acceptable symbols
                {"йцукеув@gmail.com", "ER0001", "Wrong value format"},
        };
    }

    @Test(description = "validate email", dataProvider = "Invalid emails")
    public void validateEmail(String email, String code, String description) {
        user.setEmail(email);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "email", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid passwords")
    public Object[][] invalidPasswords() {
        return new Object[][]{
                //upper letters
                {"QWERTY!123", "ER0001", "Wrong value format"},
                //lower letters
                {"qwerty!123", "ER0001", "Wrong value format"},
                //missed numbers
                {"Qwertyuiop!", "ER0001", "Wrong value format"},
                //min length
                {"Qw!123", "ER0007", "Wrong value size"},
                //max length
                {faker.lorem().characters(100) + "Qw!123", "ER0007", "Wrong value size"},
                //is null
                {null, "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "validate password", dataProvider = "Invalid passwords")
    public void validatePassword(String password, String code, String description) {
        user.setPassword(password);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "password", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @Test(description = "validate password contains first name")
    public void validatePasswordContainsFirstName() {
        user.setPassword(userProfileDto.getName() + "Q123!");
        user.setRepeatedPassword(userProfileDto.getName() + "Q123!");
        postAndValidateResponse(user);
    }

    @Test(description = "validate password contains last name")
    public void validatePasswordContainsLastName() {
        user.setPassword(userProfileDto.getSurname() + "Q123!");
        user.setRepeatedPassword(userProfileDto.getSurname() + "Q123!");
        postAndValidateResponse(user);
    }

    @Test(description = "validate password contains user name")
    public void validatePasswordContainsUserName() {
        user.setPassword(user.getUserName() + "Q123!");
        user.setRepeatedPassword(user.getUserName() + "Q123!");
        postAndValidateResponse(user);
    }

    public void postAndValidateResponse(User user) {
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "password.", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), "ER0008", "Invalid code");
        assertEquals(responseBetOn.getDescription(), "Please do not use Username / Name / Surname as password", "Invalid description");
    }

    @Test(description = "Registration with the same phone")
    public void registrationWithTheSamePhone() {
        user.setPhone("+375292907810");
        responseBetOn = registrationAdapter.post(user, 200);
        assertEquals(responseBetOn.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(responseBetOn.getAccessToken(), null, "Invalid access token");
    }

    @DataProvider(name = "Invalid phones")
    public Object[][] invalidPhones() {
        return new Object[][]{
                //+ and letters
                {"+xascasca", "ER0001", "Wrong value format"},
                //without +
                {"375292907810", "ER0001", "Wrong value format"},
                //is empty
                {"", "ER0001", "Wrong value format"},
                //is null
                {null, "ER0004", "Field is mandatory"},
                //max length
                {"+375292907810012345676543223456787654323456789", "ER0007", "Wrong value size"}
        };
    }

    @Test(description = "validate phone", dataProvider = "Invalid phones")
    public void validatePhone(String phone, String code, String description) {
        user.setPhone(phone);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "phone", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid repeated emails")
    public Object[][] invalidRepeatedEmails() {
        return new Object[][]{
                //invalid format
                {"qwerty", "ER0001", "Wrong value format"},
                //empty field
                {"", "ER0001", "Wrong value format"},
                //is nul
                {null, "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "validate repeated email", dataProvider = "Invalid repeated emails")
    public void validateRepeatedEmail(String repeatedEmail, String code, String description) {
        user.setRepeatedEmail(repeatedEmail);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "repeatedEmail", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid repeated passwords")
    public Object[][] invalidRepeatedPasswords() {
        return new Object[][]{
                //lower letters
                {"qwerty!123", "repeatedPassword", "ER0001", "Wrong value format"},
                //min length
                {"Qw!123", "repeatedPassword", "ER0007", "Wrong value size"},
                //is null
                {null, "repeatedPassword", "ER0004", "Field is mandatory"},
                //passwords are not equals
                {"Qwerty!1234", "password", "ER0005", "Values are mismatch"}
        };
    }

    @Test(description = "validate repeated password", dataProvider = "Invalid repeated passwords")
    public void validateRepeatedPassword(String repeatedPassword, String field, String code, String description) {
        user.setRepeatedPassword(repeatedPassword);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), field, "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid terms and conditions")
    public Object[][] invalidTermsAndConditions() {
        return new Object[][]{
                //is null
                {null},
                //is false
                {false}
        };
    }

    @Test(description = "validate terms and conditions", dataProvider = "Invalid terms and conditions")
    public void validateTermsAndConditions(Boolean accept) {
        termsAndConditionDto.setAccept(accept);
        user.setTermsAndConditionDto(termsAndConditionDto);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "termsAndConditionDto.accept", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), "ER0010", "Invalid code");
        assertEquals(responseBetOn.getDescription(), "You must agree to create an account", "Invalid description");
    }

    @Test(description = "Registration with null address")
    public void registrationWithNullAddress() {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setAddresses(null);
        user.setUserProfileDto(userProfileDto);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "userProfileDto.addresses", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), "ER0004", "Invalid code");
        assertEquals(responseBetOn.getDescription(), "Field is mandatory", "Invalid description");
    }

    @DataProvider(name = "Invalid country codes")
    public Object[][] invalidCountryCodes() {
        return new Object[][]{
                //unacceptable symbols
                {"12", "userProfileDto.addresses[0].countryCode", "ER0001", "Wrong value format"},
                //not existing code
                {"ZZ", "countryCode", "ER0009", "Incorrect value"},
                //is empty
                {"", "userProfileDto.addresses[0].countryCode", "ER0001", "Wrong value format"},
                //is null
                {null, "userProfileDto.addresses[0].countryCode", "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "validate country code", dataProvider = "Invalid country codes")
    public void validateCountryCode(String countryCode, String field, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        Address[] addresses = userProfileDto.getAddresses();
        addresses[0].setCountryCode(countryCode);
        userProfileDto.setAddresses(addresses);
        user.setUserProfileDto(userProfileDto);

        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), field, "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid birth dates")
    public Object[][] invalidBirthDate() {
        return new Object[][]{
                //is null
                {null},
                //is empty
                {""}
        };
    }

    @Test(description = "validate birth date", dataProvider = "Invalid birth dates")
    public void validateBirthDate(String birthDate) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setBirthDate(birthDate);
        user.setUserProfileDto(userProfileDto);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "userProfileDto.birthDate", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), "ER0004", "Invalid code");
        assertEquals(responseBetOn.getDescription(), "Field is mandatory", "Invalid description");
    }

    @DataProvider(name = "Invalid dataTransferToBp")
    public Object[][] invalidDataTransferToBp() {
        return new Object[][]{
                //false
                {false, "dataTransferToBp", "ER0010", "You must agree to create an account"},
                //is null
                {null, "userProfileDto.dataTransferToBp", "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "validate dataTransferToBp", dataProvider = "Invalid dataTransferToBp")
    public void validateDataTransferToBp(Boolean dataTransferToBp, String field, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setDataTransferToBp(dataTransferToBp);
        user.setUserProfileDto(userProfileDto);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), field, "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid dataTransferToCashOn")
    public Object[][] invalidDataTransferToCashOn() {
        return new Object[][]{
                //false
                {false, "dataTransferToCashOn", "ER0010", "You must agree to create an account"},
                //is null
                {null, "userProfileDto.dataTransferToCashOn", "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "validate dataTransferToCashOn", dataProvider = "Invalid dataTransferToCashOn")
    public void validateDataTransferToCashOn(Boolean dataTransferToCashOn, String field, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setDataTransferToCashOn(dataTransferToCashOn);
        user.setUserProfileDto(userProfileDto);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), field, "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid names")
    public Object[][] invalidName() {
        return new Object[][]{
                //is empty
                {"", "ER0007", "Wrong value size"},
                //is null
                {null, "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "validate name", dataProvider = "Invalid names")
    public void validateName(String name, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setName(name);
        user.setUserProfileDto(userProfileDto);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "userProfileDto.name", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid surnames")
    public Object[][] invalidSurname() {
        return new Object[][]{
                //empty field
                {"", "ER0007", "Wrong value size"},
                //null
                {null, "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "validate surname", dataProvider = "Invalid surnames")
    public void validateSurname(String surname, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setSurname(surname);
        user.setUserProfileDto(userProfileDto);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "userProfileDto.surname", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid nationality ids")
    public Object[][] invalidNationalityId() {
        return new Object[][]{
                //is 0
                {0, "userProfileDto.nationalityId", "ER0006", "Choose the value"},
                //not existing
                {999999, "nationalityId", "ER0009", "Incorrect value"}
        };
    }

    @Test(description = "validate nationality id", dataProvider = "Invalid nationality ids")
    public void validateNationalityId(int nationalityId, String field, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setNationalityId(nationalityId);
        user.setUserProfileDto(userProfileDto);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), field, "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid titles")
    public Object[][] invalidTitle() {
        return new Object[][]{
                //is 0
                {0, "userProfileDto.title", "ER0006", "Choose the value"},
                //not existing
                {121212, "titleId", "ER0009", "Incorrect value"}
        };
    }

    @Test(description = "validate invalid title", dataProvider = "Invalid titles")
    public void validateTitle(int title, String field, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setTitle(title);
        user.setUserProfileDto(userProfileDto);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), field, "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid usernames")
    public Object[][] invalidUsername() {
        return new Object[][]{
                //invalid username: min length
                {"qwerty", "ER0007", "Wrong value size"},
                //invalid username: invalid symbols
                {"$%^&*(*&^%$#$%^&**&^%$#", "ER0001", "Wrong value format"},
                //invalid username: existing username
                {"testtest123", "ER0002", "Not unique value"},
                //invalid username: is null
                {null, "ER0004", "Field is mandatory"},
                //invalid username: is empty
                {"", "ER0001", "Wrong value format"},
        };
    }

    @Test(description = "validate title", dataProvider = "Invalid usernames")
    public void validateUsername(String username, String code, String description) {
        user.setUserName(username);
        responseBetOn = registrationAdapter.post(user, 400);
        assertEquals(responseBetOn.getField(), "username", "Invalid field");
        assertEquals(responseBetOn.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBetOn.getCode(), code, "Invalid code");
        assertEquals(responseBetOn.getDescription(), description, "Invalid description");
    }


}
