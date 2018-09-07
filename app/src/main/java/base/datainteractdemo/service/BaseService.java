package base.datainteractdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import base.datainteractdemo.Constants;
import base.datainteractdemo.logger.Log;

/**
 * Created by beyond on 18-9-4.
 */

public class BaseService extends Service {
    protected int progress = 0;
    private Timer timer;

    @Override
    public void onCreate() {
        Log.d(Constants.TAG_V1, this.getClass().getSimpleName() + " service OnCreate.");
        super.onCreate();
    }

    protected void cancelTimer() {
        progress = 0;
        dataChanged();
        if (timer != null) {
            timer.cancel();
        }
    }

    protected void initTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (progress == 100) {
                    progress = 0;
                }

                progress++;
                dataChanged();
            }
        }, 0, 100);
    }

    void dataChanged() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG_V1, this.getClass().getSimpleName() + " service OnDestroy.");
        cancelTimer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Constants.TAG_V1, this.getClass().getSimpleName() + " service onBind.");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(Constants.TAG_V1, this.getClass().getSimpleName() + " service onUnBind.");
        return super.onUnbind(intent);
    }
}
