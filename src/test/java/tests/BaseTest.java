package tests;

import adapters.*;
import com.github.javafaker.Faker;
import models.ResponseBetOn;
import models.User;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import utils.DataReader;
import utils.TestListener;

import java.util.Random;

@Listeners(TestListener.class)
public class BaseTest {
    protected ResponseBetOn responseBetOn;
    protected User user;
    protected Faker faker = new Faker();
    protected Random random = new Random();
    protected DataReader data = new DataReader();
    public static String token;
    protected AuthorizationAdapter authorizationAdapter = new AuthorizationAdapter();
    protected ContentAdapter contentAdapter = new ContentAdapter();
    protected GetRegistrationDataAdapted getRegistrationDataAdapted = new GetRegistrationDataAdapted();
    protected RegistrationAdapter registrationAdapter = new RegistrationAdapter();
    protected ResendEmailAdapter resendEmailAdapter = new ResendEmailAdapter();
    protected RulesAdapter rulesAdapter = new RulesAdapter();
    protected MatchDayAdapter matchDayAdapter = new MatchDayAdapter();
    protected TranslationAdapter translationAdapter = new TranslationAdapter();
    protected TemplateAdapter templateAdapter = new TemplateAdapter();
    protected PlayerAdapter playerAdapter = new PlayerAdapter();
    protected UserAdapter userAdapter = new UserAdapter();
    protected FavouritesAdapter favouritesAdapter = new FavouritesAdapter();
    protected CatalogAdapter catalogAdapter = new CatalogAdapter();

    @BeforeSuite
    public void getToken() {
        user = data.get("userForLogin.json", User.class);
        token = new MainAdapter().getToken(user);
    }
}

