package base.datainteractdemo.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import base.datainteractdemo.IGetCurrentProgress;
import base.datainteractdemo.R;
import base.datainteractdemo.service.BinderInterfaceService;

/**
 * Created by beyond on 18-8-31.
 * 缺点：
 * 同进程中通信，不能进行跨进程通信
 */

public class BinderInterfaceFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser) {
            unbindService();
            cancelTimer();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    void dataChange() {
        super.dataChange();
        if (iGetCurrentProgress != null) {
            position = iGetCurrentProgress.getProgress();
            notifyObservers();
        }
    }

    private synchronized void unbindService() {
        if (getActivity() == null) return;
        if (isServiceBind) {
            isServiceBind = false;
            getActivity().unbindService(serviceConnection);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService();
    }

    private IGetCurrentProgress iGetCurrentProgress;
    private ServiceConnection serviceConnection;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnStart) {
            final Intent intent = new Intent(getContext(), BinderInterfaceService.class);
            if (getActivity() != null) {
                getActivity().bindService(intent, serviceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        isServiceBind = true;
                        iGetCurrentProgress = (IGetCurrentProgress) service;
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        isServiceBind = false;
                        iGetCurrentProgress = null;
                    }
                }, Context.BIND_AUTO_CREATE);
                initTimer();
            }
        }
    }
}