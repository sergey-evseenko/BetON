package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationDataAll {
    @Expose
    RegistrationData de;
    @Expose
    RegistrationData ru;
    @Expose
    RegistrationData en;
    @Expose
    RegistrationData cn;
    @Expose
    RegistrationData fr;
    @Expose
    RegistrationData es;
    @Expose
    RegistrationData nl;
    @Expose
    RegistrationData tr;
}
