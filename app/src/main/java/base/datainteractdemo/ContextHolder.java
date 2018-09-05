package base.datainteractdemo;

import android.content.Context;

/**
 * Created by beyond on 18-9-4.
 */

public class ContextHolder {
    private static Context appContext;
    public static void setContext (Context context) {
        appContext = context;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
