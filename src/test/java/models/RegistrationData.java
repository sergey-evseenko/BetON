package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationData {
    @Expose
    Country[] countries;
    @Expose
    Nationality[] nationalities;
    @Expose
    Title[] titles;
    @Expose
    Question[] questions;
    @Expose
    DefaultCountry defaultCountry;
}
