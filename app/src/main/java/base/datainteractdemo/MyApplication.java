package base.datainteractdemo;

import android.app.Application;

/**
 * Created by beyond on 18-9-4.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(this);
    }
}
