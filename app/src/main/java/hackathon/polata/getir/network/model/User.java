package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by polata on 20/02/2016.
 */
@Parcel
public class User {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    /**
     * Default constructor disabled.
     */
    private User() {
    }

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
