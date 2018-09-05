package base.datainteractdemo.service;

import java.util.prefs.PreferenceChangeEvent;

import base.datainteractdemo.PreferenceUtils;

/**
 * Created by beyond on 18-9-4.
 */

public class SharePreferenceService extends BaseService {
    @Override
    public void onCreate() {
        super.onCreate();
        initTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    @Override
    void dataChanged() {
        super.dataChanged();
        PreferenceUtils.setProgress(progress);
    }
}
