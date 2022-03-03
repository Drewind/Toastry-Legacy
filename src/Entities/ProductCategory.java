package Entities;

public enum ProductCategory {
    MAIN_DISH,
    SIDE_DISH,
    BEVERAGE,
    DESSERT,
    UNASSIGNED;

    public static ProductCategory getEnum(String value) {
        for(ProductCategory v : values())
            if(v.name().equalsIgnoreCase(value)) return v;
        
        System.out.println("\n[WARNING] No ProductCategory was found for '" + value + "'. Returning UNASSIGNED.");
        return UNASSIGNED;
    }
}
