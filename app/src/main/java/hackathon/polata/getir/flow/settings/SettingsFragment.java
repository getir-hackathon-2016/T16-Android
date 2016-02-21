package hackathon.polata.getir.flow.settings;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

import hackathon.polata.getir.R;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.util.LanguageUtil;

/**
 * Created by polata on 21/02/2016.
 */
public class SettingsFragment extends BaseFragment<SettingsController> implements RadioGroup.OnCheckedChangeListener {

    @Override
    protected int getResourceLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initUserInterface(LayoutInflater layoutInflater, View view) {
        final int selectedId = (TextUtils.equals(LanguageUtil.getLanguage(),
                LanguageUtil.TURKISH.getLanguage()))
                ? R.id.settings_radiobutton_turkish : R.id.settings_radiobutton_english;

        final RadioButton radioButton = ((RadioButton) view.findViewById(selectedId));
        radioButton.setChecked(true);

        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.settings_radiogroup_language);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.settings_radiobutton_turkish:
                changeLanguage(getActivity(), LanguageUtil.TURKISH);
                break;
            case R.id.settings_radiobutton_english:
                changeLanguage(getActivity(), LanguageUtil.ENGLISH);
                break;
            default:
                break;
        }
    }

    private void changeLanguage(Context context, Locale language) {
        LanguageUtil.changeLanguage(context, language);
        controller.onLanguageChanged();
    }
}
