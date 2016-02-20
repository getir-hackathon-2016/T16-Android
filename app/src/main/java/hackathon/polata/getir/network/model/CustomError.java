package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by polata on 20/02/2016.
 */
public class CustomError {

    @SerializedName("code")
    private ErrorType errorType;

    @SerializedName("error_message")
    private String errorMessage;

    public CustomError(String errorMessage) {
        this.errorType = ErrorType.GENERIC_ERROR;
        this.errorMessage = errorMessage;
    }

    public CustomError(ErrorType errorType) {
        this.errorType = errorType;
        errorMessage = errorType.toString();
    }

    public CustomError(String errorMessage, ErrorType errorType) {
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }
}
