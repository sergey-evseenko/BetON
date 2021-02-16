package tests.registration;

import adapters.FieldValidationAdapter;
import models.Response;
import models.ValidateField;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;

public class ValidateFieldTest extends BaseTest {

    @DataProvider(name = "fields values")
    public Object[][] fieldsValues() {
        return new Object[][]{
                {"USERNAME", "polinazz", "username", false},
                {"USERNAME", "polinazzqwertyui", "", true},
                {"EMAIL", "qwerty81@gmail.com", "email", false},
                {"EMAIL", "qwerty81qwqwqw@gmail.com", "", true}
        };
    }

    @Test(description = "validation of username and emails field", dataProvider = "fields values")
    public void fieldValidation(String fieldType, String fieldValue, String field, Boolean isEmptyResponse) {
        ValidateField validateField = new ValidateField(fieldType, fieldValue);
        Response[] responseBody = new FieldValidationAdapter().post(validateField);
        if (isEmptyResponse) {
            assertEquals(responseBody.length, 0, "Incorrect field validation");
        } else {
            assertEquals(responseBody[0].getField(), field, "Invalid field");
            assertEquals(responseBody[0].getType(), "VALIDATION", "Invalid type");
            assertEquals(responseBody[0].getCode(), "ER0002", "Invalid code");
            assertEquals(responseBody[0].getDescription(), "Not unique value", "Invalid description");
        }
    }
}
