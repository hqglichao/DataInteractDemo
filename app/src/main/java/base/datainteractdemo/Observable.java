package base.datainteractdemo;

/**
 * Created by beyond on 18-9-4.
 */

public interface Observable {
    public void addObserver(Observer o);
    public void deleteObserver();
    public void notifyObservers();
}
