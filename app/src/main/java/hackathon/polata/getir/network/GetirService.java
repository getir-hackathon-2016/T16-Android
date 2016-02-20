package hackathon.polata.getir.network;

import hackathon.polata.getir.network.model.AccessToken;
import hackathon.polata.getir.network.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by polata on 20/02/2016.
 */
public interface GetirService {

    @POST("/login")
    Call<AccessToken> login(@Body User user);
}