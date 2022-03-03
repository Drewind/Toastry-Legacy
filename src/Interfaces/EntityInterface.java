package Interfaces;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * All entities must implement this interface. Allows for models
 * and views to interact with entities.
 * @method getID: returns @return int.
 * @method hasChanged: returns whether or not the entity has changed since last save.
 * @method resetChangedState: sets hasChanged to false; indicating this entity has saved.
 * @method getLoadOrder: list of methods to load a entity from the DB to the application.
 * @method getSaveOrder: list of methods to persist a entity to the DB.
 */
public interface EntityInterface {
    public int getID();
    public boolean hasChanged();
    public void resetChangedState();
    public List<Consumer<Object>> getLoadOrder();
    public List<Supplier<Object>> getSaveOrder();
}
