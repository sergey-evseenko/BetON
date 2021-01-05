package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Address {
    @Expose
    String city;
    @Expose
    String countryCode;
    @Expose
    int homeNumber;
    @Expose
    int postalCode;
    @Expose
    String street;
}
