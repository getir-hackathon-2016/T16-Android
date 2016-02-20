package hackathon.polata.getir.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by polata on 20/02/2016.
 */
public final class GetirServiceGenerator {

    public static final String API_BASE_URL = "http://qeremy.com:3000";

    /**
     * Default constructor disabled.
     */
    private GetirServiceGenerator() {
    }

    /**
     * Create retrofit service instance.
     *
     * @param serviceClass service class parameter
     * @param <S>          Service Class type
     * @return retrofit service instance
     */
    public static <S> S createService(Class<S> serviceClass) {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        return retrofit.create(serviceClass);
    }
}
