package Models;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import Entities.Location;
import Utilities.Debugger;

public class LocationModel implements IModelInterface<Location> {
    private final File CSV_FILE = new File("LocationList.txt");
    private final Path CSV_PATH = Paths.get("LocationList.txt");
    private final String WARNING_PREFIX = "\n<WARNING>: ";
    private final String SUCCESS_PREFIX = "\nSUCCESS: ";
    private ArrayList<Location> entities = new ArrayList<>();
    
    //private boolean fileLock = false;
    //private opsQueue
    //private final static String FATAL_PREFIX   = "[ FATAL ]   ";

    /**
     * loadLocations
     * Loads into the application all of the Locations saved. Used during initialization.
     */
    @Override
    public void loadEntities() {
        int pk = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line = reader.readLine();

            // Read through the file. Confirm each line is valid through lineIsValid.
            while (line != null) {
                String[] fields = line.split(",");
                if (lineIsValid(fields, 2)) {
                    this.entities.add(new Location(
                        fields[0],                      // Name of Location
                        pk++,                           // ID of Location
                        Boolean.parseBoolean(fields[1].replaceAll("\\s", ""))  // Price
                    ));
                    System.out.println(SUCCESS_PREFIX + "Line '" + line + "' was successfully validated and parsed.\n");
                } else {
                    System.out.println(WARNING_PREFIX + "Corrupt data. Line '" + line + "' failed validation.\n");
                }
                line = reader.readLine();
            }
        } catch(IOException ex) {
            //warnings.add(FATAL_PREFIX + "IOException: '" + ex + "'.");
        }
    }

    @Override
    public ArrayList<Location> getEntities() {
        return this.entities;
    }

    @Override
    public Location getEntity(final int UID) {
        try {
            return this.entities.get(UID);
        } catch (IndexOutOfBoundsException exception) {
            System.out.println(WARNING_PREFIX + "Could not find entity of type " + Location.class.getName() +
                " with unique ID of " + UID + ".");
            return null;
        }
    }

    @Override
    public Location getLatestEntity() {
        try {
            return this.entities.get(this.entities.size());
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(WARNING_PREFIX + "OutOfBounds thrown when fetching latest entity of class " +
                Location.class.getName() + ". Did another class delete a entity before calling this method?");
        } catch (Exception ex) {
            System.out.println(WARNING_PREFIX + "Encountered an error when fetching latest entity of class " +
                Location.class.getName() + ".");
        }
        return null;
    }

    @Override
    public boolean entityExists(final int UID) {
        try {
            return (this.entities.get(UID) != null ? true : false);
        } catch (IndexOutOfBoundsException exception) {
            System.out.println(WARNING_PREFIX + "Encountered OutOfBounds when checking if entity " + Location.class.getName() +
                " with unique ID of " + UID + " exists.");
            return false;
        }
    }

    @Override
    public boolean editEntity(Location Location) {
        try {
            List<String> lines = Files.readAllLines(CSV_PATH);
            final int LINE_ID = Location.getID();
            String[] fields = lines.get(LINE_ID).split(",");
            List<Supplier<Object>> loadOrder = Location.getSaveOrder();

            for (int i = 0; i < fields.length; i++) {
                fields[i] = loadOrder.get(i).get().toString();
            }

            lines.set(LINE_ID, String.join(",", fields));
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            return true;
        } catch (IOException ex) {
            //
        }
        return false;
    }

    @Override
    public boolean deleteEntity(Location product) {
        try {
            List<String> lines = Files.readAllLines(CSV_PATH);
            lines.remove(product.getID());
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            System.out.println(this.SUCCESS_PREFIX + "Removed entity #" + product.getID() + " " + product.getLocationName());
            this.entities.remove(product.getID());
            return true;
        } catch (IOException ex) {
            System.out.println(this.WARNING_PREFIX + "IOException thrown in LocationModel.");
            Debugger.output(ex, "LocationModel");
            return false;
        } catch (Exception ex) {
            System.out.println(this.WARNING_PREFIX + "Unexpected error has occurred while deleting entity in LocationModel.");
            Debugger.output(ex, "LocationModel");
            return false;
        }
    }

    @Override
    public boolean createEntity(Location Location) {
        try {
            List<Supplier<Object>> loadOrder = Location.getSaveOrder();
            List<String> lines = Files.readAllLines(CSV_PATH);
            String[] fields = new String[lines.size()];

            for (int i = 0; i < fields.length; i++) {
                fields[i] = loadOrder.get(i).get().toString();
            }

            lines.add(String.join(",", fields));
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            return true;
        } catch (IOException ex) {
            //
        }
        return false;
    }

    @Override
    public int generateID() {
        try {
            final List<String> lines = Files.readAllLines(CSV_PATH);
            return (lines.size() + 1);
        } catch (IOException ex) {
            return ThreadLocalRandom.current().nextInt(100, 300);
        }
    }

    /**
     * lineIsValid
     * Checks whether or not the index range exists in a CSV row.
     * @param data String[]
     * @param index int to check for
     * @return bool
     */
    private boolean lineIsValid(String[] data, int index) {
        if (data == null || index > data.length) return false;

        for (int i = 0; i < index; i++) {
            if (data[i] == null) return false;
        }

        return true;
    }
}