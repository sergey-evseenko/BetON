package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TermsAndConditionDto {
    @Expose
    Boolean accept;

    public TermsAndConditionDto() {
    }
}
