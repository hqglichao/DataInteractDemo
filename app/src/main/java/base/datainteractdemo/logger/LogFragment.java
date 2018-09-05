package base.datainteractdemo.logger;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by beyond on 18-8-16.
 */

public class LogFragment extends Fragment {
    private LogView logView;
    private ScrollView scrollView;
    private final int textPadding = 10;

    private View inflateView() {
        scrollView = new ScrollView(getActivity());
        ViewGroup.LayoutParams scrollLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrollLayoutParams);
        logView = new LogView(getActivity());
        double scale = getResources().getDisplayMetrics().density;
        int padding = (int)((textPadding * scale) + 0.5);
        logView.setPadding(padding, padding, padding, padding);
        scrollView.addView(logView);
        return scrollView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflateView();
        logView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        return view;
    }

    public LogView getLogView() {
        return logView;
    }
}
