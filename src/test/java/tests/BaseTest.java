package tests;

import adapters.AuthorizationAdapter;
import com.github.javafaker.Faker;
import models.ResponseBody;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.DataReader;
import utils.TestListener;

import java.util.Random;

@Listeners(TestListener.class)
public class BaseTest {
    protected String token;
    protected ResponseBody responseBody;
    protected User user;
    protected Faker faker = new Faker();
    protected Random random = new Random();
    protected DataReader data = new DataReader();

    @BeforeClass
    public void getToken() {
        user = data.get("userForLogin.json", User.class);
        responseBody = new AuthorizationAdapter().post(user, "clientId", "secret", 200);
        token = responseBody.getAccessToken();
    }
}

