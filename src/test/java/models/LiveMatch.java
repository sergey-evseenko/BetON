package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LiveMatch {
    @Expose
    int eventBetradarId;
    @Expose
    String langIso;
    @Expose
    String[] markets;
}
