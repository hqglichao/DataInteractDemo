package base.datainteractdemo.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import base.datainteractdemo.Constants;
import base.datainteractdemo.R;
import base.datainteractdemo.logger.Log;
import base.datainteractdemo.service.MessengerService;

/**
 * Created by beyond on 18-8-31.
 */

public class MessengerFragment extends BaseFragment {
    private MessengerConnection mServiceConnection = new MessengerConnection();
    private Messenger mService = null;
    private final Messenger mClient = new Messenger(new ClientHandler());

    class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_PROGRESS:
                    position = msg.arg1;
                    notifyObservers();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser) {
            unBindService();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unBindService();
    }

    private void bindService() {
        if (getActivity() != null && !isServiceBind) {
            isServiceBind = true;
            getActivity().bindService(new Intent(getContext(), MessengerService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void unBindService() {
        if (isServiceBind && getActivity() != null) {
            isServiceBind = false;
            try {
                Message msg = Message.obtain(null, Constants.MSG_UNREGISTER_CLIENT);
                msg.replyTo = mClient;
                mService.send(msg);
            } catch (RemoteException e) {
                Log.d(Constants.TAG_V1, e.toString());
            }
            getActivity().unbindService(mServiceConnection);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnStart) {
            bindService();
        }
    }

    class MessengerConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                mService = new Messenger(service);
                Message msg = Message.obtain(null, Constants.MSG_REGISTER_CLIENT);
                msg.replyTo = mClient;
                mService.send(msg);
            } catch (RemoteException e) {
                Log.d(Constants.TAG_V1, e.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    }
}
