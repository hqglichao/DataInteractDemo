package base.datainteractdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import base.datainteractdemo.logger.Log;

/**
 * Created by beyond on 18-8-30.
 */

public class SlidingTagStrip extends LinearLayout {
    public SlidingTagStrip(Context context) {
        this(context, null);
    }

    public SlidingTagStrip(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private Paint mBottomPaint;
    private Paint mDividerPaint;
    private Paint mFocusPaint;
    private int mBottomHeight;
    private float mDividerHeightRatio = 0.5f;
    private int mFocusHeight;

    private static final int DEFAULT_BOTTOM_THICKNESS_DIPS = 2;
    private static final int DEFAULT_BOTTOM_ALPHA = 0x26;
    private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
    private static final int DEFAULT_DIVIDER_ALPHA = 0x20;
    private static final int DEFAULT_FOCUS_HEIGHT = 8;



    private IGetTabData iGetTabData;

    public SlidingTagStrip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorForeground, typedValue, true);
        final int defaultColor = typedValue.data;
        final float density = getResources().getDisplayMetrics().density;
        mBottomPaint = new Paint();
        mBottomPaint.setColor(setColorAlpha(defaultColor, DEFAULT_BOTTOM_ALPHA));
        mBottomHeight = (int)(DEFAULT_BOTTOM_THICKNESS_DIPS * density);

        mDividerPaint = new Paint();
        mDividerPaint.setStrokeWidth((int) (DEFAULT_DIVIDER_THICKNESS_DIPS * density));
        mDividerPaint.setColor(setColorAlpha(defaultColor, DEFAULT_DIVIDER_ALPHA));

        mFocusPaint = new Paint();
        mFocusHeight = (int) (DEFAULT_FOCUS_HEIGHT * density);
    }

    private int setColorAlpha(int color, int alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    public void setCustomColor(IGetTabData iGetTabData) {
        this.iGetTabData = iGetTabData;
        invalidate();
    }

    private int position;
    private float pageOffset;
    public void onViewPagePositionChange(int position, float offset) {
        this.position = position;
        this.pageOffset = offset;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int dividerHeight = (int) (height * mDividerHeightRatio);

        View curView = getChildAt(position);
        int color = iGetTabData.getBottomColor(position);
        float left = curView.getLeft();
        float right = curView.getRight();
        if (pageOffset > 0f && position < (getChildCount() - 1)) {
            View nextView = getChildAt(position + 1);
            int nextColor = iGetTabData.getBottomColor(position + 1);
            color = blendColors(nextColor, color, pageOffset);
            left = curView.getLeft() + curView.getWidth() * pageOffset;
            right = curView.getRight() + nextView.getWidth() * pageOffset;
        }
        mFocusPaint.setColor(color);
//        Log.d(Constants.TAG_V1, "position: " + position + " pageOffset: " + pageOffset);
        canvas.drawRect(left, getBottom() - mFocusHeight, right, getBottom(), mFocusPaint);

        canvas.drawRect(0, getBottom() - mBottomHeight, getRight(), getBottom(), mBottomPaint);

        int separateTop = (getHeight() - dividerHeight) / 2;
        for (int i = 0; i < getChildCount() - 1; i++) {
            View view = getChildAt(i);
            canvas.drawLine(view.getRight(), separateTop, view.getRight(), separateTop + dividerHeight, mDividerPaint);
        }
    }

    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

}
