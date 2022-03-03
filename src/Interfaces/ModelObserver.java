package Interfaces;

public interface ModelObserver {
    public void registerObserver(ViewObserver observer);
    public void removeObserver(ViewObserver observer);
    public void notifyObservers();
}
