package Entities;

/**
 * Location
 * An entity for a single location. Defines variables and methods for a product.
 */
public class Location extends Entity{
    private double rent;
    private String name;
    private boolean isCorporate;

    /**
     * Location
     * Builds a new location entity through the application.
     * @param name string
     * @param corporate boolean; a corporate location or not
     */
    public Location(String name, double rent, boolean corporate) {
        super();
        this.name = name;
        this.rent = rent;
        this.isCorporate = corporate;

        super.setSaveOrder();
        super.setLoadOrder();
    }

    /**
     * Location
     * Loads in a location based on existing data from a CSV file.
     * @param name string
     * @param uid int; unique identifier for this entity
     * @param corporate boolean; a corporate location or not
     */
    public Location(String UID) {
        super(UID);
        setSaveOrder();
        setLoadOrder();
    }

    /* ___________________________________________
                        GETTERS
    ___________________________________________ */
    public boolean isCorporateLocation() {
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

    /* ___________________________________________
                        SETTERS
    ___________________________________________ */

    /**
     * Functions to call in order to safely modify the textfile database.
     */
    @Override
    protected void setSaveOrder() {
        this.CSV_SAVE_ORDER.add(this::getLocationName);
        this.CSV_SAVE_ORDER.add(this::isCorporateLocation);
    }

    /**
     * Methods to call to load a enitty from the database into the application.
     */
    @Override
    protected void setLoadOrder() {
        this.CSV_LOAD_ORDER.add(a -> emptyMethod());
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
            "\n------------------------------------",
            "Location Name", this.getLocationName(),
            "Rent", this.getRent(),
            "Is Corporate", (this.isCorporateLocation() ? "true" : "false"),
            "Save/Load Ops", this.CSV_SAVE_ORDER.size() + "/" + this.CSV_LOAD_ORDER.size()
        );
    }

    public void emptyMethod() {
    }
}