package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import Interfaces.EntityInterface;
import Services.IDGenerator;

/**
 * Product
 * An entity for a single product. Defines variables and methods for a product.
 */
public class Product implements EntityInterface {
    private final List<Consumer<Object>> CSV_LOAD_ORDER = new ArrayList<Consumer<Object>>();
    private final List<Supplier<Object>> CSV_SAVE_ORDER = new ArrayList<Supplier<Object>>();
    private final String PRODUCT_ID; // Primary key
    private String name;
    private double price; // Current price listed
    private double cost; // Current cost to make
    private ProductCategory category; // Assigned category
    private boolean active; // If this product is purchaseable.

    private int totalSales = 0;
    private double totalExpenses = 0.0;
    private double totalGrossSales = 0.0;
    private int dailySales = 0;
    private boolean hasChanged = false;

    /**
     * Product
     * Builds a new product listing through the application.
     * @param name string
     * @param cost double; cost us to make
     * @param price double; price to buy from us
     * @param ProductCategory category
     */
    public Product(String name, double cost, double price, ProductCategory category) {
        super();
        this.PRODUCT_ID = IDGenerator.generateGUID();
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.category = category;

        setSaveOrder();
    }

    /**
     * Product
     * Loads in a product based on existing data from a CSV file.
     * @param name string
     * @param cost double; cost us to make
     * @param price double; price to buy from us
     * @param totalSales int; total sales for this product
     */
    public Product(String name, final int id, double price, double cost, int totalSales, ProductCategory category) {
        super();
        this.name = name;
        this.price = price;
        this.cost = cost;
        this.totalSales = totalSales;
        this.category = category;
        this.PRODUCT_ID = IDGenerator.generateGUID();

        setSaveOrder();
    }

    public Product(final String ID) {
        this.PRODUCT_ID = ID;
        setLoadOrder();
        setSaveOrder();
    }

    /**
     * Methods to call to load a enitty from the database into the application.
     */
    private void setLoadOrder() {
        this.CSV_LOAD_ORDER.add(a -> emptyMethod());
        this.CSV_LOAD_ORDER.add(a -> setProductName(a.toString()));
        this.CSV_LOAD_ORDER.add(a -> setPrice(Double.parseDouble(a.toString())));
        this.CSV_LOAD_ORDER.add(a -> setCost(Double.parseDouble(a.toString())));
        this.CSV_LOAD_ORDER.add(a -> loadTotalSales(Integer.parseInt(a.toString())));
        this.CSV_LOAD_ORDER.add(a -> setCategory(ProductCategory.valueOf(a.toString())));
    }

    /**
     * Functions to call in order to safely modify the textfile database.
     */
    private void setSaveOrder() {
        // Sets load order operations
        this.CSV_SAVE_ORDER.add(this::getID);
        this.CSV_SAVE_ORDER.add(this::getProductName);
        this.CSV_SAVE_ORDER.add(this::getPrice);
        this.CSV_SAVE_ORDER.add(this::getCost);
        this.CSV_SAVE_ORDER.add(this::getTotalSales);
        this.CSV_SAVE_ORDER.add(this::getCategory);
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

    /**
     * Retrieves this entity's unique ID.
     * @return int
     */
    public String getID() {
        return this.PRODUCT_ID;
    }

    /**
     * Returns this product's assigned category.
     * @return ProductCategory
     */
    public ProductCategory getCategory() {
        return this.category;
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
     * getProfit
     * Returns price - cost for this product.
     * @return double profit of this product.
     */
    public double getProfit() {
        return Math.round(this.price - this.cost);
    }

    /**
     * getPrice
     * Returns price for this product.
     * @return double price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * getCost
     * Returns cost to make this product.
     * @return double cost
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * getProductName
     * Returns the product/dish name.
     * @return String product/dish
     */
    public String getProductName() {
        return this.name;
    }

    /**
     * getYesterdaySales
     * Returns yesterday sales.
     * @return int yesterday sales
     */
    public int getDailySales() {
        return this.dailySales;
    }

    /**
     * Retrieves this product's daily profit.
     * @return double
     */
    public double getDailyProfit() {
        return (this.price * this.dailySales) - (this.cost * this.dailySales);
    }

    /**
     * Returns this product's total sales over its life.
     * @return int
     */
    public int getTotalSales() {
        return this.totalSales;
    }

    /**
     * getTotalProfit
     * Retrieves the total profit for this product.
     * @return double
     */
    public double getTotalProfit() {
        return (this.price * this.totalSales) - (this.cost * this.totalSales);
    }

    /**
     * Retrieves total revenue for this product's lifespan.
     * @return double
     */
    public double getTotalRevenue() {
        return this.totalGrossSales;
    }

    /**
     * Retrieves total expenses for this product's lifespan.
     * @return double
     */
    public double getTotalExpenses() {
        return this.totalExpenses;
    }

    /**
     * Returns if the product is active or not.
     * @return
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Called internally to load total sales from the DB to the entity.
     * @param totalSales
     */
    private void loadTotalSales(final int totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * Prints out the entity's values in console.
     * @todo remove this
     */
    public void debugEntity() {
        System.out.println(
            "\n------------------------------------" +
            "\n--| DEBUGGER |--" +
            "\nClass " + this.getClass().getCanonicalName() + ";\n" +
            (this.hasChanged ? " | PENDING CHANGES |" : "") +
            "\n * Cost: " + getCost() +
            "\n * Price: " + getPrice() +
            "\n * YSales: " + getDailySales() +
            "\n * TSales: " + getTotalSales() +
            "\n-----------------------------------"
        );
    }

    /* ___________________________________________
                        SETTERS
    ___________________________________________ */

    /**
     * Assigns the product's name. Used by methods to change this product's name.
     * @param name String
     * @return void
     */
    public void setProductName(String name) {
        this.name = name;
        this.hasChanged = true;
    }

    /**
     * Assigns a cost to this product.
     * @todo add this to the change history table
     * @param cost double
     * @return void
     */
    public void setCost(double cost) {
        this.cost = cost;
        this.hasChanged = true;
    }

    /**
     * Assigns the price of this product.
     * @todo add this to the change history table
     * @param price double
     * @return void
     */
    public void setPrice(double price) {
        this.price = price;
        this.hasChanged = true;
    }

    /**
     * Assigns the category to this product.
     * @todo add this to the change history table
     * @param category
     * @return void
     */
    public void setCategory(ProductCategory category) {
        this.category = category;
        this.hasChanged = true;
    }

    /**
     * Adds daily sales in dollars and COGS to this entity.
     * Wipes the yesterdaySales counter to zero,
     * retiring this entity's day.
     * @param sales int number of sales this product has made.
     * @return void
     */
    public void computeEndOfDay() {
        this.totalSales += this.dailySales;
        this.totalGrossSales += (this.dailySales * this.price);
        this.totalExpenses += (this.dailySales * this.cost);
        this.dailySales = 0;
        this.hasChanged = true;
    }

    /**
     * Add sales to this product. Increments total sales, total reveneue/expenses.
     * @todo add a way to discount items
     * @param quantity
     */
    public void addSale(int quantity) {
        this.totalSales += quantity;
        this.totalExpenses += (quantity * cost);
        this.totalGrossSales += (quantity * price);
    }

    /**
     * Sets if this product is active or not. If it is active, then
     * customers may purchase it.
     * @todo add this to the change history table
     * @param state boolean
     */
    public void setActive(boolean state) {
        this.active = state;
    }

    public void emptyMethod() {
        //
    }
}