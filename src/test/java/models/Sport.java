package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Sport {
    @Expose
    int id;
    @Expose
    boolean ac;
    @Expose
    int si;
    @Expose
    int bId;
    @Expose
    String nm;
    @Expose
    String snm;
}
