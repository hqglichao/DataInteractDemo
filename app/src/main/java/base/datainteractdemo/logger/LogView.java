package base.datainteractdemo.logger;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by beyond on 18-8-16.
 */

public class LogView extends TextView implements LogNode{
    public LogView(Context context) {
        super(context);
    }

    public LogView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void println(int priority, String tag, String msg, Throwable throwable) {
        String priorityStr = null;
        switch(priority) {
            case android.util.Log.VERBOSE:
                priorityStr = "VERBOSE";
                break;
            case android.util.Log.DEBUG:
                priorityStr = "DEBUG";
                break;
            case android.util.Log.INFO:
                priorityStr = "INFO";
                break;
            case android.util.Log.WARN:
                priorityStr = "WARN";
                break;
            case android.util.Log.ERROR:
                priorityStr = "ERROR";
                break;
            case android.util.Log.ASSERT:
                priorityStr = "ASSERT";
                break;
            default:
                break;
        }

        String exceptionStr = null;
        if (throwable != null) {
            exceptionStr = android.util.Log.getStackTraceString(throwable);
        }

        final StringBuilder outStrBuilder = new StringBuilder();
        String delimiter = "\t";
        appendIfNoNone(outStrBuilder, priorityStr, delimiter);
        appendIfNoNone(outStrBuilder, exceptionStr, delimiter);
        appendIfNoNone(outStrBuilder, tag, delimiter);
        appendIfNoNone(outStrBuilder, msg, delimiter);

        post(new Runnable() {
            @Override
            public void run() {
                appendToLog(outStrBuilder.toString());
            }
        });
    }

    private void appendIfNoNone(StringBuilder stringBuilder, String content, String delimiter) {
        if (content == null || content.length() < 1) return;
        stringBuilder.append(content).append(delimiter);
    }

    private void appendToLog(String log) {
        append(log + "\n");
    }
}
