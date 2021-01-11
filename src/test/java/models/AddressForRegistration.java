package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddressForRegistration {
    @Expose
    int id;
    @Expose
    String countryCode;
    @Expose
    String state;
    @Expose
    String district;
    @Expose
    String postcode;
    @Expose
    String city;
    @Expose
    String street;
}
