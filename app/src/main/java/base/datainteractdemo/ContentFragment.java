package base.datainteractdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by beyond on 18-8-30.
 */

public class ContentFragment extends Fragment {
    private String title;

    private static final String PAGE_TITLE = "fragment_title";

    public static ContentFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(PAGE_TITLE, title);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(bundle);
        return fragment;
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
            TextView tvTitle = view.findViewById(R.id.tvFragmentTitle);
            title = args.getString(PAGE_TITLE);
            tvTitle.setText(title);
        }
    }
}
