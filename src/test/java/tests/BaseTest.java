package tests;

import com.github.javafaker.Faker;
import models.ResponseBody;
import models.User;
import org.testng.annotations.Listeners;
import utils.DataReader;
import utils.TestListener;

@Listeners(TestListener.class)
public class BaseTest {
    protected String token;
    protected ResponseBody responseBody;
    protected User user;
    protected Faker faker = new Faker();
    protected DataReader data = new DataReader();
}

