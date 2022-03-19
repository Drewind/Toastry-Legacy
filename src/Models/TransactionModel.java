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
import java.util.function.Consumer;
import java.util.function.Supplier;

import Entities.Product;
import Entities.Transaction;
import Interfaces.ModelObserver;
import Interfaces.ViewObserver;
import Utilities.Debugger;

/**
 * TransactionModel
 * Loads, saves, and manages purchases and their history.
 */
public class TransactionModel implements IModelInterface<Transaction>, ModelObserver {
    private final File CSV_FILE = new File("transactions.txt");
    private final Path CSV_PATH = Paths.get("transactions.txt");
    private final String WARNING_PREFIX = "\n<WARNING>: ";
    private final String SUCCESS_PREFIX = "\nSUCCESS: ";
    private ArrayList<Transaction> entities = new ArrayList<>();
    private ArrayList<ViewObserver> observers = new ArrayList<>();
    private final IModelInterface<Product> products;

    public TransactionModel(IModelInterface<Product> model) {
        this.products = model;
    }

    /**
     * GUID [PK]  X  X  X  X
     * 35FKDJ357  X  X  X  X
     */

    /**
     * Loads into the application all of the transactions saved. Used during initialization.
     * 
     * Called in the beginning of the application. Parses all products from the database.
     * @return void
     */
    @Override
    public void loadEntities() {
        int pk = 0;
        int i = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line = reader.readLine();

            // Read through the file. Confirm each line is valid through lineIsValid.
            while (line != null) {
                String[] fields = line.split(",");
                if (lineIsValid(fields, 6)) {
                    Transaction newTransaction = new Transaction(pk++, Integer.parseInt(fields[2]), this.products.getEntity(Integer.parseInt(fields[2])));
                    i = 0;
                    for (Consumer<Object> consumer : newTransaction.getLoadOrder()) {
                        consumer.accept(fields[i]);
                        i++;
                    }

                    this.entities.add(newTransaction);
                    System.out.println(SUCCESS_PREFIX + "Line '" + line + "' was successfully validated and parsed.");
                } else {
                    System.out.println(WARNING_PREFIX + "Corrupt data. Line '" + line + "' failed validation.");
                }
                line = reader.readLine();
            }
        } catch(IOException ex) {
            //warnings.add(FATAL_PREFIX + "IOException: '" + ex + "'.");
        }
    }

    @Override
    public ArrayList<Transaction> getEntities() {
        return this.entities;
    }

    @Override
    public Transaction getLatestEntity() {
        try {
            return this.entities.get(this.entities.size() - 1);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(WARNING_PREFIX + "OutOfBounds thrown when fetching latest entity of class " +
                Transaction.class.getName() + ". Did another class delete a entity before calling this method?");
        } catch (Exception ex) {
            System.out.println(WARNING_PREFIX + "Encountered an error when fetching latest entity of class " +
                Transaction.class.getName() + ".");
        }
        return null;
    }

    @Override
    public Transaction getEntity(final int UID) {
        try {
            return this.entities.get(UID);
        } catch (IndexOutOfBoundsException exception) {
            System.out.println(WARNING_PREFIX + "Could not find entity of type " + Transaction.class.getName() +
                " with unique ID of " + UID + ".");
            return null;
        }
    }

    @Override
    public boolean entityExists(final int UID) {
        try {
            return (this.entities.get(UID) != null ? true : false);
        } catch (IndexOutOfBoundsException exception) {
            return false;
        }
    }

    @Override
    public boolean editEntity(Transaction Transaction) {
        try {
            List<String> lines = Files.readAllLines(CSV_PATH);
            final int LINE_ID = Transaction.getID();
            String[] fields = lines.get(LINE_ID).split(",");
            List<Supplier<Object>> loadOrder = Transaction.getSaveOrder();

            for (int i = 0; i < fields.length; i++) {
                if (loadOrder.get(i) != null) {
                    fields[i] = loadOrder.get(i).get().toString();
                } else
                    System.out.println(this.WARNING_PREFIX + "Transaction #" + LINE_ID + " experienced a null load Supplier method.");
            }

            lines.set(LINE_ID, String.join(",", fields));
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            Transaction.resetChangedState();
            System.out.println(this.SUCCESS_PREFIX + "Persisted changes to #" + Transaction.getID() + ".");
            notifyObservers();
            return true;
        } catch (IOException ex) {
            //
        }
        return false;
    }

    @Override
    public boolean createEntity(Transaction Transaction) {
        try {
            List<Supplier<Object>> loadOrder = Transaction.getSaveOrder();
            List<String> lines = Files.readAllLines(CSV_PATH);
            String[] fields = new String[loadOrder.size()];

            for (int i = 0; i < fields.length; i++) {
                fields[i] = loadOrder.get(i).get().toString();
            }

            lines.add(String.join(",", fields));
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            Transaction.resetChangedState();
            this.entities.add(Transaction);
            System.out.println(this.SUCCESS_PREFIX + "Persisted changes to #" + Transaction.getID() + ".");
            notifyObservers();
            return true;
        } catch (IOException ex) {
            //
        }
        return false;
    }

    @Override
    public int generateID() {
        int ID;
        try {
            ID = this.entities.get(this.entities.size() - 1).getID();
            System.out.println("Test: continuing with ID " + ID);
        } catch (NullPointerException ex) {
            ID = this.entities.size();
            System.out.println("Test: NullPointerException thrown in TransactionModel. Returning ID " + ID);
        }
        return (ID);
    }

    /* Observers */
    @Override
    public void registerObserver(ViewObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(ViewObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (ViewObserver observer : this.observers)
            observer.notifyObserver();
    }

    @Override
    public boolean deleteEntity(Transaction Transaction) {
        try {
            List<String> lines = Files.readAllLines(CSV_PATH);
            lines.remove(Transaction.getID());
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            System.out.println(this.SUCCESS_PREFIX + "Removed entity #" + Transaction.getID() + ".");
            notifyObservers();
            this.entities.remove(Transaction.getID());
            return true;
        } catch (IOException ex) {
            System.out.println(this.WARNING_PREFIX + "IOException thrown in ProductModel.");
            Debugger.output(ex, "TransactionModel");
            return false;
        } catch (Exception ex) {
            System.out.println(this.WARNING_PREFIX + "Unexpected error has occurred while deleting entity in ProductModel.");
            Debugger.output(ex, "TransactionModel");
            return false;
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