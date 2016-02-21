package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by polata on 20/02/2016.
 */
@Parcel
public class AuthenticatedUser {
    @SerializedName("id")
    String id;

    @SerializedName("email")
    String email;

    /**
     * Private constructor disabled.
     */
    private AuthenticatedUser() {
    }

    /**
     * Constructor.
     *
     * @param id    id
     * @param email email
     */
    public AuthenticatedUser(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
