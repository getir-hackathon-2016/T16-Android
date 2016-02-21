package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by polata on 20/02/2016.
 */
public class CustomError {

    @SerializedName("code")
    private int errorType;

    @SerializedName("text")
    private String errorMessage;

    public CustomError(String errorMessage) {
        this.errorType = ErrorType.GENERIC_ERROR.ordinal();
        this.errorMessage = errorMessage;
    }

    public CustomError(ErrorType errorType) {
        this.errorType =  errorType.ordinal();
        errorMessage = errorType.toString();
    }

    public CustomError(String errorMessage, ErrorType errorType) {
        this.errorMessage = errorMessage;
        this.errorType = errorType.ordinal();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorType getErrorType() {
        return ErrorType.fromType(errorType);
    }
}
