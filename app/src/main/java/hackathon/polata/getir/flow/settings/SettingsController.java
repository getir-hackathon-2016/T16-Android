package hackathon.polata.getir.flow.settings;

import hackathon.polata.getir.core.BaseController;

/**
 * Created by polata on 21/02/2016.
 */
public interface SettingsController extends BaseController {

    /**
     * Callback when a language is changed.
     */
    void onLanguageChanged();
}
