package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserForCheck {
    @Expose
    Address address;
    @Expose
    String birthDate;
    @Expose
    String email;
    @Expose
    String name;
    @Expose
    int nationalityId;
    @Expose
    String passportNumber;
    @Expose
    String phone;
    @Expose
    String surname;
    @Expose
    String username;


}
