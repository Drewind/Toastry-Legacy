package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

import Constants.LogLevel;
import Entities.Product;

/**
 * ProductModel
 * A class to handle reading and writing of Product entities.
 */
public class ProductModel extends Model {
    private final HashMap<String, Product> products = new HashMap<String, Product>();

    public ProductModel() {
        super("ProductList.txt", new Product("0").getLoadOrder().size());
        loadEntities();
    }

    public ProductModel(final String CSV_PATH) {
        super(CSV_PATH, new Product("0").getLoadOrder().size());
        loadEntities();
    }

    public Product getProduct(final String UID) {
        return this.products.get(UID);
    }

    public boolean addProduct(Product product) {
        try {
            if (this.products.containsKey(product.getID())) {
                super.addLogMessage(LogLevel.WARNING, "Product (" + product.getID() + ") in model already exists.");
                return false;
            }

            this.products.put(product.getID(), product);
            super.addEntity(product);
            return true;
        } catch (Exception ex) {
            super.addLogMessage(LogLevel.WARNING, "An error occurred when persisting product (" + product.getID() + ") in model.\n\t" + ex, true);
        }
        return false;
    }

    public boolean editProduct(Product product) {
        try {
            if (this.products.containsKey(product.getID())) {
                this.products.put(product.getID(), product);
                super.editEntity(product);
                return true;
            }

            super.addLogMessage(LogLevel.WARNING, "Could not find product (" + product.getID() + ") in model.");
            return false;
        } catch (Exception ex) {
            super.addLogMessage(LogLevel.WARNING, "An error occurred when editing product (" + product.getID() + ") in model.\n\t" + ex, true);
        }
        return false;
    }

    public boolean deleteProduct(Product product) {
        try {
            if (this.products.containsKey(product.getID())) {
                this.products.remove(product.getID());
                super.deleteEntity(product);
                return true;
            }

            super.addLogMessage(LogLevel.WARNING, "Could not find product (" + product.getID() + ") in model.", true);
            return false;
        } catch (Exception ex) {
            super.addLogMessage(LogLevel.WARNING, "An error occurred when deleting product (" + product.getID() + ") in model.\n\t" + ex, true);
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
                    super.addLogMessage(LogLevel.WARNING, "Could not parse line \n'" + line + "'' in product model; line was invalid.");
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
                        + "\n\t" + this.products.size() + " successfully entities parsed."
                        + "\n\t" + failedToParse + " entiites failed to parsed.", true);
        }
    }

    private boolean parseEntity(final String line) {
        int i = 0;

        try {
            String[] fields = line.split(",");
            Product product = new Product(fields[0]);
            product.setOriginalData(line);

            i = 0;
            for (Consumer<Object> consumer : product.getLoadOrder()) {
                consumer.accept(fields[i++]);
            }

            this.products.put(product.getID(), product);
        } catch (NumberFormatException ex) {
            super.addLogMessage(LogLevel.FATAL, "Could not parse products in model.", true);
            return false;
        }

        return true;
    }

    /**
     * Retrieves ALL entities from this model.
     * @return Entity
     */
    public Collection<Product> getEntities() {
        return this.products.values();
    }

    /**
     * Retrieves ALL entities from this model.
     * @return Entity
     */
    public Product getEntity(final String UID) {
        return this.products.get(UID);
    }

    /**
     * Checks to see if the given UID for a entity in this model exists. Returns true if found.
     * @param UID unique ID of the entity
     * @return boolean, true if found
     */
    public boolean entityExists(final String UID) {
        return (this.products.containsKey(UID));
    }
}