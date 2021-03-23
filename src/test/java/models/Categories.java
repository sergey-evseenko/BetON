package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Categories {
    @Expose
    Category[] categories;
    @SerializedName("market_layers")
    @Expose
    MarketLayer[] marketLayers;
}
