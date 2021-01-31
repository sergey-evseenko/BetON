package adapters;

import models.ResponseBody;
import models.ValidateField;

public class FieldValidationAdapter extends MainAdapter {

    public ResponseBody[] post(ValidateField validateField) {
        response = post("auth/v1/users/field/validate", 200, gson.toJson(validateField));
        return gson.fromJson(response.asString().trim(), ResponseBody[].class);
    }
}
