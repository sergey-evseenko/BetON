package tests;

import models.UserInfo;

import java.io.FileNotFoundException;

public class UpdatedUserInfo extends BaseTest {

    UserInfo userInfo;

    public UpdatedUserInfo() throws FileNotFoundException {
    }

    /*

    @BeforeClass
    public void login() throws FileNotFoundException {
        user = gson.fromJson(new FileReader("src/test/resources/Data/userForLogin.json"), User.class);
        responseBody = new AuthorizationAdapter().post(user);
        token = responseBody.getAccessToken();
    }



    @AfterClass
    public void setDefaultUserInfo() throws FileNotFoundException {
        UserInfo expectedUserInfo;
        expectedUserInfo = gson.fromJson(new FileReader("src/test/resources/Data/userInfo.json"), UserInfo.class);
        user = gson.fromJson(new FileReader("src/test/resources/Data/userForRegistration.json"), User.class);
        userInfo = new UserAdapter().put(user, token);
        assertEquals(userInfo, expectedUserInfo, "setting default user info error");
    }




    @Test(description = "update user with valid data")
    public void updateUserWithValidData() throws FileNotFoundException {


        user = gson.fromJson(new FileReader("src/test/resources/Data/userForRegistration.json"), User.class);

        //updated user's phone
        user.setPhone(phone);

        //updated user's address
        Address updatedAddress = gson.fromJson(new FileReader("src/test/resources/Data/updatedAddress.json"), Address.class);
        userProfileDto.updateAddresses(updatedAddress);

        //updated user's date of birth
        userProfileDto.setBirthDate(birthDate);

        //update user's name
        userProfileDto.setName(name);
        //update user's surname
        userProfileDto.setSurname(surname);
        //update user's nationalityId
        userProfileDto.setNationalityId(nationalityId);
        //update user's passport number
        userProfileDto.setPassportNumber(passportNumber);
        //update user's phone code
        userProfileDto.setPhoneCode(phoneCode);
        //update user's title
        userProfileDto.setTitle(title);

        user.setUserProfileDto(userProfileDto);

        userInfo = new UserAdapter().put(user, token);


        assertEquals(userInfo.getPhone(), phone, "invalid phone");
        assertEquals(userInfo.getAddresses()[0], updatedAddress, "invalid phone");
        assertEquals(userInfo.getBirthDate(), birthDate, "invalid date of birth");
        assertEquals(userInfo.getName(), name, "invalid name");
        assertEquals(userInfo.getSurname(), surname, "invalid surname");
        assertEquals(userInfo.getNationalityId(), nationalityId, "invalid surname");
        assertEquals(userInfo.getPassportNumber(), passportNumber, "invalid surname");
        assertEquals(userInfo.getPhoneCode(), phoneCode, "invalid surname");
        assertEquals(userInfo.getTitle(), title, "invalid title");



        /*
        assertEquals(userInfo.getAddresses(), userProfileDto.getAddresses(), "invalid address");
        assertEquals(userInfo.getAddresses(), birthDate, "invalid date of birth");
        assertEquals(userInfo., name, "invalid name");
        assertEquals("", surname, "invalid nationality ID");
        assertEquals("", passportNumber, "invalid passport number");
        assertEquals("", userProfileDto.getPhoneCode(), "invalid phone code");
        assertEquals("", surname, "invalid surname");
        assertEquals("", title, "invalid title");
         */


}
