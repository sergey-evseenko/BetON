package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OptionsList {
    @Expose
    int sportId;
    @Expose
    Boolean autoAccept;
    @Expose
    Boolean wantNewslettersOnEmail;
}
