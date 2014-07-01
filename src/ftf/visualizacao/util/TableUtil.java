package ftf.visualizacao.util;

import ftf.persistencia.util.ClassUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableUtil {
    public static String[] getColumnNames(Object value){
        List<Field> campos = ClassUtil.getCampos(value.getClass());
        List<String> columnNames = new ArrayList<>();
        campos.stream().forEach((campo) -> {
            columnNames.add(campo.getName());
        });
        return columnNames.toArray(new String[columnNames.size()]);
    }
    
    public static String[] getColumnNames(List<Object> value){
        return getColumnNames(value.get(0));
    }
    
    public static String[][] getRowData(Object value) {
        return null;
    }
}
