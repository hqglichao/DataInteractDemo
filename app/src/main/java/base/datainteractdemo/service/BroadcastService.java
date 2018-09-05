package base.datainteractdemo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import base.datainteractdemo.Constants;
import base.datainteractdemo.IGetCurrentProgress;
import base.datainteractdemo.fragment.BaseFragment;

/**
 * Created by beyond on 18-8-15.
 */

public class BroadcastService extends BaseService {
    @Override
    public void onCreate() {
        super.onCreate();
        initTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    @Override
    void dataChanged() {
        super.dataChanged();
        final Intent intent = new Intent(Constants.BROADCAST_FILTER);
        intent.putExtra("position", progress);
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
