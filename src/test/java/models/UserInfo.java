package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfo {
    @Expose
    String username;
    @Expose
    String email;
    @Expose
    String phone;
    @Expose
    String phoneCode;
    @Expose
    int title;
    @Expose
    String name;
    @Expose
    String surname;
    @Expose
    int nationalityId;
    @Expose
    String birthDate;
    @Expose
    String passportNumber;
    @Expose
    Address[] addresses;
}
