package adapters;

import models.ResponseBody;
import models.ValidateField;

public class FieldValidationAdapter extends MainAdapter {

    public ResponseBody[] post(ValidateField validateField) {
        body = gson.toJson(validateField);
        response = post("auth/v1/users/field/validate", 200, body);
        return gson.fromJson(response.asString().trim(), ResponseBody[].class);
    }
}
