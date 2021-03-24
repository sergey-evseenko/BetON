package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Wager {
    @Expose
    int wager;
    @Expose
    String wagerType;
}
