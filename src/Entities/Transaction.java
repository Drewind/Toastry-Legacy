package Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import Constants.TransactionOutcome;
import Interfaces.EntityInterface;
import Services.IDGenerator;

/**
 * Represents a single transaction for a customer.
 */
public class Transaction implements EntityInterface {
    private final List<Consumer<Object>> CSV_LOAD_ORDER = new ArrayList<Consumer<Object>>();
    private final List<Supplier<Object>> CSV_SAVE_ORDER = new ArrayList<Supplier<Object>>();
    private final String TRANSACTION_ID; // Primary key using custom-built GUID
    private final HashMap<Product, Integer> PRODUCTS_PURCHASED;
    private final Date PURCHASE_DATE;
    private final int TABLE_SIZE;

    private TransactionOutcome stage = TransactionOutcome.CREATED;
    private double subtotal;
    private double total;
    private double COGS;
    private boolean hasChanged = false;

    public Transaction(final HashMap<Product, Integer> productsBought, final int tableSize) {
        this.TRANSACTION_ID = IDGenerator.generateGUID();
        this.PURCHASE_DATE = new Date();
        this.TABLE_SIZE = tableSize;
        this.PRODUCTS_PURCHASED = productsBought;
        processProducts();
        setLoadOrder();
        setSaveOrder();
    }

    /* ___________________________________________
                        GETTERS
    ___________________________________________ */
    public String getID() {
        return this.TRANSACTION_ID;
    }

    public List<Consumer<Object>> getLoadOrder() {
        return this.CSV_LOAD_ORDER;
    }

    public List<Supplier<Object>> getSaveOrder() {
        return this.CSV_SAVE_ORDER;
    }

    /**
     * Retrieves a set of all products purchased in this transaction.
     * Since this is a set, it will be unique products without duplicates.
     * @return Set<Product>
     */
    public Set<Product> getPurchases() {
        return this.PRODUCTS_PURCHASED.keySet();
    }

    public final Date getPurchaseDate() {
        return this.PURCHASE_DATE;
    }

    /**
     * Retrieves how many customers were at this table during this transaction.
     * @return int
     */
    public int getNumberServed() {
        return this.TABLE_SIZE;
    }
    
    /**
     * Retrieves which stage this transaction is currently at.
     * @return
     */
    public TransactionOutcome getStage() {
        return this.stage;
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
        System.out.printf(
            "\n------------------------------------" +
            "\nClass %10s:" +
            "\n * GUID: %10s" + " * Purchase Date: %10s" +
            "\n * Served: %10s" + " * Price/Cost: %s/%s" +
            "\n * COGS: %s10" +
            "\n------------------------------------",
            this.getClass().getCanonicalName(),
            this.TRANSACTION_ID, this.PURCHASE_DATE,
            this.TABLE_SIZE, this.subtotal, this.total, this.COGS);
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
        this.CSV_SAVE_ORDER.add(this::getNumberServed);
    }

    /**
     * Completes this transaction.
     */
    private void processProducts() {
        Product product;

        this.stage = TransactionOutcome.PENDING;

        for (Map.Entry<Product, Integer> entry : this.PRODUCTS_PURCHASED.entrySet()) {
            product = entry.getKey();
            System.out.print(" >> Processing '" + product.getProductName() + "'.");
            product.addSale(entry.getValue());
            this.stage = TransactionOutcome.COMPLETED;
        }
    }
}