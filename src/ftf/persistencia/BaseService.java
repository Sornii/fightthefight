package ftf.persistencia;

import ftf.modelo.ModelBase;
import ftf.persistencia.util.CampoValor;
import ftf.persistencia.util.ClassUtil;
import static ftf.persistencia.util.ClassUtil.getCamposValores;
import ftf.persistencia.util.ComandosSqlUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseService<T extends ModelBase> {

    private final Connection connection = ConexaoDatabase.getConnection();
    private final Class<T> thisClass;

    protected BaseService(Class<T> thisClass) {
        this.thisClass = thisClass;
    }

    public void salvar(T value) {

        List<CampoValor> camposValores = ClassUtil.getCamposValores(value);

        String[] campoValor = getInsertValores(value);
        String campo = campoValor[0];
        String valor = campoValor[1];

        String comando = ComandosSqlUtil.SQL_INSERT.getComando();
        String format = String.format(comando, ClassUtil.getNomeTabela(thisClass), campo, valor);
        System.out.println(format);

        T auxValue = getUnico(value.getId());
        
        try {
            if (auxValue != null) {
                String comandoUpdate = ComandosSqlUtil.SQL_UPDATE.getComando();
                String formatUpdate = String.format(comandoUpdate, ClassUtil.getNomeTabela(thisClass), getUpdateValores(camposValores), value.getId());
                System.out.println(formatUpdate);
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(formatUpdate);
            } else {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(format);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private T montarUnico(ResultSet resultSet) throws SQLException {
        T novaInstancia = null;

        try {
            novaInstancia = thisClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("Não foi possível construir uma instância");
        }

        List<Field> fields = ClassUtil.getCampos(thisClass);
        for (Field field : fields) {
            String setMethod = ClassUtil.getMethodSet(field.getName());
            Class<?> type = field.getType();

            try {
                if (type == Integer.class) {
                    int aInt = resultSet.getInt(field.getName());
                    Method method = thisClass.getMethod(setMethod, Integer.class);
                    method.invoke(novaInstancia, aInt);
                } else if (type == String.class) {
                    String string = resultSet.getString(field.getName());
                    Method method = thisClass.getMethod(setMethod, String.class);
                    method.invoke(novaInstancia, string);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return novaInstancia;
    }

    public T getUnico(Integer id) {
        String comando = ComandosSqlUtil.SQL_SELECT_ID.getComando();
        String format = String.format(comando, ClassUtil.getNomeTabela(thisClass), id);
        System.out.println(format);

        try {
            Statement createStatement = connection.createStatement();
            ResultSet resultSet = createStatement.executeQuery(format);
            if (resultSet.next()) {
                return montarUnico(resultSet);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    public T getCustomUnico(String where) {
        String comando = ComandosSqlUtil.SQL_SELECT.getComando();
        String format = String.format(comando, ClassUtil.getNomeTabela(thisClass));
        format +=  " where " + where;
        System.out.println(format);
        
        try {
            Statement createStatement = connection.createStatement();
            ResultSet resultSet = createStatement.executeQuery(format);
            if(resultSet.next()){
                return montarUnico(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<T> getCustomListagem(String where) {
        String comando = ComandosSqlUtil.SQL_SELECT.getComando();
        String format = String.format(comando, ClassUtil.getNomeTabela(thisClass));
        format += " where " + where;
        System.out.println(format);
        
        List<T> lista = new ArrayList<>();
        
        try {
            Statement createStatement = connection.createStatement();
            ResultSet resultSet = createStatement.executeQuery(format);
            while(resultSet.next()){
                lista.add(montarUnico(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }

    public List<T> getListagem() {
        String comando = ComandosSqlUtil.SQL_SELECT.getComando();
        String format = String.format(comando, ClassUtil.getNomeTabela(thisClass));
        System.out.println(format);

        List<T> lista = new ArrayList<>();

        try {
            Statement createStatement = connection.createStatement();
            ResultSet resultSet = createStatement.executeQuery(format);
            while (resultSet.next()) {
                T montarUnico = montarUnico(resultSet);
                lista.add(montarUnico);
            }
            return lista;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private String getUpdateValores(List<CampoValor> camposValores) {
        String updateValores = "";

        for (Iterator<CampoValor> it = camposValores.iterator(); it.hasNext();) {
            CampoValor campoValor = it.next();
            String set = campoValor.getCampo() + "=" + campoValor.getValorString();
            updateValores += set;
            if (it.hasNext()) {
                updateValores += ", ";
            }
        }

        return updateValores;
    }

    public static String[] getInsertValores(Object value) {
        List<CampoValor> camposValores = getCamposValores(value);

        String campos = "";
        String valores = "";

        for (Iterator<CampoValor> it = camposValores.iterator(); it.hasNext();) {
            CampoValor campoValor = it.next();

            if (!campoValor.isNull()) {
                campos += campoValor.getCampo();
                valores += campoValor.getValorString();
                if (it.hasNext()) {
                    campos += ", ";
                    valores += ", ";
                }
            }
        }

        return new String[]{campos, valores};
    }
}
