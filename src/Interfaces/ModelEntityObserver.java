package Interfaces;

/**
 * Models which handle entities will implement this interface.
 * When models do not work directly with the entity framework, they will
 * implement the standard ModelObserver interface.
 */
public interface ModelEntityObserver<T> {
    public void registerObserver(ViewActionObserver observer);
    public void removeObserver(ViewActionObserver observer);
    // public T notifyEntityAdded();
    // public T notifyEntityRemoved();
    // public T notifyEntityModified();
}
