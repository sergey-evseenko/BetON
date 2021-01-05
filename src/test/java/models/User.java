package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    @Expose
    String email;
    @Expose
    String partnerTrackingCode;
    @Expose
    String password;
    @Expose
    String phone;
    @Expose
    String repeatedEmail;
    @Expose
    String repeatedPassword;
    @Expose
    TermsAndConditionDto termsAndConditionDto;
    @Expose
    UserProfileDto userProfileDto;
    @SerializedName("username")
    @Expose
    String userName;

}
