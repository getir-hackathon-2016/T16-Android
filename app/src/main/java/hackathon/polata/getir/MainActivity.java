package hackathon.polata.getir;

import hackathon.polata.getir.core.BaseActivity;
import hackathon.polata.getir.core.BaseFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected BaseFragment getContainedFragment() {
        return new LoginFragment();
    }
}
