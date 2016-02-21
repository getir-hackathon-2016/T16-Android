package hackathon.polata.getir.network;

import hackathon.polata.getir.network.model.ApiResponse;
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
        ApiResponse res = (ApiResponse) response.body();
        if (response.code() == 200) {
            if (res.getError().getErrorType().ordinal() == ErrorType.SUCCESS.ordinal()) {
                onResponse((T)res);
            }
        } else {
            onFailure(new CustomError(res.getError().getErrorMessage(), res.getError().getErrorType()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(new CustomError(ErrorType.GENERIC_ERROR));
    }
}
