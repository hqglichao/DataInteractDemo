package base.datainteractdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import base.datainteractdemo.Constants;
import base.datainteractdemo.R;
import base.datainteractdemo.logger.Log;

import static base.datainteractdemo.Constants.TAG_V1;

public class MainActivity extends AppCompatActivity {
    private Button btnDataInteract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDataInteract = findViewById(R.id.btnDataInteract);

        btnDataInteract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServiceDataInteractActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * send dynamic register broadcast Constants.BROADCAST_NAME
     * & send manifest static register broadcast Constants.BROADCAST_NAME_PRINT
     */
    private void initBroadcast() {

    }
}
