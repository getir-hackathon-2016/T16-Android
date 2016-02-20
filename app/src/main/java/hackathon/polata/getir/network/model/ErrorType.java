package hackathon.polata.getir.network.model;

/**
 * Created by polata on 20/02/2016.
 */

public enum ErrorType {

    SUCCESS(0),
    GENERIC_ERROR(-1);

    private int type;

    /**
     * Constructor.
     *
     * @param type error type
     */
    ErrorType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    /**
     * Get error type enum from given type.
     *
     * @param type type
     * @return error type
     */
    public static ErrorType fromType(int type) {

        for (ErrorType errorType : ErrorType.values()) {
            if (errorType.getType() == type) {
                return errorType;
            }
        }

        return GENERIC_ERROR;
    }
}
