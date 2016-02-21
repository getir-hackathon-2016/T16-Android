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

    /**
     * Initialize the pref util.
     *
     * @param context context
     */
    private static void init(Context context) {
        if (sharedPreferences != null) {
            return;
        }
        sharedPreferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    /**
     * Get integer value.
     *
     * @param context      context
     * @param key          key
     * @param defaultValue default value
     * @return value
     */
    public static int getInt(Context context, final String key, int defaultValue) {
        init(context);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Put integer value.
     *
     * @param context context
     * @param key     key
     * @param value   value
     */
    public static void putInt(Context context, String key, int value) {
        init(context);
        sharedPreferences.edit().putInt(key, value).apply();
    }

    /**
     * Get string value.
     *
     * @param context      context
     * @param key          key
     * @param defaultValue default value
     * @return value
     */
    public static String getString(Context context, final String key, final String defaultValue) {
        init(context);
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * Put string value.
     *
     * @param context context
     * @param key     key
     * @param value   value
     */
    public static void putString(Context context, String key, String value) {
        init(context);
        sharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * Remove value with key.
     *
     * @param context context
     * @param key     key
     */
    public static void remove(Context context, String key) {
        init(context);
        sharedPreferences.edit().remove(key).apply();
    }
}
