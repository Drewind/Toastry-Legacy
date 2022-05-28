package Entities;

/**
 * Product
 * An entity for a single product. Defines variables and methods for a product.
 */
public class Product extends Entity {
    private String name;
    private double price; // Current price listed
    private double cost; // Current cost to make
    private ProductCategory category; // Assigned category

    // Properties for handling math
    private int totalSales = 0;
    private double totalExpenses = 0.0;
    private double totalGrossSales = 0.0;
    private int dailySales = 0;

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
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.category = category;

        setLoadOrder();
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
    public Product(final String id) {
        super(id);
        setLoadOrder();
        setSaveOrder();
    }

    /**
     * Methods to call to load a enitty from the database into the application.
     */
    @Override
    protected void setLoadOrder() {
        this.CSV_LOAD_ORDER.add(a -> emptyMethod());
        this.CSV_LOAD_ORDER.add(a -> setProductName(a.toString()));
        this.CSV_LOAD_ORDER.add(a -> setPrice(Double.parseDouble(a.toString())));
        this.CSV_LOAD_ORDER.add(a -> setCost(Double.parseDouble(a.toString())));
        this.CSV_LOAD_ORDER.add(a -> loadTotalSales(Integer.parseInt(a.toString())));
        this.CSV_LOAD_ORDER.add(a -> setCategory(ProductCategory.getEnum(a.toString())));
    }

    /**
     * Functions to call in order to safely modify the textfile database.
     */
    @Override
    protected void setSaveOrder() {
        this.CSV_SAVE_ORDER.add(super::getID);
        this.CSV_SAVE_ORDER.add(this::getProductName);
        this.CSV_SAVE_ORDER.add(this::getPrice);
        this.CSV_SAVE_ORDER.add(this::getCost);
        this.CSV_SAVE_ORDER.add(this::getTotalSales);
        this.CSV_SAVE_ORDER.add(this::categoryName);
    }

    /* ___________________________________________
                        GETTERS
    ___________________________________________ */
    /**
     * Returns this product's assigned category.
     * @return ProductCategory
     */
    public ProductCategory getCategory() {
        return this.category;
    }

    public String categoryName() {
        return this.category.name();
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
        // Print header
        System.out.println("\n------------------------------------" +
                "\n--| DEBUGGER |--" +
                "\nClass: " + this.getClass().getCanonicalName() + "\n" +
                (super.hasChanged() ? " | PENDING CHANGES |" : "CURRENT") +
                "\nOriginal Data\n" + super.getOriginalData() + "\n"
        );

        // Print fields
        System.out.printf(
            "\n * %16s: %s" +
            "\n * %16s: %s" +
            "\n * %16s: %s" +
            "\n * %16s: %s" +
            "\n * %16s: %s" +
            "\n * %16s: %s" +
            "\n * %16s: %s" +
            "\n------------------------------------",
            "Product Name", this.getProductName(),
            "Category", (this.getCategory() != null ? this.getCategory().toString() : "null"),
            "Cost", this.getCost(),
            "Price", this.getPrice(),
            "Yesterday Sales", this.getDailySales(),
            "Today's Sales", this.getTotalSales(),
            "Save/Load Ops", this.CSV_SAVE_ORDER.size() + "/" + this.CSV_LOAD_ORDER.size()
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

    public void emptyMethod() {
    }
}