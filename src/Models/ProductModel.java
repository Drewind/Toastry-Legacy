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
import Interfaces.ModelEntityObserver;
import Interfaces.ViewActionObserver;
import Utilities.Debugger;

/**
 * ProductModel
 * A class to handle reading and writing of Product entities.
 */
public class ProductModel implements IModelInterface<Product>, ModelEntityObserver<Product> {
    private final File CSV_FILE = new File("productList.txt");
    private final Path CSV_PATH = Paths.get("productList.txt");
    private final String WARNING_PREFIX = "\n<WARNING>: ";
    private final String SUCCESS_PREFIX = "\nSUCCESS: ";
    private ArrayList<Product> entities = new ArrayList<>();
    private ArrayList<ViewActionObserver<Product>> observers = new ArrayList<>();

    /**
     * Loads into the application all of the products saved. Used during initialization.
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
                if (lineIsValid(fields, 5)) {
                    Product newProduct = new Product(pk++);
                    i = 0;
                    for (Consumer<Object> consumer : newProduct.getLoadOrder()) {
                        consumer.accept(fields[i]);
                        i++;
                    }

                    this.entities.add(newProduct);
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
    public ArrayList<Product> getEntities() {
        return this.entities;
    }

    @Override
    public Product getLatestEntity() {
        try {
            return this.entities.get(this.entities.size() - 1);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(WARNING_PREFIX + "OutOfBounds thrown when fetching latest entity of class " +
                Product.class.getName() + ". Did another class delete a entity before calling this method?");
        } catch (Exception ex) {
            System.out.println(WARNING_PREFIX + "Encountered an error when fetching latest entity of class " +
                Product.class.getName() + ".");
        }
        return null;
    }

    @Override
    public Product getEntity(final int UID) {
        try {
            return this.entities.get(UID);
        } catch (IndexOutOfBoundsException exception) {
            System.out.println(WARNING_PREFIX + "Could not find entity of type " + Product.class.getName() +
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
    public boolean editEntity(Product product) {
        try {
            List<String> lines = Files.readAllLines(CSV_PATH);
            final int LINE_ID = product.getID();
            String[] fields = lines.get(LINE_ID).split(",");
            List<Supplier<Object>> loadOrder = product.getSaveOrder();

            System.out.println("\n[ TEST ] ID " + LINE_ID + ". FIELDS LENGTH " + fields.length);
            for (int i = 0; i < fields.length; i++) {
                if (loadOrder.get(i) != null) {
                    System.out.println("\n[ TEST ] Attempting load operation.. field '" + fields[i] + ", for " + i);
                    fields[i] = loadOrder.get(i).get().toString();
                } else
                    System.out.println(this.WARNING_PREFIX + "Product #" + LINE_ID + " experienced a null load Supplier method.");
            }

            lines.set(LINE_ID, String.join(",", fields));
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            product.resetChangedState();
            System.out.println(this.SUCCESS_PREFIX + "Persisted changes to #" + product.getID() +
                " " + product.getProductName());
            for (ViewActionObserver<Product> observer : this.observers) { observer.notifyModifiedEntity(product); }
            return true;
        } catch (IOException ex) {
            //
        }
        return false;
    }

    @Override
    public boolean createEntity(Product product) {
        try {
            List<Supplier<Object>> loadOrder = product.getSaveOrder();
            List<String> lines = Files.readAllLines(CSV_PATH);
            String[] fields = new String[loadOrder.size()];

            for (int i = 0; i < fields.length; i++) {
                fields[i] = loadOrder.get(i).get().toString();
            }

            lines.add(String.join(",", fields));
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            product.resetChangedState();
            this.entities.add(product);
            System.out.println(this.SUCCESS_PREFIX + "Persisted changes to #" + product.getID() +
                " " + product.getProductName());
            for (ViewActionObserver<Product> observer : this.observers) { observer.notifyNewEntity(product); }
            return true;
        } catch (IOException ex) {
            //
        }
        return false;
    }

    @Override
    public int generateID() {
        // final List<String> lines = Files.readAllLines(CSV_PATH);
        // return (lines.size() + 1);
        System.out.println("<DEBUG: GenerateID. Total " + this.entities.size());
        return (this.entities.size());
    }

    /* Observers */
    @Override
    public void registerObserver(ViewActionObserver<Product> observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(ViewActionObserver<Product> observer) {
        this.observers.remove(observer);
    }

    @Override
    public boolean deleteEntity(Product product) {
        try {
            List<String> lines = Files.readAllLines(CSV_PATH);
            lines.remove(product.getID());
            Files.write(CSV_PATH, lines, StandardCharsets.UTF_8);
            System.out.println(this.SUCCESS_PREFIX + "Removed entity #" + product.getID() + " " + product.getProductName());
            for (ViewActionObserver<Product> observer : this.observers) { observer.notifyRemovedEntity(product); }
            this.entities.remove(product.getID());
            return true;
        } catch (IOException ex) {
            System.out.println(this.WARNING_PREFIX + "IOException thrown in ProductModel.");
            Debugger.output(ex, "ProductModel");
            return false;
        } catch (Exception ex) {
            System.out.println(this.WARNING_PREFIX + "Unexpected error has occurred while deleting entity in ProductModel.");
            Debugger.output(ex, "ProductModel");
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