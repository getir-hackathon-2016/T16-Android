package hackathon.polata.getir;

import android.os.Bundle;
import android.support.annotation.Nullable;

import hackathon.polata.getir.core.BaseActivity;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.network.CustomCallback;
import hackathon.polata.getir.network.GetirService;
import hackathon.polata.getir.network.GetirServiceGenerator;
import hackathon.polata.getir.network.model.AccessToken;
import hackathon.polata.getir.network.model.CustomError;
import hackathon.polata.getir.network.model.User;

public class LoginActivity extends BaseActivity implements LoginController {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseFragment getContainedFragment() {
        return new LoginFragment();
    }

    @Override
    protected boolean hasNavigationDrawer() {
        return false;
    }

    @Override
    public void onClickLogin(String username, String password) {
        final GetirService service = GetirServiceGenerator.createService(GetirService.class);
        service.login(new User(username, password)).enqueue(new CustomCallback<AccessToken>() {
            @Override
            public void onFailure(CustomError error) {

            }

            @Override
            public void onResponse(AccessToken response) {
            }
        });
    }

}
