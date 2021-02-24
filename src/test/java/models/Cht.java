package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Cht {
    @Expose
    int ti;
    @Expose
    int[] chi;
}
