package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

import Constants.LogLevel;
import Entities.Location;

/**
 * LocationModel
 * A class to handle reading and writing of Location entities.
 */
public class LocationModel extends Model {
    private final HashMap<String, Location> entities = new HashMap<String, Location>();

    public LocationModel() {
        super("LocationList.txt", new Location("0").getLoadOrder().size());
    }

    public LocationModel(final String CSV_PATH) {
        super(CSV_PATH, new Location("0").getLoadOrder().size());
    }

    public Location getLocation(final String UID) {
        return this.entities.get(UID);
    }

    public boolean addLocation(Location entity) {
        try {
            if (this.entities.containsKey(entity.getID())) {
                super.addLogMessage(LogLevel.WARNING, "Location (" + entity.getID() + ") in model already exists.");
                return false;
            }

            this.entities.put(entity.getID(), entity);
            super.addEntity(entity);
            return true;
        } catch (Exception ex) {
            super.addLogMessage(LogLevel.WARNING, "An error occurred when persisting location (" + entity.getID() + ") in model.\n\t" + ex, true);
        }
        return false;
    }

    public boolean editLocation(Location location) {
        try {
            if (this.entities.containsKey(location.getID())) {
                this.entities.put(location.getID(), location);
                super.editEntity(location);
                return true;
            }

            super.addLogMessage(LogLevel.WARNING, "Could not find location (" + location.getID() + ") in model.");
            return false;
        } catch (Exception ex) {
            super.addLogMessage(LogLevel.WARNING, "An error occurred when editing location (" + location.getID() + ") in model.\n\t" + ex, true);
        }
        return false;
    }

    public boolean deleteLocation(Location location) {
        try {
            if (this.entities.containsKey(location.getID())) {
                this.entities.remove(location.getID());
                super.deleteEntity(location);
                return true;
            }

            super.addLogMessage(LogLevel.WARNING, "Could not find location (" + location.getID() + ") in model.", true);
            return false;
        } catch (Exception ex) {
            super.addLogMessage(LogLevel.WARNING, "An error occurred when deleting location (" + location.getID() + ") in model.\n\t" + ex, true);
        }
        return false;
    }

    /**
     * Loads all of the entities in this model. Required to call prior to using the model.
     * @return void
     */
    public boolean loadEntities() {
        int failedToParse = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(super.CSV_FILE))) {
            String line = reader.readLine();

            while (line != null) {
                if (super.lineIsValid(line) && parseEntity(line)) {
                } else {
                    super.addLogMessage(LogLevel.WARNING, "Could not parse line \n'" + line + "'' in location model; line was invalid.");
                    failedToParse++;
                }

                line = reader.readLine();
            }

            return true;
        } catch (IOException ex) {
            super.addLogMessage(LogLevel.FATAL, "Could not locate file in model.");
            return false;
        } finally {
            super.addLogMessage(LogLevel.VERBASE,
                        "Model has finished loading entities."
                        + "\n\t" + this.entities.size() + " successfully entities parsed."
                        + "\n\t" + failedToParse + " entiites failed to parsed.", true);
        }
    }

    private boolean parseEntity(final String line) {
        int i = 0;

        try {
            String[] fields = line.split(",");
            Location location = new Location(fields[0]);
            location.setOriginalData(line);

            i = 0;
            for (Consumer<Object> consumer : location.getLoadOrder()) {
                consumer.accept(fields[i++]);
            }

            this.entities.put(location.getID(), location);
        } catch (NumberFormatException ex) {
            super.addLogMessage(LogLevel.FATAL, "Could not parse entities in model.", true);
            return false;
        }

        return true;
    }

    /**
     * Retrieves ALL entities from this model.
     * @return Entity
     */
    public Collection<Location> getEntities() {
        return this.entities.values();
    }

    /**
     * Checks to see if the given UID for a entity in this model exists. Returns true if found.
     * @param UID unique ID of the entity
     * @return boolean, true if found
     */
    public boolean entityExists(final String UID) {
        return (this.entities.containsKey(UID));
    }
}