package tests;

import adapters.AuthorizationAdapter;
import com.github.javafaker.Faker;
import models.Response;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import utils.DataReader;
import utils.TestListener;

import java.util.Random;

@Listeners(TestListener.class)
public class BaseTest {
    protected String token;
    protected Response response;
    protected User user;
    protected Faker faker = new Faker();
    protected Random random = new Random();
    protected DataReader data = new DataReader();

    @BeforeClass
    public void getToken() {
        user = data.get("userForLogin.json", User.class);
        response = new AuthorizationAdapter().post(user, "clientId", "secret", 200);
        token = response.getAccessToken();
    }
}

