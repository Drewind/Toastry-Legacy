package Services.Commands;

import Entities.Product;
import Graphics.Tables.EntityTableModel;
import Interfaces.Command;
import Interfaces.EntityInterface;
import Models.IModelInterface;

public class EndOfDay implements Command {
    final EntityTableModel salesTable;
    final IModelInterface<Product> model;

    public EndOfDay(final EntityTableModel table, final IModelInterface<Product> model) {
        this.salesTable = table;
        this.model = model;
    }

    @Override
    public void execute() {
        System.out.println("Debug: execute called in EndOfDay.");
        int i = 0;
        for (EntityInterface entity : this.salesTable.getChangedEntities()) {
            System.out.println("End of Day: loop (" + i + "/" + this.salesTable.getChangedEntities().size() + "): " + entity.getID() + " was changed.");
            System.out.println("End of Day: debug view: #" + ((Product)entity).getID() + ": " + ((Product)entity).getProductName() + ".");
            this.model.editEntity((Product)entity);
            i++;
        }
        System.out.println("End of Day: FINISH with execute();");
    }
}