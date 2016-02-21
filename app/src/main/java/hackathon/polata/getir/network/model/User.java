package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by polata on 20/02/2016.
 */
@Parcel
public class User {

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    /**
     * Private constructor disabled.
     */
    private User() {
    }

    /**
     * Constructor.
     *
     * @param email    email
     * @param password password
     */
    public User(String email, String password) {
        this.password = password;
        this.email = email;
    }
}
