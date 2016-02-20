package hackathon.polata.getir.flow.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.devspark.appmsg.AppMsg;

import hackathon.polata.getir.core.BaseActivity;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.flow.product.ProductsActivity;
import hackathon.polata.getir.network.CustomCallback;
import hackathon.polata.getir.network.GetirService;
import hackathon.polata.getir.network.GetirServiceGenerator;
import hackathon.polata.getir.network.model.AccessToken;
import hackathon.polata.getir.network.model.CustomError;
import hackathon.polata.getir.network.model.User;
import hackathon.polata.getir.util.PrefUtil;

public class LoginActivity extends BaseActivity implements LoginController {

    private static final String KEY_TOKEN = "keyToken";

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
        showProgressDialog();
        service.login(new User(username, password)).enqueue(new CustomCallback<AccessToken>() {
            @Override
            public void onResponse(AccessToken response) {
                hideProgressDialog();
                PrefUtil.putString(LoginActivity.this, KEY_TOKEN, response.getAccessToken());
                startActivity(ProductsActivity.newIntent(LoginActivity.this, true));
            }

            @Override
            public void onFailure(CustomError error) {
                hideProgressDialog();
                AppMsg.makeText(LoginActivity.this, error.getErrorMessage(), AppMsg.STYLE_INFO);
            }
        });
    }
}
