package base.datainteractdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import base.datainteractdemo.fragment.AIDLFragment;
import base.datainteractdemo.fragment.BaseFragment;
import base.datainteractdemo.fragment.BinderInterfaceFragment;
import base.datainteractdemo.fragment.BroadcastFragment;
import base.datainteractdemo.fragment.MessengerFragment;
import base.datainteractdemo.fragment.SharePreferenceFragment;

import static base.datainteractdemo.fragment.BaseFragment.PAGE_TITLE;

/**
 * Created by beyond on 18-8-16.
 */

public class SlidingTabsColorsFragment extends Fragment {

    private List<PagerTabItem> itemList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemList.add(new PagerTabItem(Color.BLUE, getString(R.string.tab_broadcast)));
        itemList.add(new PagerTabItem(Color.GREEN, getString(R.string.tab_binder_interface)));
        itemList.add(new PagerTabItem(Color.YELLOW, getString(R.string.tab_messenger)));
        itemList.add(new PagerTabItem(Color.RED, getString(R.string.tab_share_preferences)));
        itemList.add(new PagerTabItem(Color.DKGRAY, getString(R.string.tab_aidl)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slide_tab_container_fragment, container, false);
    }

    SlideTabLayout slideTabLayout;
    ViewPager viewPager;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new SimpleFragmentAdapter(getFragmentManager()));

        slideTabLayout = view.findViewById(R.id.slideTabLayout);
        slideTabLayout.setViewPager(viewPager);
        slideTabLayout.setCustomTabColor(new IGetTabData() {
            @Override
            public int getBottomColor(int i) {
                return itemList.get(i).getBottomLineColor();
            }
        });
    }

    private class SimpleFragmentAdapter extends FragmentPagerAdapter {
        public SimpleFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return itemList.get(position).createFragment(position);
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return itemList.get(position).getTitle();
        }
    }

    private class PagerTabItem {
        private int bottomLineColor;
        private String title;
        private static final int FRAGMENT_BROADCAST = 0;
        private static final int FRAGMENT_BINDER = 1;
        private static final int FRAGMENT_MESSAGER = 2;
        private static final int FRAGMENT_SHARE_PREFERENCE = 3;
        private static final int FRAGMENT_AIDL = 4;

        public PagerTabItem(int bottomLineColor, String title) {
            this.bottomLineColor = bottomLineColor;
            this.title = title;
        }

        private BaseFragment createFragment(int position) {
            BaseFragment fragment = null;
            switch (position) {
                case FRAGMENT_BROADCAST:
                    fragment = new BroadcastFragment();
                    break;
                case FRAGMENT_BINDER:
                    fragment = new BinderInterfaceFragment();
                    break;
                case FRAGMENT_MESSAGER:
                    fragment = new MessengerFragment();
                    break;
                case FRAGMENT_SHARE_PREFERENCE:
                    fragment = new SharePreferenceFragment();
                    break;
                case FRAGMENT_AIDL:
                    fragment = new AIDLFragment();
                    break;
                default:
                    break;
            }
            Bundle bundle = new Bundle();
            bundle.putString(PAGE_TITLE, title);
            fragment.setArguments(bundle);

            return fragment;
        }

        public int getBottomLineColor() {
            return bottomLineColor;
        }

        public String getTitle() {
            return title;
        }
    }
}
