package Services;

import java.io.IOException;

import Entities.Product;
import Models.ProductModel;

/**
 * Service to be injected into views/controllers.
 * Handles processing transacations and purchases.
 */
public class TransactionService {
    private final ProductModel model;
    
    public TransactionService(ProductModel model) {
        this.model = model;
    }

    /**
     * Handles processing orders.
     * @return boolean if false, an error ocurred.
     */
    public boolean processTransaction(Product product) {
        try {
            //
        } catch (Exception ex) {
            System.out.println("ERROR: Failed to process transaction.\n\tTransactionService");
            return false;
        }

        return true;
    }
}
