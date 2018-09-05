package base.datainteractdemo.fragment;

import android.view.View;

import base.datainteractdemo.PreferenceUtils;
import base.datainteractdemo.R;
import base.datainteractdemo.service.SharePreferenceService;

/**
 * Created by beyond on 18-8-31.
 * 缺点：
 * 经过了一个中转站，这种操作将更耗时
 */

public class SharePreferenceFragment extends BaseFragment {
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser) {
            stopService(SharePreferenceService.class);
            cancelTimer();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    void dataChange() {
        super.dataChange();
        position = PreferenceUtils.getProgress();
        notifyObservers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(SharePreferenceService.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnStart) {
            startService(SharePreferenceService.class);
            initTimer();
        }
    }
}
