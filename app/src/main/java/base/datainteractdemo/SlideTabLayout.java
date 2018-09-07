package base.datainteractdemo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

/**
 * Created by beyond on 18-8-16.
 */

public class SlideTabLayout extends HorizontalScrollView implements View.OnClickListener{
    private SlidingTagStrip mStrip;
    private int mTitleOffset;
    private final int TITLE_OFFSET = 24;

    public SlideTabLayout(Context context) {
        this(context, null);
    }

    public SlideTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFillViewport(true);
        mStrip = new SlidingTagStrip(context);
        addView(mStrip, LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
        mTitleOffset = (int) (TITLE_OFFSET * getResources().getDisplayMetrics().density);
    }

    @Override
    public void onClick(View v) {
        int position = mStrip.indexOfChild(v);
        viewPager.setCurrentItem(position);
    }

    ViewPager viewPager;
    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(new InternalOnChangeListener());
            addSlideTab(viewPager);
        }
    }

    private void addSlideTab(ViewPager viewPager) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null) {
            int count = adapter.getCount();
            for (int i = 0; i < count; i++) {
                TextView textView = createDefaultView(getContext());
                if (textView != null) {
                    textView.setText(adapter.getPageTitle(i));
                    textView.setOnClickListener(this);
                }
                mStrip.addView(textView);
            }
        }
    }

    private void scrollTab(int position, int offset) {
        int count = mStrip.getChildCount();
        if (count == 0 || position < 0 || position >= count) {
            return;
        }
        View child = mStrip.getChildAt(position);
        if (child != null) {
            int scrollX = child.getLeft() + offset;
            if (position > 0 || offset > 0) {
                scrollX -= mTitleOffset;
            }
            scrollTo(scrollX, 0);
        }
    }

    private static final int TAB_TEXT_SIZE = 12;
    private static final int TAB_TEXT_PADDING = 16;
    private TextView createDefaultView(Context context) {
        TextView textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_TEXT_SIZE);
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        int padding = (int) (TAB_TEXT_PADDING * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);
        return textView;
    }

    public void setCustomTabColor(IGetTabData iGetTabData) {
        if (mStrip != null) {
            mStrip.setCustomColor(iGetTabData);
        }
    }

    private class InternalOnChangeListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mStrip.onViewPagePositionChange(position, positionOffset);
            int scrollOffset = (int) (mStrip.getChildAt(position).getWidth() * positionOffset);
            scrollTab(position, scrollOffset);
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                scrollTab(position, 0);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;
        }
    }
}
