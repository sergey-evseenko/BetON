package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class OutrightCategory {
    @Expose
    Tournament tournament;
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
    String sn;
    @Expose
    int sbid;
    @Expose
    int cid;
    @Expose
    String cn;
    @Expose
    int cbid;
    @Expose
    String cf;
    @Expose
    int tid;
    @Expose
    String tn;
    @Expose
    int tbid;
    @Expose
    int oc;
    @Expose
    String sd;
    @Expose
    int sni;
}
