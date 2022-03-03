package Interfaces;

/**
 * Specific methods that the view actions require to keep their children updated.
 * 
 * Models which implement ModelEntityObserver will call observers listening to the
 * model with these methods. i.e., a ModelEntity will fire notifyNewEntity on
 * the listeners from the model. This ensures view actions will be properly
 * able to update their children.
 */
public interface ViewActionObserver<T> {
    public void notifyNewEntity(T entity);   // Entity was added to model
    public void notifyRemovedEntity(T entity); // Entity was removed from model
    public void notifyModifiedEntity(T entity); // Entity was modified from model
}