package hackathon.polata.getir.flow.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;

import com.devspark.appmsg.AppMsg;

import hackathon.polata.getir.core.BaseActivity;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.flow.product.ProductsActivity;
import hackathon.polata.getir.network.CustomCallback;
import hackathon.polata.getir.network.GetirServiceProvider;
import hackathon.polata.getir.network.model.AccessToken;
import hackathon.polata.getir.network.model.ApiResponse;
import hackathon.polata.getir.network.model.CustomError;
import hackathon.polata.getir.network.model.User;
import hackathon.polata.getir.util.PrefUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        showProgressDialog();
        //MockGenerator generator = new MockGenerator(this);
        //generator.login(new User(username, password));
        //startActivity(ProductsActivity.newIntent(LoginActivity.this, true));
        GetirServiceProvider.getService().login(new User(username, password)).enqueue(new CustomCallback<
                ApiResponse<AccessToken>>() {
            @Override
            public void onResponse(ApiResponse<AccessToken> response) {
                hideProgressDialog();
                PrefUtil.putString(LoginActivity.this, KEY_TOKEN, response.getData().getAccessToken());
                startActivity(ProductsActivity.newIntent(LoginActivity.this, true));
            }

            @Override
            public void onFailure(CustomError error) {
                hideProgressDialog();
                AppMsg.makeText(LoginActivity.this, error.getErrorMessage(), AppMsg.STYLE_ALERT)
                        .setLayoutGravity(Gravity.BOTTOM).show();
            }
        });
    }
}
