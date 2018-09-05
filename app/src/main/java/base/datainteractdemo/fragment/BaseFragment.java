package base.datainteractdemo.fragment;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import base.datainteractdemo.Observable;
import base.datainteractdemo.Observer;
import base.datainteractdemo.R;
import base.datainteractdemo.service.BroadcastService;
import base.datainteractdemo.service.SharePreferenceService;

/**
 * Created by beyond on 18-8-31.
 */

public class BaseFragment extends Fragment implements View.OnClickListener, Observable{
    public static final String PAGE_TITLE = "page_title";
    protected int position = 0;
    protected boolean isServiceStarted = false;
    protected boolean isServiceBind = false;

    private Timer timer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof Observer) addObserver((Observer) getActivity());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString(PAGE_TITLE);
            TextView tvTitle = view.findViewById(R.id.tvFragmentTitle);
            tvTitle.setText(title);
            Button btnStart = view.findViewById(R.id.btnStart);
            btnStart.setOnClickListener(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        position = 0;
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() instanceof Observer) {
            addObserver((Observer) getActivity());
            notifyObservers();
        } else {
            deleteObserver();
        }
    }

    void initTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dataChange();
            }
        }, 0, 100);
    }

    void dataChange() {

    }


    protected synchronized <T> void startService(Class<T> service) {
        if (getActivity() != null) {
            isServiceStarted = true;
            getActivity().startService(new Intent(getContext(), service));
        }
    }

    protected synchronized <T> void stopService(Class<T> service) {
        if (isServiceStarted && getActivity() != null) {
            isServiceStarted = false;
            getActivity().stopService(new Intent(getContext(), service));
        }
    }

    void cancelTimer() {
        if (timer != null) timer.cancel();
    }

    //Todo 这里的观察者模式用的并不好，但是动态的赋予SeekBar只观察一个来源数据，待优化
    private List<Observer> observers = new ArrayList<>();
    @Override
    public synchronized void addObserver(Observer o) {
        if (o == null) throw new NullPointerException();
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void deleteObserver() {
        observers.clear();
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update(position);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
