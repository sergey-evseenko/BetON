package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Ct {
    @Expose
    int id;
    @Expose
    String nm;
    @Expose
    String snm;
}

