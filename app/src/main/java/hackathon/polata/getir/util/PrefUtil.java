package hackathon.polata.getir.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by polata on 20/02/2016.
 */

public final class PrefUtil {

    private static final String NAME_SHARED_PREFERENCES_FILE = "getirPreferences";
    private static SharedPreferences sharedPreferences;

    /**
     * Private default constructor.
     */
    private PrefUtil() {
    }

    private static void init(Context context) {
        if (sharedPreferences != null) {
            return;
        }
        sharedPreferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public static int getInt(Context context, final String key, int defaultValue) {
        init(context);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        init(context);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static String getString(Context context, final String key, final String defaultValue) {
        init(context);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void putString(Context context, String key, String value) {
        init(context);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, final boolean defaultValue) {
        init(context);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        init(context);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void remove(Context context, String key) {
        init(context);
        sharedPreferences.edit().remove(key).apply();
    }
}
