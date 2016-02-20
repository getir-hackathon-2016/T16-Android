package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by polata on 20/02/2016.
 */
@Parcel
public class BaseResponse {

    @SerializedName("code")
    int errorCode;

    @SerializedName("text")
    String message;

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
