package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ValidateField {
    @Expose
    String fieldType;
    @Expose
    String fieldValue;
}
