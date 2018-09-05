// IRemoteServiceCallback.aidl
package base.datainteractdemo;
import base.datainteractdemo.IRemoteToClient;
// Declare any non-default types here with import statements

interface IRemoteServiceCallback {
    void registerCallback(IRemoteToClient iRemoteToClient);
    void unregisterCallback(IRemoteToClient iRemoteToClient);
}
