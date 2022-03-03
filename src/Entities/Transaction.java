package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import Interfaces.EntityInterface;

/**
 * Represents a single transaction for a customer.
 */
public class Transaction implements EntityInterface {
    private final List<Consumer<Object>> CSV_LOAD_ORDER = new ArrayList<Consumer<Object>>();
    private final List<Supplier<Object>> CSV_SAVE_ORDER = new ArrayList<Supplier<Object>>();
    private final int TRANSACTION_ID; // Primary key
    private final int PRODUCT_ID;
    private final int PURCHASE_DAY; // In-game day for which this was bought
    private final int NUMBER_SERVED;
    private final double PRICE;
    private final double COST;
    
    private boolean hasChanged = false;

    public Transaction(final int ID, final int BOUGHT_ON, final Product product) {
        this.TRANSACTION_ID = ID;
        this.PRODUCT_ID = product.getID();
        this.PURCHASE_DAY = BOUGHT_ON;
        this.NUMBER_SERVED = product.getDailySales();
        this.PRICE = product.getPrice();
        this.COST = product.getCost();
        setLoadOrder();
        setSaveOrder();
    }

    /* ___________________________________________
                        GETTERS
    ___________________________________________ */
    public int getID() {
        return this.TRANSACTION_ID;
    }

    public List<Consumer<Object>> getLoadOrder() {
        return this.CSV_LOAD_ORDER;
    }

    public List<Supplier<Object>> getSaveOrder() {
        return this.CSV_SAVE_ORDER;
    }

    public int getProductID() {
        return this.PRODUCT_ID;
    }

    public int getPurchaseDay() {
        return this.PURCHASE_DAY;
    }

    public int getNumberServed() {
        return this.NUMBER_SERVED;
    }

    public double getPrice() {
        return this.PRICE;
    }

    public double getCost() {
        return this.COST;
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
     * Prints out the entity's values in console.
     * @todo remove this
     */
    public void debugEntity() {
        System.out.println(
            "\n------------------------------------" +
            "\n--| DEBUGGER |--" +
            "\nClass " + this.getClass().getCanonicalName() + ";" +
            "\n * ID: " + this.TRANSACTION_ID +
            "\n * Product ID: " + this.PRODUCT_ID +
            "\n * Bought on: " + this.PURCHASE_DAY +
            "\n * Served: " + this.NUMBER_SERVED +
            "\n * Price: " + this.PRICE +
            "\n * Cost: " + this.COST +
            "\n-----------------------------------"
        );
    }

    /* ___________________________________________
                        SETTERS
    ___________________________________________ */

    /**
     * Methods to call to load a enitty from the database into the application.
     */
    private void setLoadOrder() {
    }

    /**
     * Functions to call in order to safely modify the textfile database.
     */
    private void setSaveOrder() {
        // Sets load order operations
        this.CSV_SAVE_ORDER.add(this::getID);
        this.CSV_SAVE_ORDER.add(this::getProductID);
        this.CSV_SAVE_ORDER.add(this::getPurchaseDay);
        this.CSV_SAVE_ORDER.add(this::getPrice);
        this.CSV_SAVE_ORDER.add(this::getCost);
        this.CSV_SAVE_ORDER.add(this::getNumberServed);
    }
}