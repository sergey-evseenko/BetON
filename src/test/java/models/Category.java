package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class Category {
    @Expose
    int id;
    @Expose
    int bId;
    @Expose
    Sp sp;
    @Expose
    String nm;
    @Expose
    String snm;
    @Expose
    int tpi;
    @Expose
    int si;
    @Expose
    Fl fl;
}
