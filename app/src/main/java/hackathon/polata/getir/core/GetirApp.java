package hackathon.polata.getir.core;

import android.app.Application;

/**
 * Created by polata on 20/02/2016.
 */
public class GetirApp extends Application {
    private static GetirApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * Return static Application instance.
     *
     * @return app instance
     */
    public static GetirApp getInstance() {
        return instance;
    }
}
