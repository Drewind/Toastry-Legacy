package Interfaces;

/**
 * A model interface that's not connected to the entity framework.
 * Used by App, models, and controllers. Not for used for views.
 * 
 * This interface allows for retrieving and setting the state of
 * various objects in the application. i.e., getting and setting
 * total daily sales.
 */
public interface AppStateModel {
    public void initialize();
}
