package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResendEmailData {
    @Expose
    String email;
    @Expose
    String externalId;
    @Expose
    String repeatedEmail;
}

