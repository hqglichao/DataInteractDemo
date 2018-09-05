package base.datainteractdemo.service;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import base.datainteractdemo.Constants;
import base.datainteractdemo.logger.Log;

/**
 * Created by beyond on 18-9-5.
 */

public class MessengerService extends BaseService {
    private List<Messenger> mClient = new ArrayList<>();
    private final Messenger remoteMessenger = new Messenger(new RemoteHandler());

    class RemoteHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_REGISTER_CLIENT:
                    if (!mClient.contains(msg.replyTo)) {
                        mClient.add(msg.replyTo);
                    }
                    break;
                case Constants.MSG_UNREGISTER_CLIENT:
                    mClient.remove(msg.replyTo);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    void dataChanged() {
        for (Messenger messenger : mClient) {
            try {
                Message msg = Message.obtain(null, Constants.MSG_PROGRESS);
                msg.arg1 = progress;
                messenger.send(msg);
            } catch (RemoteException e) {
                Log.d(Constants.TAG_V1, e.toString());
            }
        }
        super.dataChanged();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        cancelTimer();
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        initTimer();
        return remoteMessenger.getBinder();
    }
}
