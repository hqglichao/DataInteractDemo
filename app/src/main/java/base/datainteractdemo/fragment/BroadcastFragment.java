package base.datainteractdemo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import base.datainteractdemo.Constants;
import base.datainteractdemo.R;
import base.datainteractdemo.service.BroadcastService;

/**
 * Created by beyond on 18-8-31.
 * 缺点：
 * 在广播接收器中不能处理长耗时操作，否则系统会出现ANR即应用程序无响应。
 */

public class BroadcastFragment extends BaseFragment {
    private BroadcastReceiver broadcastReceiver;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                position = intent.getIntExtra("position", 0);
                notifyObservers();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_FILTER);
        if (getActivity() != null) getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser) {
            stopService(BroadcastService.class);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(BroadcastService.class);
        if (getActivity() != null) getActivity().unregisterReceiver(broadcastReceiver);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnStart) {
            startService(BroadcastService.class);
        }
    }
}
