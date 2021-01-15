package tests;

import adapters.RegistrationAdapter;
import models.TermsAndConditionDto;
import models.UserProfileDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class RegistrationTest extends BaseTest {

    UserProfileDto userProfileDto;
    TermsAndConditionDto termsAndConditionDto;

    @BeforeMethod
    public void getNewUser() throws FileNotFoundException {
        user = new UserFactory().getNewUser();
        userProfileDto = user.getUserProfileDto();
        termsAndConditionDto = user.getTermsAndConditionDto();
    }

    @Test(description = "Registration with valid data")
    public void registrationWithValidData() {
        responseBody = new RegistrationAdapter().post(user, 200);
        assertEquals(responseBody.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(responseBody.getAccessToken(), null, "Invalid access token");
    }

    @Test(description = "Registration with Partner Tracking Code")
    public void registrationWithPartnerTrackingCode() {
        user.setPartnerTrackingCode("bwin");
        responseBody = new RegistrationAdapter().post(user, 200);
        assertEquals(responseBody.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(responseBody.getAccessToken(), null, "Invalid access token");
    }

    @Test(description = "Registration with the same phone")
    public void registrationWithTheSamePhone() {
        user.setPhone("+375292907810");
        responseBody = new RegistrationAdapter().post(user, 200);
        assertEquals(responseBody.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(responseBody.getAccessToken(), null, "Invalid access token");
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

    @Test(description = "Registration with invalid email", dataProvider = "Invalid emails")
    public void registrationWithInvalidEmail(String email, String code, String description) {
        user.setEmail(email);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "email", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
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
                //is null
                {null, "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "Registration with invalid password", dataProvider = "Invalid passwords")
    public void registrationWithInvalidPassword(String password, String code, String description) {
        user.setPassword(password);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "password", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid passwords with name")
    public Object[][] invalidPasswordsWithNames() {
        return new Object[][]{
                //contain first name
                {userProfileDto.getName()},
                //contain last name
                {userProfileDto.getSurname()},
                //contain user name
                {user.getUserName()}
        };
    }

    @Ignore
    @Test(description = "Registration with invalid password contains name", dataProvider = "Invalid passwords with name")
    public void registrationWithInvalidPasswordContainsName(String password) {
        user.setPassword(password + "Q123!");
        user.setRepeatedPassword(password + "Q123!");
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "password.", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), "ER0008", "Invalid code");
        assertEquals(responseBody.getDescription(), "Please do not use Username / Name / Surname as password", "Invalid description");
    }

    @DataProvider(name = "Invalid phones")
    public Object[][] invalidPhones() {
        return new Object[][]{
                //+ and letters
                {"+xascasca", "ER0001", "Wrong value format"},
                //without +
                {"375292907810", "ER0001", "Wrong value format"},
                //is null
                {null, "ER0004", "Field is mandatory"},
                //max length
                {"+375292907810012345676543223456787654323456789", "ER0007", "Wrong value size"}
        };
    }

    @Test(description = "Registration with invalid phone", dataProvider = "Invalid phones")
    public void registrationWithInvalidPhone(String phone, String code, String description) {
        user.setPhone(phone);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "phone", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
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

    @Test(description = "Registration with invalid repeated email", dataProvider = "Invalid repeated emails")
    public void registrationWithInvalidRepeatedEmail(String repeatedEmail, String code, String description) {
        user.setRepeatedEmail(repeatedEmail);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "repeatedEmail", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
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

    @Test(description = "Registration with invalid repeated password", dataProvider = "Invalid repeated passwords")
    public void registrationWithInvalidRepeatedPassword(String repeatedPassword, String field, String code, String description) {
        user.setRepeatedPassword(repeatedPassword);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), field, "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid terms and conditions")
    public Object[][] invalidTermsAndConditions() {
        return new Object[][]{
                //invalid termsAndConditionDto: is null
                {null},
                //invalid termsAndConditionDto: is false
                {false}
        };
    }

    @Test(description = "Registration with invalid terms and conditions", dataProvider = "Invalid terms and conditions")
    public void registrationWithInvalidTermsAndConditions(Boolean accept) {
        termsAndConditionDto.setAccept(accept);
        user.setTermsAndConditionDto(termsAndConditionDto);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "termsAndConditionDto.accept", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), "ER0010", "Invalid code");
        assertEquals(responseBody.getDescription(), "You must agree to create an account", "Invalid description");
    }

    @Test(description = "Registration with null address")
    public void registrationWithNullAddress() {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setAddresses(null);
        user.setUserProfileDto(userProfileDto);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "userProfileDto.addresses", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), "ER0004", "Invalid code");
        assertEquals(responseBody.getDescription(), "Field is mandatory", "Invalid description");
    }

    //TODO invalid address block: empty fields
    //TODO invalid address block: country code - not existing code
    //TODO invalid address block: country code - unacceptable symbols

    @DataProvider(name = "Invalid birth dates")
    public Object[][] invalidBirthDate() {
        return new Object[][]{
                //is null
                {null},
                //empty filed
                {""}
        };
    }

    @Test(description = "Registration with invalid birth date", dataProvider = "Invalid birth dates")
    public void registrationWithInvalidBirthDate(String birthDate) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setBirthDate(birthDate);
        user.setUserProfileDto(userProfileDto);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "userProfileDto.birthDate", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), "ER0004", "Invalid code");
        assertEquals(responseBody.getDescription(), "Field is mandatory", "Invalid description");
    }

    @DataProvider(name = "Invalid dataTransferToBp")
    public Object[][] invalidDataTransferToBp() {
        return new Object[][]{
                //false
                {false, "dataTransferToBp", "ER0010", "You must agree to create an account"},
                //null
                {null, "userProfileDto.dataTransferToBp", "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "Registration with invalid dataTransferToBp", dataProvider = "Invalid dataTransferToBp")
    public void registrationWithInvalidDataTransferToBp(Boolean dataTransferToBp, String field, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setDataTransferToBp(dataTransferToBp);
        user.setUserProfileDto(userProfileDto);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), field, "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid names")
    public Object[][] invalidName() {
        return new Object[][]{
                //empty field
                {"", "ER0007", "Wrong value size"},
                //null
                {null, "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "Registration with invalid name", dataProvider = "Invalid names")
    public void registrationWithInvalidName(String name, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setName(name);
        user.setUserProfileDto(userProfileDto);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "userProfileDto.name", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
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

    @Test(description = "Registration with invalid surname", dataProvider = "Invalid surnames")
    public void registrationWithInvalidSurname(String surname, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setSurname(surname);
        user.setUserProfileDto(userProfileDto);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "userProfileDto.surname", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid nationality ids")
    public Object[][] invalidNationalityId() {
        return new Object[][]{
                //is 0
                {0, "userProfileDto.nationalityId", "ER0006", "Choose the value"},
                //null
                {999999, "nationalityId", "ER0009", "Incorrect value"}
        };
    }

    @Test(description = "Registration with invalid nationality id", dataProvider = "Invalid nationality ids")
    public void registrationWithInvalidNationalityId(int nationalityId, String field, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setNationalityId(nationalityId);
        user.setUserProfileDto(userProfileDto);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), field, "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid titles")
    public Object[][] invalidTitle() {
        return new Object[][]{
                //is 0
                {0, "userProfileDto.title", "ER0006", "Choose the value"},
                //null
                {121212, "titleId", "ER0009", "Incorrect value"}
        };
    }

    @Test(description = "Registration with invalid title", dataProvider = "Invalid titles")
    public void registrationWithInvalidTitle(int title, String field, String code, String description) {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setTitle(title);
        user.setUserProfileDto(userProfileDto);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), field, "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
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
                {null, "ER0004", "Field is mandatory"}
        };
    }

    @Test(description = "Registration with invalid title", dataProvider = "Invalid usernames")
    public void registrationWithInvalidUsername(String username, String code, String description) {
        user.setUserName(username);
        responseBody = new RegistrationAdapter().post(user, 400);
        assertEquals(responseBody.getField(), "username", "Invalid field");
        assertEquals(responseBody.getType(), "VALIDATION", "Invalid type");
        assertEquals(responseBody.getCode(), code, "Invalid code");
        assertEquals(responseBody.getDescription(), description, "Invalid description");
    }


}
