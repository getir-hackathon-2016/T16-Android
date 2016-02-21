package hackathon.polata.getir.network;

import hackathon.polata.getir.network.model.AccessToken;
import hackathon.polata.getir.network.model.ApiResponse;
import hackathon.polata.getir.network.model.AuthenticatedUser;
import hackathon.polata.getir.network.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by polata on 20/02/2016.
 */
public interface GetirService {

    @POST("/login")
    Call<ApiResponse<AccessToken>> login(@Body User user);

    @GET("/init")
    Call<ApiResponse<AuthenticatedUser>> init(@Header("X-Access-Token") String accessToken);
}