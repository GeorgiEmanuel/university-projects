package Tools;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class responsible to populate a table with data from a list of objects.
 */
public class TableReflection {
    /**
     * Populates the table with data form a list of objects.
     * Object's fields are used as columns, and one object corresponds to a row.
     *
     * @param table The table that will be populated.
     * @param objectList The list of objects whose data will be displayed.
     * @param <T> The type of objects contained in the list.
     */
    public static <T> void populateTable(JTable table, List<T> objectList) {
        if(objectList == null || objectList.isEmpty()){
            return;
        }
        T firstObject = objectList.getFirst();
        List<Field> fields = Arrays.asList(firstObject.getClass().getDeclaredFields());

        String[] columnNames = new String[fields.size()];
        for(int i = 0; i < columnNames.length; i++){
            columnNames[i] = fields.get(i).getName();
        }
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for(T object : objectList){
            Object[] rowData = new Object[fields.size()];
            for(int i = 0; i < columnNames.length; i++){
                fields.get(i).setAccessible(true);
                try{
                    rowData[i] = fields.get(i).get(object);
                }catch(IllegalAccessException e){
                    rowData[i] = null;
                }

            }
            tableModel.addRow(rowData);
        }

        table.setModel(tableModel);

    }
}
