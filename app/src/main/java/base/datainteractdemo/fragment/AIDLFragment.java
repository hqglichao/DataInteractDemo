package base.datainteractdemo.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import base.datainteractdemo.Constants;
import base.datainteractdemo.IRemoteService;
import base.datainteractdemo.IRemoteServiceCallback;
import base.datainteractdemo.IRemoteToClient;
import base.datainteractdemo.R;
import base.datainteractdemo.logger.Log;
import base.datainteractdemo.service.AIDLService;

/**
 * Created by beyond on 18-8-31.
 * 两种方式的数据交互
 * 1、通过保存的服务端引用，主动调用服务端方法
 * 2、通过向服务端注册回调接口，被动接收服务端传回数据
 */

public class AIDLFragment extends BaseFragment {
    private IRemoteService iRemoteService = null;
    private IRemoteServiceCallback mCallback = null;
    private IRemoteToClient iRemoteToClient = null;

    class RemoteToClientStub extends IRemoteToClient.Stub {
        @Override
        public void setProgress(int progress) throws RemoteException {
            //第二种方式：被动接收服务器传回数据
            position = progress;
            notifyObservers();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser) {
            cancelTimer();
            unbindService();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    void dataChange() {
        super.dataChange();
        //第一种方式：主动向服务器取数据
//        try {
//            if (iRemoteService != null) {
//                position = iRemoteService.getProgress();
//                notifyObservers();
//            }
//        } catch (RemoteException e) {
//            Log.d(Constants.TAG_V1, e.toString());
//        }

    }

    private void unbindService() {
        if (isServiceBind && getActivity() != null) {
            isServiceBind = false;
            try {
                mCallback.unregisterCallback(iRemoteToClient);
            } catch (RemoteException e) {
                Log.d(Constants.TAG_V1, e.toString());
            }
            getActivity().unbindService(aidlServiceConnection);
            getActivity().unbindService(aidlBackServiceConnection);
        }
    }

    private void bindService() {
        if (!isServiceBind && getActivity() != null) {
            aidlServiceConnection = new AIDLServiceConnection();
            aidlBackServiceConnection = new AIDLBackServiceConnection();
            Intent intent = new Intent(getContext(), AIDLService    .class);
            intent.setAction(IRemoteService.class.getSimpleName());
            getActivity().bindService(intent, aidlServiceConnection, Context.BIND_AUTO_CREATE);
            intent.setAction(IRemoteServiceCallback.class.getSimpleName());
            getActivity().bindService(intent, aidlBackServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnStart) {
            bindService();
            initTimer();
        }
    }

    private AIDLServiceConnection aidlServiceConnection;
    private AIDLBackServiceConnection aidlBackServiceConnection;

    class AIDLServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //主动调用服务端方法获取数据
            iRemoteService = IRemoteService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iRemoteService = null;
        }
    }

    class AIDLBackServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isServiceBind = true;
            mCallback = IRemoteServiceCallback.Stub.asInterface(service);
            try {
                iRemoteToClient = new RemoteToClientStub();
                //向服务器注册接口
                mCallback.registerCallback(iRemoteToClient);
            } catch (RemoteException e) {
                Log.d(Constants.TAG_V1, e.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCallback = null;
        }
    }
}
