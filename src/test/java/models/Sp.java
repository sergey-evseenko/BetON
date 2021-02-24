package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Sp {
    @Expose
    int id;
    @Expose
    Boolean ac;
    @Expose
    int si;
    @Expose
    int bId;
    String nm;
    @Expose
    String snm;
}
