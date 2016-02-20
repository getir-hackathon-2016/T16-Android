package hackathon.polata.getir;

import hackathon.polata.getir.core.BaseController;

/**
 * Created by polata on 20/02/2016.
 */
public interface LoginController extends BaseController {

    /**
     * Callback when login button is clicked.
     *
     * @param username username
     * @param password password
     */
    void onClickLogin(String username, String password);
}
