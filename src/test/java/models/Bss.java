package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Bss {
    @Expose
    String bssn;
    @Expose
    int num;
    @Expose
    int den;
    @Expose
    int bnk;
}
