package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SecurityOption {
    @Expose
    String secretAnswer;
    @Expose
    int secretQuestionId;
}
