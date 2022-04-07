package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import Services.IDGenerator;

/**
 * Standard template for entities in the database. All entities have a
 * final string ID. By default a new UID will be generated but can be
 * supplied with an existing ID. Two list collections hold the necessary
 * sequence for loading and saving whatever entity subclass inherits entity.
 * 
 * Use the following code to add a new function in the save sequence:
 * super.CSV_SAVE_ORDER.add(this::FUNCTION_NAME);
 */
public class Entity {
    protected final List<Consumer<Object>> CSV_LOAD_ORDER = new ArrayList<Consumer<Object>>();
    protected final List<Supplier<Object>> CSV_SAVE_ORDER = new ArrayList<Supplier<Object>>();
    private final String ID;
    private boolean hasChanged = false;


    /**
     * Creates an entity with a custom UID.
     */
    public Entity(final String ID) {
        this.ID = ID;
    }


    public final String getID() {
        return this.ID;
    }

    /**
     * Returns whether the entity has been modified.
     * @return boolean
     */
    public boolean hasChanged() {
        return this.hasChanged;
    }

    /**
     * Will reset this entity's hasChanged state; indicating this entity has not been modified.
     * @return void
     */
    public void resetChangedState() {
        this.hasChanged = false;
    }


    /**
     * Ordered list of functions to call to retrieve data according to how it is saved in the DB.
     * @return List<Consumer<Object>>
     */
    public List<Consumer<Object>> getLoadOrder() {
        return this.CSV_LOAD_ORDER;
    }
    

    /**
     * Ordered list of functions to call to safely modify the textfile database.
     * @return List<Supplier<Object>>
     */
    public List<Supplier<Object>> getSaveOrder() {
        return this.CSV_SAVE_ORDER;
    }


    /**
     * Functions to call in order to safely modify the textfile database.
     */
    protected void setSaveOrder() {
        // Sets load order operations
        this.CSV_SAVE_ORDER.add(this::getID);
    }
}
