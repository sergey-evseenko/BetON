package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserProfileDto {
    @Expose
    Address[] addresses;
    @Expose
    String birthDate;
    @Expose
    Boolean dataTransferToBp;
    @Expose
    Boolean dataTransferToCashOn;
    @Expose
    String name;
    @Expose
    String surname;
    @Expose
    int nationalityId;
    @Expose
    String passportNumber;
    @Expose
    String phoneCode;
    @Expose
    int title;

    public UserProfileDto() {
    }
}
