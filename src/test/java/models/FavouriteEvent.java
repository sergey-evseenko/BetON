package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FavouriteEvent {
    @Expose
    String eventId;
    @Expose
    String userId;
}
