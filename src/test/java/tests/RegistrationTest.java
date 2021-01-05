package tests;

import adapters.RegistrationAdapter;
import com.github.javafaker.Faker;
import models.Response;
import models.User;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class RegistrationTest {
    Faker faker = new Faker();
    Response response;
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


    @DataProvider(name = "Valid registration data")
    public Object[][] validData() {
        return new Object[][]{
                {"0" + email, password, phone, "0" + repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, "0" + name, surname, nationalityId, title, "0" + userName},
                //title is null
                {"1" + email, password, phone, "1" + repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, "1" + name, surname, nationalityId, null, "1" + userName},
                //the same phone
                {"2" + email, password, "+375292907810", "2" + repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, "2" + name, surname, nationalityId, null, "2" + userName},

        };
    }

    @DataProvider(name = "Invalid registration data")
    public Object[][] invalidData() {
        return new Object[][]{

                //invalid email: format
                {"email", "ER0001", "Wrong value format", "qwerty", password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid email: not unique email
                {"email", "ER0002", "Not unique value", "qwerty77270@gmail.com", password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid email: empty field
                {"email", "ER0001", "Wrong value format", "", password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid email: is null
                {"email", "ER0004", "Field is mandatory", null, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid email: emails are not equal
                {"email", "ER0005", "Values are mismatch", "qwerty98356@gmail.com", password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid email: not acceptable symbols
                {"email", "ER0001", "Wrong value format", "йцукеув@gmail.com", password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},

                //invalid password: upper letters
                {"password", "ER0001", "Wrong value format", email, "QWERTY!123", phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid password: lower letters
                {"password", "ER0001", "Wrong value format", email, "qwerty!123", phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid password: missed numbers
                {"password", "ER0001", "Wrong value format", email, "Qwertyuiop!", phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid password: min length
                {"password", "ER0007", "Wrong value size", email, "Qw!123", phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid password: is null
                {"password", "ER0004", "Field is mandatory", email, null, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid password: contains first name
                {"password.", "ER0008", "Please do not use Username / Name / Surname as password", email, name + "123!", phone, repeatedEmail, name + "123!", accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid password: contains last name
                {"password.", "ER0008", "Please do not use Username / Name / Surname as password", email, surname + "123!", phone, repeatedEmail, surname + "123!", accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid password: contains user name
                {"password.", "ER0008", "Please do not use Username / Name / Surname as password", email, userName + "Q123!", phone, repeatedEmail, userName + "Q123!", accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},

                //invalid phone: + and letters
                {"phone", "ER0001", "Wrong value format", email, password, "+xascasca", repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid phone: without +
                {"phone", "ER0001", "Wrong value format", email, password, "375292907810", repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid phone: is null
                {"phone", "ER0004", "Field is mandatory", email, password, null, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid phone: max length
                {"phone", "ER0007", "Wrong value size", email, password, "+375292907810012345676543223456787654323456789", repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid phone: mix length
                {"phone", "ER0001", "Wrong value format", email, password, "911", repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},

                //invalid repeatedEmail: format
                {"repeatedEmail", "ER0001", "Wrong value format", email, password, phone, "qwerty", repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid repeatedEmail: empty field
                {"repeatedEmail", "ER0001", "Wrong value format", email, password, phone, "", repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid repeatedEmail: is nul
                {"repeatedEmail", "ER0004", "Field is mandatory", email, password, phone, null, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},

                //invalid repeatedPassword: lower letters
                {"repeatedPassword", "ER0001", "Wrong value format", email, password, phone, repeatedEmail, "qwerty!123", accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid repeatedPassword: min length
                {"repeatedPassword", "ER0007", "Wrong value size", email, password, phone, repeatedEmail, "Qw!123", accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid repeatedPassword: is null
                {"repeatedPassword", "ER0004", "Field is mandatory", email, password, phone, repeatedEmail, null, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid repeatedPassword: passwords are not equals
                {"password", "ER0005", "Values are mismatch", email, password, phone, repeatedEmail, "Qwerty!1234", accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},

                //invalid termsAndConditionDto: is null
                {"termsAndConditionDto.accept", "ER0010", "You must agree to create an account", email, password, phone, repeatedEmail, repeatedPassword, null, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid termsAndConditionDto: is false
                {"termsAndConditionDto.accept", "ER0010", "You must agree to create an account", email, password, phone, repeatedEmail, repeatedPassword, false, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},

                //TODO invalid adress block: is null
                //TODO invalid adress block: empty fields
                //TODO invalid address block: without block at all
                //TODO invalid country code: not existing code
                //TODO invalid country code: unacceptable symbols

                //invalid birthDate: is null
                {"userProfileDto.birthDate", "ER0004", "Field is mandatory", email, password, phone, repeatedEmail, repeatedPassword, accept, null, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid birthDate: empty field
                {"userProfileDto.birthDate", "ER0004", "Field is mandatory", email, password, phone, repeatedEmail, repeatedPassword, accept, "", dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //TODO: invalid birthDate: format

                //invalid dataTransferToBp = false
                {"dataTransferToBp", "ER0010", "You must agree to create an account", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, false, dataTransferToCashOn, name, surname, nationalityId, title, userName},
                //invalid dataTransferToBp = null
                {"userProfileDto.dataTransferToBp", "ER0004", "Field is mandatory", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, null, dataTransferToCashOn, name, surname, nationalityId, title, userName},

                //invalid dataTransferToCashOn=false
                {"dataTransferToCashOn", "ER0010", "You must agree to create an account", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, false, name, surname, nationalityId, title, userName},
                //invalid dataTransferToCashOn: is null
                {"userProfileDto.dataTransferToCashOn", "ER0004", "Field is mandatory", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, null, name, surname, nationalityId, title, userName},

                //invalid name: empty field
                {"userProfileDto.name", "ER0007", "Wrong value size", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, "", surname, nationalityId, title, userName},
                //invalid name: is null
                {"userProfileDto.name", "ER0004", "Field is mandatory", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, null, surname, nationalityId, title, userName},

                //invalid surname: empty field
                {"userProfileDto.surname", "ER0007", "Wrong value size", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, "", nationalityId, title, userName},
                //invalid surname: is null
                {"userProfileDto.surname", "ER0004", "Field is mandatory", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, null, nationalityId, title, userName},

                //invalid nationalityId: is 0
                {"userProfileDto.nationalityId", "ER0006", "Choose the value", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, 0, title, userName},
                //invalid nationalityId: not existing ID
                {"nationalityId", "ER0009", "Incorrect value", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, 999999, title, userName},
                //TODO: invalid nationalityId: is null

                //invalid title: is 0
                {"userProfileDto.title", "ER0006", "Choose the value", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, "0", userName},
                //invalid title: not existing ID
                {"titleId", "ER0009", "Incorrect value", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, "121212", userName},

                //invalid username: min length
                {"username", "ER0007", "Wrong value size", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, "qwerty"},
                //invalid username: invalid symbols
                {"username", "ER0001", "Wrong value format", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, "$%^&*(*&^%$#$%^&**&^%$#"},
                //invalid username: existing username
                {"username", "ER0002", "Not unique value", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, "testtest123"},
                //invalid username: is null
                {"username", "ER0004", "Field is mandatory", email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, null}
                //TODO: invalid username: empty field

        };

    }

    @Test(description = "Registration with invalid data", dataProvider = "Invalid registration data")
    public void registrationWithInvalidData(String field, String code, String description, String email, String password, String phone, String repeatedEmail, String repeatedPassword, Boolean accept, String birthDate, Boolean dataTransferToBp, Boolean dataTransferToCashOn, String name, String surname, int nationalityId, String title, String userName) throws FileNotFoundException {
        User user = new UserFactory().getUser(email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName);
        response = new RegistrationAdapter().post(user, 400);
        assertEquals(response.getField(), field, "Invalid field");
        assertEquals(response.getType(), "VALIDATION", "Invalid type");
        assertEquals(response.getCode(), code, "Invalid code");
        assertEquals(response.getDescription(), description, "Invalid description");

    }

    @Test(description = "Registration with valid data", dataProvider = "Valid registration data")
    public void registrationWithValidData(String email, String password, String phone, String repeatedEmail, String repeatedPassword, Boolean accept, String birthDate, Boolean dataTransferToBp, Boolean dataTransferToCashOn, String name, String surname, int nationalityId, String title, String userName) throws FileNotFoundException {
        User user = new UserFactory().getUser(email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName);
        response = new RegistrationAdapter().post(user, 200);
        assertEquals(response.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(response.getAccessToken(), null, "Invalid access token");
    }

    @Test(description = "Registration with Partner Tracking Code")
    public void registrationWithPartnerTrackingCode() throws FileNotFoundException {
        User user = new UserFactory().getUser(email, password, phone, repeatedEmail, repeatedPassword, accept, birthDate, dataTransferToBp, dataTransferToCashOn, name, surname, nationalityId, title, userName);
        user.setPartnerTrackingCode(partnerTrackingCode);
        response = new RegistrationAdapter().post(user, 200);
        assertEquals(response.getEmail(), user.getEmail(), "Invalid email");
        assertNotEquals(response.getAccessToken(), null, "Invalid access token");
    }
}
