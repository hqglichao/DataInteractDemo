package base.datainteractdemo.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import base.datainteractdemo.IGetCurrentProgress;

/**
 * Created by beyond on 18-9-4.
 */

public class BinderInterfaceService extends BaseService {
    @Override
    public boolean onUnbind(Intent intent) {
        cancelTimer();
        progress = 0;
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        initTimer();
        return new MyBinder();
    }

    private class MyBinder extends Binder implements IGetCurrentProgress {
        @Override
        public int getProgress() {
            return progress;
        }
    }
}