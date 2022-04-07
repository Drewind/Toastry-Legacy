package Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import Entities.Entity;
import Interfaces.ViewActionObserver;

public class Model {
    private final File CSV_FILE;
    private final Path CSV_PATH;
    private final String WARNING_PREFIX = "\n<WARNING>: ";
    private final String SUCCESS_PREFIX = "\nSUCCESS: ";
    private final String FATAL_PREFIX = "\n<FATAL>: ";
    private ArrayList<ViewActionObserver<Entity>> observers = new ArrayList<>();
    // private ArrayList<Entity> entities = new ArrayList<>();
    private final HashMap<String, Entity> entities = new HashMap<String, Entity>();


    /**
     * Instantiates a new model object. Accepts two arguments, a file object and
     * @param csvFile
     * @param size
     */
    public Model(final String csvFilePath) {
        this.CSV_FILE = new File(csvFilePath);
        this.CSV_PATH = Paths.get(csvFilePath);
    }


    /**
     * Edits an existing entity in this model. Requires to be overriden by its children.
     * @return boolean
     */
    public boolean editEntity(Entity entity) {
        try {
            List<String> lines = Files.readAllLines(CSV_PATH);
            Optional<String> entityInFile = lines.stream().filter(ID -> ID.contains(entity.getID())).findFirst();

            if (entityInFile.isPresent()) {
                System.out.println(">> Entity #" + entity.getID() + " was found in file.");

                // Create a new line based on the entity's ordered save list. Joins the results with a comma.
                String newLine = entity.getSaveOrder().stream().map(fun -> fun.get().toString()).collect(Collectors.joining(","));

                System.out.println(this.SUCCESS_PREFIX + "Persisted changes to entity with UID '" + entity.getID() + "'.");
                Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
                entity.resetChangedState();
                for (ViewActionObserver<Entity> observer : this.observers) { observer.notifyModifiedEntity(entity); }
                System.out.println(this.SUCCESS_PREFIX + "Persisted changes to entity with UID '" + entity.getID() + "''.");
                return true;
            } else {
                System.out.println(WARNING_PREFIX + "Corrupt data. Entity with UID of '" + entity.getID() + "' was not found in DB.");
            }
        } catch (IOException ex) {
            System.out.println(WARNING_PREFIX + "Corrupt data. Entity with UID of '" + entity.getID() + "' was not updated in DB.");
        }

        return false;
    }


    /**
     * Loads all of the entities in this model. Required to call prior to using the model.
     * @return void
     */
    public void loadEntities() {
        int i = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(this.CSV_FILE))) {
            String line = reader.readLine();

            // O(n^2) with the for/stream
            while (line != null) {
                String[] fields = line.split(",");
                if (lineIsValid(fields)) {
                    Entity entity = new Entity(fields[0]);

                    // Call the load order.
                    i = 0;
                    for (Consumer<Object> consumer : entity.getLoadOrder()) {
                        consumer.accept(fields[i]);
                        i++;
                    }

                    this.entities.put(entity.getID(), entity);
                    // System.out.println(SUCCESS_PREFIX + "Line '" + line + "' was successfully validated and parsed.");
                } else {
                    System.out.println(WARNING_PREFIX + "Corrupt data. Line '" + line + "' failed validation.");
                }

                line = reader.readLine();
            }
        } catch (IOException ex) {
            System.out.println(this.FATAL_PREFIX + "IOException: '" + ex + "'.");
        }
    }


    /**
     * Retrieves ALL entities from this model.
     * @return Entity
     */
    public Collection<Entity> getEntities() {
        return this.entities.values();
    }


    /**
     * Retrieves a single entity from the model.
     * @param UID unique ID of the entity
     * @return Entity
     */
    public Entity getEntity(final String UID) {
        return this.entities.get(UID);
    }


    /**
     * Checks to see if the given UID for a entity in this model exists. Returns true if found.
     * @param UID unique ID of the entity
     * @return boolean, true if found
     */
    public boolean entityExists(final String UID) {
        return (this.entities.containsKey(UID));
    }


    /**
     * lineIsValid
     * Checks whether or not the index range exists in a CSV row.
     * @param data String[]
     * @param index int to check for
     * @return bool
     */
    private boolean lineIsValid(String[] data) {
        if (data == null) return false;

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) return false;
        }

        return true;
    }
}
