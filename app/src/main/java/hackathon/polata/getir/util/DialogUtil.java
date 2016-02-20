package hackathon.polata.getir.util;

import android.content.Context;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;

import hackathon.polata.getir.R;

/**
 * Created by polata on 20/02/2016.
 */
public final class DialogUtil {
    private static MaterialDialog progressDialog;

    /**
     * Default constructor disabled.
     */
    private DialogUtil() {
    }

    /**
     * Show progress dialog.
     *
     * @param context context
     */
    public static void showProgressDialog(Context context) {
        showProgressDialog(context, R.string.please_wait);
    }

    /**
     * Show progress dialog with a custom message.
     *
     * @param context    context
     * @param resourceId string resource of message
     */
    public static void showProgressDialog(Context context, @StringRes int resourceId) {
        showProgressDialog(context, context.getString(resourceId));
    }

    /**
     * Show progress dialog with a custom message.
     *
     * @param context context
     * @param message message
     */
    public static void showProgressDialog(Context context, String message) {
        if (progressDialog != null) {
            progressDialog.setContent(message);
            return;
        }
        progressDialog
                = new MaterialDialog.Builder(context)
                .content(message)
                .cancelable(false)
                .progress(true, 0)
                .show();
    }

    /**
     * Hide progress dialog.
     */
    public static void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }
}

