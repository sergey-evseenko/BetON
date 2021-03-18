package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Combination {
    @Expose
    Bss bss;
    @Expose
    Boolean sel;
    @Expose
    int nbet;
}
