package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class OutrightTournament {
    @Expose
    int id;
    @Expose
    int bid;
    @Expose
    String st;
    @Expose
    Boolean act;
    @Expose
    int sid;
    @Expose
    int sbid;
    @Expose
    int cid;
    @Expose
    int cbid;
    @Expose
    String cf;
    @Expose
    int tid;
    @Expose
    int tbid;
    @Expose
    String on;
    @Expose
    int cc;
}
