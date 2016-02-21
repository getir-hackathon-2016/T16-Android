package hackathon.polata.getir.flow.login;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;

import butterknife.Bind;
import hackathon.polata.getir.R;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.util.KeyboardUtil;
import hackathon.polata.getir.view.GetirEditText;

/**
 * Created by polata on 20/02/2016.
 */
public class LoginFragment extends BaseFragment<LoginController> implements TextView.OnEditorActionListener {

    @Bind(R.id.fragment_login_edittext_password)
    GetirEditText editTextPassword;

    @Bind(R.id.fragment_login_edittext_username)
    GetirEditText editTextUsername;

    @Override
    protected int getResourceLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initUserInterface(LayoutInflater inflater, View rootView) {
        editTextPassword.setOnEditorActionListener(this);
        editTextUsername.setText("alpercem70@gmail.com");
        editTextPassword.setText("1");
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            KeyboardUtil.hideKeyboard(getActivity());
            if (validateInput()) {
                controller.onClickLogin(editTextUsername.getTextAsString(), editTextPassword.getTextAsString());
            }
        }
        return true;
    }

    /**
     * Validate input fields.
     *
     * @return boolean
     */
    private boolean validateInput() {
        if (TextUtils.isEmpty(editTextPassword.getTextAsString())) {
            AppMsg.makeText(getActivity(), R.string.error_password_empty, AppMsg.STYLE_INFO)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .show();
            return false;
        }
        return true;
    }
}
