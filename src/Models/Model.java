package Models;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Constants.LogLevel;
import Entities.Entity;
import Interfaces.ViewActionObserver;


/**
 * Handles interactions with the database.
 * In order to properly save and load data, we will need the following parameters pass
 * to and saved on this model.
 * @param csvFilePath a {@code String} to the file path as we will create the path/file here.
 * @param expectedFields {@code int} is the exact number of fields in a line we expect when validating a line.
 */
public class Model {
    protected final File CSV_FILE;
    protected final Path CSV_PATH;
    private final int EXPECTED_FIELDS;
    private boolean modelLocked;
    protected ArrayList<ViewActionObserver> observers = new ArrayList<>();
    private List<LogMessage> logMessages = new ArrayList<LogMessage>();


    /**
     * Instantiates a new model object. Accepts two arguments, a file object and
     * @param csvFilePath {@code String} a string path pointing to the CSV file.
     * @param size {@code int} how many fields we are expecting when validating a line.
     */
    public Model(final String csvFilePath, final int expectedFields) {
        this.CSV_FILE = new File(csvFilePath);
        this.CSV_PATH = Paths.get(csvFilePath);
        this.EXPECTED_FIELDS = expectedFields;
    }


    /**
     * Edits an existing entity in this model. Requires to be overriden by its children.
     * @return boolean
     */
    public void editEntity(Entity entity) throws IOException, Exception {
        if (modelLocked) { throw new Exception(LogLevel.WARNING + "Model is locked."); }
        modelLocked = true;

        // Confirms the line exists in the CSV file.
        if (getIndexFromFile(entity.getID()).isPresent()) {
            List<String> lines = Files.readAllLines(CSV_PATH);

            // Create a new line based on the entity's ordered save list. Joins the results with a comma.
            String newLine = entity.getSaveOrder().stream().map(fun -> fun.get().toString()).collect(Collectors.joining(","));

            // Write to the file. Grabs the index in the file via getIndexFromFile.
            lines.set(getIndexFromFile(entity.getID()).get(), newLine);
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            entity.resetChangedState();
            for (ViewActionObserver observer : this.observers) { observer.notifyModifiedEntity(entity); }
            addLogMessage(LogLevel.SUCCESS, "Persisted changes to UID '" + entity.getID() + "'.");
        } else {
            addLogMessage(LogLevel.WARNING, "Could not persist changes to entity " + entity.getID() + " because no such entity was found in CSV. "
                        + "Double-check the CSV file for the entity.");
            throw new Exception(LogLevel.WARNING + "Could not find entity (" + entity.getID() + ") in CSV.");
        }
    }


    /**
     * Removes a entity from the database.
     * @param entity
     * @return boolean
     */
    public void deleteEntity(Entity entity) throws Exception {
        if (modelLocked) { throw new Exception(LogLevel.WARNING + "Model is locked."); }
        modelLocked = true;
        
        // Removes the requested entity from the database using a stream and filter.
        // Acts like a while loop, building up the list that we'll need to replace the file anyways.
        List<String> lines = Files.readAllLines(CSV_PATH).stream()
            .filter(line -> !line.contains(entity.getID()))
            .toList();

        Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
    }


    /**
     * Adds a entity to the database.
     * @return boolean
     */
    public void addEntity(Entity entity) throws Exception {
        if (modelLocked) { throw new Exception(LogLevel.WARNING + "Model is locked."); }
        modelLocked = true;

        List<String> lines = Files.readAllLines(CSV_PATH);

        // Create a new line based on the entity's ordered save list. Joins the results with a comma.
        String newLine = entity.getSaveOrder()
                .stream().map(fun -> fun.get().toString())
                .collect(Collectors.joining(","));
        System.out.println(" >> NEWLINE: '" + newLine + "'.");

        // Verify the line isn't null.
        if (newLine == null || newLine == "") {
            addLogMessage(LogLevel.WARNING, "Could not persist entity " + entity.getID() + " because the payload was null. "
                        + "Did you create a valid subtype with a defined save order?");
            throw new Exception(LogLevel.WARNING + "Line is invalid.");
        }

        // Persist the entity.
        lines.add(newLine);

        Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
        modelLocked = false;
    }


    /**
     * Retrieves a index from the file based on the UID passed to it.
     * @param UID
     * @return int
     */
    public Optional<Integer> getIndexFromFile(String UID) {
        Optional<Integer> index = Optional.empty();
        
        try {
            List<String> lines = Files.readAllLines(CSV_PATH);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(UID)) {
                    index = Optional.of(i);
                    System.out.println(" >> getIndexFromFile found index #" + index + ".\n\t" + i); // @todo remove this line
                    return index;
                }
            }
        } catch (IOException ex) {
            addLogMessage(LogLevel.WARNING, "Corrupt data in model. Could not find index in CSV file for " + UID);
        }
        return Optional.empty();
    }


    /**
     * lineIsValid
     * Checks whether or not the index range exists in a CSV row.
     * @param data String[]
     * @param index int to check for
     * @return bool
     */
    protected boolean lineIsValid(String data) {
        if (data == null || data == "" || data.split(",").length != this.EXPECTED_FIELDS) return false;

        String[] fields = data.split(",");
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == null) return false;
        }

        return true;
    }


    /**
     * Registers a new observer for this model.
     * @param observer {@code observer} a enitty that implements ViewActionObserver interface.
     */
    public void registerObserver(ViewActionObserver observer) {
        this.observers.add(observer);
    }


    /**
     * Removes an observer from the model.
     * @param observer {@code observer} a enitty that implements ViewActionObserver interface.
     */
    public void removeObserver(ViewActionObserver observer) {
        this.observers.remove(observer);
    }


    /**
     * Adds a new {@code LogMessage} to the list of log messages for this model.
     * @param logMessage {@code LogMessage} to log.
     */
    protected void addLogMessage(LogLevel level, String msg) {
        this.logMessages.add(new LogMessage("Model", msg, level));
    }

    /**
     * Adds a new {@code LogMessage} to the list of log messages for this model.
     * Will also print to console.
     * @param logMessage {@code LogMessage} to log.
     */
    protected void addLogMessage(LogLevel level, String msg, boolean outputToConsole) {
        LogMessage log = new LogMessage("Model", msg, level);
        this.logMessages.add(log);
        System.out.println(log.toString());
    }


    /**
     * Saves a list of warnings and errors collected during the execution of the model to a file.
     * @todo move this to a singleton
     */
    public void saveReport(final String fileName) {
        final Path PATH = Paths.get(fileName);
        List<String> fileContent = new ArrayList<String>(this.logMessages.size());

        for (LogMessage log : this.logMessages) {
            fileContent.add(log.toString());
        }

        try {
            Files.write(PATH, fileContent, StandardCharsets.UTF_8);
            System.out.println(LogLevel.VERBASE + "Wrote log file to path \n" + fileName);
        } catch(IOException ex) {
            addLogMessage(LogLevel.WARNING, "Could not save log messages to file.\n" + ex, true);
        }
    }
}
