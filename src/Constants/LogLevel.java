package Constants;

/**
 * Unified logging level messages.
 */
public enum LogLevel {
    SUCCESS ("SUCCESS:  "),
    WARNING ("\n<WARNING> "),
    FATAL ("\n<FATAL>     "),
    VERBASE ("VERBASE:  ");

    private final String name;       

    private LogLevel(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}