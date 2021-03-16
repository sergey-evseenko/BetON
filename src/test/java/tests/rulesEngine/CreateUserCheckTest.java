package tests.rulesEngine;

import models.Address;
import models.UserForCheck;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class CreateUserCheckTest extends BaseTest {

    UserForCheck userForCheck;
    Address address;

    @BeforeMethod(description = "get user for check")
    public void getUserForCheck() {
        userForCheck = data.get("userForCheck.json", UserForCheck.class);
    }

    @Test(description = "check not uniq email")
    public void checkNotUniqEmail() {
        userForCheck.setEmail("qwerty81@gmail.com");

        responseBetOn = rulesAdapter.checkUserPost(userForCheck);
        assertEquals(responseBetOn.getEmail(), "E0005", "Not uniq email error");
    }

    @Test(description = "check not uniq user name")
    public void checkNotUniqUserName() {
        userForCheck.setUsername("qwerty81");

        responseBetOn = rulesAdapter.checkUserPost(userForCheck);
        assertEquals(responseBetOn.getUserName(), "E0005", "Not uniq user name error");
    }

    @Test(description = "check not uniq FN, LN, nationality, date of birth")
    public void checkNotUniqParams() {
        userForCheck.setName("Test");
        userForCheck.setSurname("User");
        userForCheck.setNationalityId(19);
        userForCheck.setBirthDate("1990-02-05");

        responseBetOn = rulesAdapter.checkUserPost(userForCheck);
        assertEquals(responseBetOn.getPlace(), "E0006", "Not uniq place error");
    }

    @Test(description = "check invalid age 18")
    public void checkInvalidAge18() {
        address = userForCheck.getAddress();
        address.setCountryCode("BY");
        userForCheck.setAddress(address);
        userForCheck.setBirthDate("2005-12-28");

        responseBetOn = rulesAdapter.checkUserPost(userForCheck);
        assertEquals(responseBetOn.getAge(), "E0002", "Invalid BirthDate error");
    }

    @Test(description = "check invalid age 21")
    public void checkInvalidAge21() {
        address = userForCheck.getAddress();
        address.setCountryCode("EE");
        userForCheck.setAddress(address);
        userForCheck.setBirthDate("2001-02-05");

        responseBetOn = rulesAdapter.checkUserPost(userForCheck);
        assertEquals(responseBetOn.getAge(), "E0002", "Invalid BirthDate error");
    }

    @Test(description = "check invalid passport number")
    public void checkInvalidPasswordNumber() {
        userForCheck.setPassportNumber("KB2068392");

        responseBetOn = rulesAdapter.checkUserPost(userForCheck);
        assertEquals(responseBetOn.getIdCard(), "E0007", "Invalid password error");
    }
}
