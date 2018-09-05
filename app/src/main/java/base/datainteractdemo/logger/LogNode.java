package base.datainteractdemo.logger;

/**
 * Created by beyond on 18-8-16.
 */

public interface LogNode {
    void println(int priority, String tag, String msg, Throwable throwable);
}
