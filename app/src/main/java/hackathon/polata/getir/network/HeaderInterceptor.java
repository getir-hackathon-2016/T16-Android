package hackathon.polata.getir.network;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by polata on 20/02/2016.
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();

        final String token = Locale.getDefault().getLanguage();

        final Request newRequest = originalRequest.newBuilder()
                .header("Accept-Language", token)
//                .header("Accept", "application/json")
                .header("X-Device-Id", "android")
                .build();

        return chain.proceed(newRequest);
    }
}
