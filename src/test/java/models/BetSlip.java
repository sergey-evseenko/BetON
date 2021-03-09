package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class BetSlip {
    @Expose
    int bmi;
    @Expose
    String ei;
    @Expose
    String lc;
    @Expose
    String mi;
    @Expose
    int oi;
}
