package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Tournament {
    @Expose
    int tournamentPriorityId;
    @Expose
    int sortIndex;
    @Expose
    int id;
    @Expose
    Ct ct;
    @Expose
    Boolean hl;
    @Expose
    String nm;
    @Expose
    String snm;
    @Expose
    int tr;
    @Expose
    int cr;
    @Expose
    String tgn;
    @Expose
    Boolean cup;
    @Expose
    int srid;
    @Expose
    Cht cht;
}
