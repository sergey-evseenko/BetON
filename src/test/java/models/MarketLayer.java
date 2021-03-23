package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class MarketLayer {
    @Expose
    int id;
    @Expose
    String ly;
    @Expose
    int mci;
}
