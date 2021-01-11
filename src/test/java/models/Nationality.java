package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Nationality {
    @Expose
    int id;
    @Expose
    String value;
}
