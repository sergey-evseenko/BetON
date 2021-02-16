package tests.userInfo;

import adapters.UserAdapter;
import models.Address;
import models.User;
import models.UserInfo;
import models.UserProfileDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class UpdateUserInfoTest extends BaseTest {

    UserProfileDto userProfileDto;

    @BeforeMethod
    public void sutUpTestData() {
        user = data.get("user.json", User.class);
        userProfileDto = user.getUserProfileDto();
    }

    @Test(description = "Update user info")
    public void updateUserInfo() {
        User updatedUser = data.get("userUpdated.json", User.class);
        UserInfo expectedUserInfo = data.get("userInfoUpdated.json", UserInfo.class);
        UserInfo actualUserInfo = new UserAdapter().put(updatedUser, token);
        assertEquals(actualUserInfo, expectedUserInfo, "Updating user info error");
        //set default user info
        new UserAdapter().put(user, token);
    }

    @DataProvider(name = "Invalid phones")
    public Object[][] invalidPhones() {
        return new Object[][]{
                //+ and letters
                {"+bdfbdbdf", "ER0001", "Wrong value format"},
                //empty phone
                {"", "ER0001", "Wrong value format"},
                //is null
                {null, "ER0004", "Field is mandatory"},
                //without +
                {"375291234567", "ER0001", "Wrong value format"},
                //max size
                {"+375292907810012345676543223456787654323456789", "ER0007", "Wrong value size"}
        };
    }

    @Test(description = "validate phone", dataProvider = "Invalid phones")
    public void validatePhone(String phone, String code, String description) {
        user.setPhone(phone);
        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), "phone", "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid titles")
    public Object[][] invalidTitles() {
        return new Object[][]{
                //not existing id
                {123456, "titleId", "ER0009", "Incorrect value"},
                //is 0
                {0, "userProfileDto.title", "ER0006", "Choose the value"}
                //TODO: "is null title" case is skipped
        };
    }

    @Test(description = "validate title", dataProvider = "Invalid titles")
    public void validateTitle(int title, String field, String code, String description) {
        userProfileDto.setTitle(title);
        user.setUserProfileDto(userProfileDto);

        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), field, "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid surnames")
    public Object[][] invalidSurnames() {
        return new Object[][]{
                //max size
                {"qwertyuytrewwedrtyuiuytrertyuiuytrrtywertrewrtytreuiuytrertyu", "ER0007", "Wrong value size"},
                //is null
                {null, "ER0004", "Field is mandatory"},
                //is empty
                {"", "ER0007", "Wrong value size"}
        };
    }

    @Test(description = "validate surname", dataProvider = "Invalid surnames")
    public void validateSurname(String surname, String code, String description) {
        userProfileDto.setSurname(surname);
        user.setUserProfileDto(userProfileDto);

        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), "userProfileDto.surname", "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid phoneCodes")
    public Object[][] invalidPhoneCodes() {
        return new Object[][]{
                //is empty
                {"", "ER0001", "Wrong value format"},
                //is null
                {null, "ER0004", "Field is mandatory"},
                //incorrect format
                {"амвамв", "ER0001", "Wrong value format"}
        };
    }

    @Test(description = "validate phone code", dataProvider = "Invalid phoneCodes")
    public void validatePhoneCode(String phoneCode, String code, String description) {
        userProfileDto.setPhoneCode(phoneCode);
        user.setUserProfileDto(userProfileDto);

        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), "userProfileDto.phoneCode", "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid passportNumbers")
    public Object[][] invalidPassportNumbers() {
        return new Object[][]{
                //max size
                {faker.lorem().characters(350), "ER0007", "Wrong value size"},
                //is null
                {null, "ER0004", "Field is mandatory"},
                //is empty
                {"", "ER0007", "Wrong value size"}
        };
    }

    @Test(description = "validate passport number", dataProvider = "Invalid passportNumbers")
    public void validatePassportNumber(String passportNumber, String code, String description) {
        userProfileDto.setPassportNumber(passportNumber);
        user.setUserProfileDto(userProfileDto);

        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), "userProfileDto.passportNumber", "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid nationalityIds")
    public Object[][] invalidNationalityIds() {
        return new Object[][]{
                //not existing
                {1300, "nationalityId", "ER0009", "Incorrect value"},
                //is 0
                {0, "userProfileDto.nationalityId", "ER0006", "Choose the value"}
                //TODO: "is null nationalityId" case is skipped
        };
    }

    @Test(description = "validate nationality id", dataProvider = "Invalid nationalityIds")
    public void validateNationalityId(int nationalityId, String field, String code, String description) {
        userProfileDto.setNationalityId(nationalityId);
        user.setUserProfileDto(userProfileDto);

        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), field, "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid names")
    public Object[][] invalidNames() {
        return new Object[][]{
                //max size
                {faker.lorem().characters(60), "ER0007", "Wrong value size"},
                //is null
                {null, "ER0004", "Field is mandatory"},
                //is empty
                {"", "ER0007", "Wrong value size"}
        };
    }

    @Test(description = "validate name", dataProvider = "Invalid names")
    public void validateName(String name, String code, String description) {
        userProfileDto.setName(name);
        user.setUserProfileDto(userProfileDto);

        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), "userProfileDto.name", "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), description, "Invalid description");
    }

    @DataProvider(name = "Invalid birth dates")
    public Object[][] invalidBirthDates() {
        return new Object[][]{
                //is null
                {null},
                //is empty
                {""}
        };
    }

    @Test(description = "validate birth date", dataProvider = "Invalid birth dates")
    public void validateBirthDate(String birthDate) {
        userProfileDto.setBirthDate(birthDate);
        user.setUserProfileDto(userProfileDto);

        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), "userProfileDto.birthDate", "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), "ER0004", "Invalid code");
        assertEquals(response.getDescription(), "Field is mandatory", "Invalid description");
    }

    @Test(description = "Update user info with invalid birth date")
    public void updateInvalidBirthDate() {
        userProfileDto.setBirthDate("02-05-1990");
        user.setUserProfileDto(userProfileDto);

        response = new UserAdapter().putInvalidBirthDate(user, token);
        assertEquals(response.getError(), "Bad Request", "Invalid error message");
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

        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), field, "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), description, "Invalid description");
    }

    @Test(description = "Update without address")
    public void updateWithoutAddress() {
        userProfileDto = user.getUserProfileDto();
        userProfileDto.setAddresses(null);
        user.setUserProfileDto(userProfileDto);

        response = new UserAdapter().putInvalidData(user, token);
        assertEquals(response.getField(), "userProfileDto.addresses", "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), "ER0004", "Invalid code");
        assertEquals(response.getDescription(), "Field is mandatory", "Invalid description");
    }

}
