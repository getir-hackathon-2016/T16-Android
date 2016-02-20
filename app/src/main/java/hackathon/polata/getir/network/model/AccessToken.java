package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by polata on 20/02/2016.
 */
public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
