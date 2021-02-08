package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Password {
    @Expose
    String newPassword;
    @Expose
    String password;
    @Expose
    String repeatNewPassword;
}
