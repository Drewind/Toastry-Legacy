package Models;

import java.util.ArrayList;

public interface IModelInterface<T> {
    public ArrayList<T> getEntities();  // Retreives ALL entities
    public void loadEntities();         // Initialization
    public boolean editEntity(T entity);   // Modifying entities
    public boolean createEntity(T entity); // Creating new entities
    public boolean entityExists(final int uid); // Checks if a entity exists
    public boolean deleteEntity(T entity); // Removes an entity from the model/DB
    public int generateID();            // Returns a unique ID
    public T getEntity(final int uid);  // Returns a single entity
    public T getLatestEntity();         // Returns the newest entity
}
