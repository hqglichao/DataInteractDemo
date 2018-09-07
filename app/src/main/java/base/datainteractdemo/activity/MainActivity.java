package base.datainteractdemo.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.SeekBar;

import base.datainteractdemo.Constants;
import base.datainteractdemo.Observer;
import base.datainteractdemo.R;
import base.datainteractdemo.SlidingTabsColorsFragment;
import base.datainteractdemo.logger.Log;
import base.datainteractdemo.logger.LogFragment;
import base.datainteractdemo.logger.LogWrapper;
import base.datainteractdemo.logger.MessageOnlyLogFilter;

/**
 * Created by beyond on 18-8-15.
 */

public class MainActivity extends FragmentActivity implements Observer{
    private SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_data_interact_activiy);
        initView();
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.tab_fragment, new SlidingTabsColorsFragment()).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLog();
    }

    @Override
    public void update(final int position) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    update(position);
                }
            });
            return;
        }
        seekBar.setProgress(position);
    }

    private void initView() {
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(Constants.SEEK_BAR_MAX);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initLog() {
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);

        MessageOnlyLogFilter messageOnlyLogFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(messageOnlyLogFilter);

        LogFragment logFragment = (LogFragment) getSupportFragmentManager().findFragmentById(R.id.log_fragment);
        messageOnlyLogFilter.setNext(logFragment.getLogView());
        Log.i(Constants.TAG_V1, "Ready.");
    }
}
