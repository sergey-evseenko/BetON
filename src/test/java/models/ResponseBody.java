package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseBody {
    @SerializedName("access_token")
    @Expose
    String accessToken;
    @SerializedName("refresh_token")
    @Expose
    String refreshToken;
    @Expose
    String email;
    @Expose
    String type;
    @Expose
    String code;
    @Expose
    String description;
    @Expose
    String error;
    @SerializedName("error_description")
    @Expose
    String errorDescription;
    @Expose
    String field;
}
