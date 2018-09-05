package base.datainteractdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

/**
 * Created by beyond on 18-9-4.
 */

public class PreferenceUtils {
    private static final String TAG = "PreferenceUtils";
    private static final SharedPreferences preferences = ContextHolder.getAppContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);

    public static void setProgress(int progress) {
        if (preferences != null) {
            preferences.edit().putInt("progress", progress).apply();
        }
    }

    public static int getProgress() {
        if (preferences != null) {
            return preferences.getInt("progress", 0);
        }
        return 0;
    }
}
