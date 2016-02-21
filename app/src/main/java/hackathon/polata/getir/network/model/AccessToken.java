package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by polata on 20/02/2016.
 */
@Parcel
public class AccessToken {

    @SerializedName("access_token")
    String accessToken;

    /**
     * Private constructor disabled.
     */
    private AccessToken() {
    }

    /**
     * Constructor.
     *
     * @param accessToken access token
     */
    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
