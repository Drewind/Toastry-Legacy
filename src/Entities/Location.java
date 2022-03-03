package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import Interfaces.EntityInterface;

/**
 * Location
 * An entity for a single location. Defines variables and methods for a product.
 */
public class Location implements EntityInterface {
    private final List<Supplier<Object>> CSV_SAVE_ORDER = new ArrayList<Supplier<Object>>();
    private final List<Consumer<Object>> CSV_LOAD_ORDER = new ArrayList<Consumer<Object>>();
    private final int UID; // Primary key
    private final double rent;
    private String name;
    private boolean isCorporate;
    private boolean hasChanged = false;

    /**
     * Location
     * Builds a new location entity through the application.
     * @param name string
     * @param corporate boolean; a corporate location or not
     */
    public Location(String name, final double rent, boolean corporate) {
        this.UID = 0;
        this.name = name;
        this.rent = rent;
        this.isCorporate = corporate;

        setSaveOrder();
        setLoadOrder();
    }

    /**
     * Location
     * Loads in a location based on existing data from a CSV file.
     * @param name string
     * @param uid int; unique identifier for this entity
     * @param corporate boolean; a corporate location or not
     */
    public Location(String name, int uid, final double rent, boolean corporate) {
        this.UID = uid;
        this.name = name;
        this.rent = rent;
        this.isCorporate = corporate;

        setSaveOrder();
        setLoadOrder();
    }

    /* ___________________________________________
                        GETTERS
    ___________________________________________ */
    public List<Consumer<Object>> getLoadOrder() {
        return this.CSV_LOAD_ORDER;
    }

    public List<Supplier<Object>> getSaveOrder() {
        return this.CSV_SAVE_ORDER;
    }

    public int getID() {
        return this.UID;
    }

    public boolean getCorporateValue() {
        return this.isCorporate;
    }

    public double getRent() {
        return this.rent;
    }

    /**
     * getLocationName
     * Returns the name of this location.
     * @return String
     */
    public String getLocationName() {
        return this.name;
    }

    /**
     * Returns whether the entity has been modified.
     * @return boolean
     */
    public boolean hasChanged() {
        return this.hasChanged;
    }

    /* ___________________________________________
                        SETTERS
    ___________________________________________ */

    /**
     * Functions to call in order to safely modify the textfile database.
     */
    private void setSaveOrder() {
        // Sets load order operations
        this.CSV_SAVE_ORDER.add(this::getLocationName);
        this.CSV_SAVE_ORDER.add(this::getCorporateValue);
    }

    /**
     * Functions to call when the RecordSales submit button is pressed.
     */
    private void setLoadOrder() {
        this.CSV_LOAD_ORDER.add(a -> setLocationName(a.toString()));
        this.CSV_LOAD_ORDER.add(a -> setCorporateValue(Boolean.parseBoolean(a.toString())));
    }

    public void setLocationName(String name) {
        this.name = name;
    }

    public void setCorporateValue(boolean isCorporate) {
        this.isCorporate = isCorporate;
    }

    /**
     * Will reset this entity's hasChanged state; indicating this entity has not been modified.
     * @return void
     */
    public void resetChangedState() {
        this.hasChanged = false;
    }
}