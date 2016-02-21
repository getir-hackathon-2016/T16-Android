package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by polata on 21/02/2016.
 */
public class ApiResponse<R> {

    @SerializedName("error")
    CustomError error;

    @SerializedName("data")
    R data;

    public R getData() {
        return data;
    }

    public CustomError getError() {
        return error;
    }
}
