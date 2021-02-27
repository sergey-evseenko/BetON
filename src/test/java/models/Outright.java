package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Outright {
    @Expose
    int id;
    @SerializedName("obid")
    @Expose
    int obId;
    @Expose
    String cn;
    @Expose
    int cbi;
    @Expose
    Float vl;
    @Expose
    String st;
    @Expose
    Boolean act;
    @Expose
    String mts;
    @Expose
    String sp;
}
