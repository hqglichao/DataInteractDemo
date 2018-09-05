package base.datainteractdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import static base.datainteractdemo.Constants.TAG_V1;

/**
 * Created by beyond on 18-8-13.
 */

public class ManifestBroadcast extends BroadcastReceiver {
    int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        String strReceive = "manifest broadcast received. " + i++;
        //每次收到广播都是一个新对象，所以i不会变
        Log.d(TAG_V1, strReceive);
        Toast.makeText(context, strReceive, Toast.LENGTH_SHORT).show();

    }
}
