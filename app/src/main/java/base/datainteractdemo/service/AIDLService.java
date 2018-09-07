package base.datainteractdemo.service;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import base.datainteractdemo.Constants;
import base.datainteractdemo.IRemoteService;
import base.datainteractdemo.IRemoteServiceCallback;
import base.datainteractdemo.IRemoteToClient;
import base.datainteractdemo.logger.Log;

/**
 * Created by beyond on 18-9-5.
 */

public class AIDLService extends BaseService {
    private List<IRemoteToClient> remoteToClients = new ArrayList<>();

    class ProgressDataStub extends IRemoteService.Stub {
        @Override
        public int getProgress() throws RemoteException {
            return progress;
        }
    }

    class RemoteServiceCallbackStub extends IRemoteServiceCallback.Stub {
        @Override
        public void registerCallback(IRemoteToClient iRemoteToClient) throws RemoteException {
            if (iRemoteToClient == null) throw new NullPointerException();
            if (!remoteToClients.contains(iRemoteToClient)) {
                remoteToClients.add(iRemoteToClient);
            }
        }

        @Override
        public void unregisterCallback(IRemoteToClient iRemoteToClient) throws RemoteException {
            remoteToClients.remove(iRemoteToClient);
        }
    }

    @Override
    void dataChanged() {
        for (IRemoteToClient iRemoteToClient : remoteToClients) {
            try {
                iRemoteToClient.setProgress(progress);
            } catch (RemoteException e) {
                Log.d(Constants.TAG_V1, e.toString());
            }
        }
        super.dataChanged();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        progress = 0;
        cancelTimer();
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        initTimer();
        if (IRemoteService.class.getSimpleName().equals(intent.getAction())) {
            return new ProgressDataStub();
        } else if (IRemoteServiceCallback.class.getSimpleName().equals(intent.getAction())){
            return new RemoteServiceCallbackStub();
        }
        return null;
    }
}
