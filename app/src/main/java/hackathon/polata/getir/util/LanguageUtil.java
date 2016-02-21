package hackathon.polata.getir.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

/**
 * Created by polata on 21/02/2016.
 */
public final class LanguageUtil {
    public static final Locale ENGLISH = Locale.ENGLISH;
    public static final Locale TURKISH = new Locale("tr", "");

    private LanguageUtil() {

    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static void changeLanguage(Context context, Locale language) {
        Locale.setDefault(language);
        final Resources resources = context.getResources();
        final Configuration config = resources.getConfiguration();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(language);
        } else {
            config.locale = language;
        }

        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}

