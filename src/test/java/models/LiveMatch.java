package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LiveMatch {
    @SerializedName("eventBetradarId")
    @Expose
    String eventId;
    @Expose
    String langIso;
    @Expose
    String[] markets;
}
