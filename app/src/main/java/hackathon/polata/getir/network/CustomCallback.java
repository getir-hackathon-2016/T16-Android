package hackathon.polata.getir.network;

import hackathon.polata.getir.network.model.BaseResponse;
import hackathon.polata.getir.network.model.CustomError;
import hackathon.polata.getir.network.model.ErrorType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by polata on 20/02/2016.
 */
public abstract class CustomCallback<T> implements Callback<T> {

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param error error
     */
    public abstract void onFailure(CustomError error);

    /**
     * Invoked for a received HTTP response.
     * An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * CustomCallback class defines the success logic and delegates here only applicable.
     */
    public abstract void onResponse(T response);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() == 200) {

            if (response.body() instanceof BaseResponse) {
                final BaseResponse meta = (BaseResponse) response.body();
                if (meta.getErrorCode() == ErrorType.SUCCESS.ordinal()) {
                    onResponse(response.body());
                    return;
                }

                onFailure(new CustomError(meta.getMessage(), ErrorType.fromType(meta.getErrorCode())));

            } else {
                throw new UnsupportedOperationException("Model classes should implement BaseResponse");
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(new CustomError(ErrorType.GENERIC_ERROR));
    }
}
