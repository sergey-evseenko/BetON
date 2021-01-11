package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DefaultCountry {
    @Expose
    String countryCode;
    @Expose
    String country;
    @Expose
    int playerAge;
    @Expose
    String phoneCode;
}
