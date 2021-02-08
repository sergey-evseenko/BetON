package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Email {
    @Expose
    String email;
    @Expose
    String password;
    @Expose
    String repeatedEmail;
}
