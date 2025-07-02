package DataAccess;

import Model.Bill;
import Model.Client;

/**
 * Data Access Object for the {@link Bill} model.
 * Inherits basic create and read operations form{@link AbstractDAO}.
 * Sets the correct table name from the database and disables delete and update operations.
 */
public class BillDAO extends AbstractDAO<Bill> {
    @Override
    protected String getTableName(){
        return "log";
    }
    @Override
    public Bill update(Bill bill) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public Bill delete(Bill bill) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
