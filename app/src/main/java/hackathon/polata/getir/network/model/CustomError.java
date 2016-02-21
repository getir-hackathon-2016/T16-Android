package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by polata on 20/02/2016.
 */
public class CustomError {

    @SerializedName("code")
    private int errorType;

    @SerializedName("text")
    private String errorMessage;

    /**
     * Constructor.
     *
     * @param errorType error type
     */
    public CustomError(ErrorType errorType) {
        this.errorType = errorType.ordinal();
        errorMessage = errorType.toString();
    }

    /**
     * Constructor.
     *
     * @param errorMessage error message
     * @param errorType    error type
     */
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
