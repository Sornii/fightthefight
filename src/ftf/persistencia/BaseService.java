package ftf.persistencia;

import ftf.modelo.Arma;
import ftf.modelo.Escudo;
import ftf.modelo.Item;
import ftf.modelo.Model;
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
import java.util.List;

public class BaseService<T extends Model> {

    private final Connection connection = ConexaoDatabase.getConnection();
    private final Class<T> thisClass;

    protected BaseService(Class<T> thisClass) {
        this.thisClass = thisClass;
    }

    public void salvar(T value) {

        List<CampoValor> camposValores = getCamposValores(value);

        String[] campoValor = getInsertValores(camposValores);
        String campo = campoValor[0];
        String valor = campoValor[1];

        T auxValue = getUnico(value.getId());

        try {
            if (auxValue != null) {
                String comandoUpdate = ComandosSqlUtil.SQL_UPDATE.getComando();
                String formatUpdate = String.format(comandoUpdate, ClassUtil.getNomeTabela(thisClass), getUpdateValores(camposValores), value.getId());
                System.out.println(formatUpdate);
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(formatUpdate);
            } else {
                String comando = ComandosSqlUtil.SQL_INSERT.getComando();
                String format = String.format(comando, ClassUtil.getNomeTabela(thisClass), campo, valor);
                System.out.println(format);
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(format, stmt.RETURN_GENERATED_KEYS);
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    value.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private T montarUnicoChild(ResultSet resultSet, Class<T> cls) throws SQLException {
        String columnName = cls.getSimpleName().toLowerCase() + "_id";
        Integer id = resultSet.getInt(columnName);

        return getUnico(id, cls);
    }

    private T montarUnico(ResultSet resultSet, Class<T> cls) throws SQLException {
        T novaInstancia = null;

        try {
            novaInstancia = cls.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("Não foi possível construir uma instância");
            return null;
        }

        List<Field> fields = ClassUtil.getCampos(cls);
        for (Field field : fields) {

            String setMethod = ClassUtil.getMethodSet(field.getName());

            Class<?> fieldClass = field.getType();
            Class<?> aux;

            try {
                if (fieldClass == Integer.class) {
                    int aInt = resultSet.getInt(field.getName());
                    Method method = cls.getMethod(setMethod, Integer.class);
                    method.invoke(novaInstancia, aInt);
                } else if (fieldClass == String.class) {
                    String string = resultSet.getString(field.getName());
                    Method method = cls.getMethod(setMethod, String.class);
                    method.invoke(novaInstancia, string);
                } else if (fieldClass.equals(Item.class)) {
                    String descriminador = resultSet.getString("descriminador");
                    Integer itemId = resultSet.getInt("item_id");
                    Method method = cls.getMethod(setMethod, Item.class);
                    switch (descriminador) {
                        case "Arma":
                            T arma = getUnico(itemId, (Class<T>) Arma.class);
                            method.invoke(novaInstancia, arma);
                            break;
                        case "Escudo":
                            T escudo = getUnico(itemId, (Class<T>) Escudo.class);
                            method.invoke(novaInstancia, escudo);
                            break;
                    }
                } else {
                    T unicoGot = montarUnicoChild(resultSet, (Class<T>) fieldClass);
                    Method method = cls.getMethod(setMethod, fieldClass);
                    method.invoke(novaInstancia, unicoGot);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return novaInstancia;
    }

    public T getUnico(Integer id) {
        return getUnico(id, thisClass);
    }

    private T getUnico(Integer id, Class<T> cls) {
        String comando = ComandosSqlUtil.SQL_SELECT_ID.getComando();
        String format = String.format(comando, ClassUtil.getNomeTabela(cls), id);
        System.out.println(format);

        try {
            Statement createStatement = connection.createStatement();
            ResultSet resultSet = createStatement.executeQuery(format);
            if (resultSet.next()) {
                return montarUnico(resultSet, cls);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public T getCustomUnico(String where) {
        String comando = ComandosSqlUtil.SQL_SELECT.getComando();
        String format = String.format(comando, ClassUtil.getNomeTabela(thisClass));
        format += " WHERE " + where;
        System.out.println(format);

        try {
            Statement createStatement = connection.createStatement();
            ResultSet resultSet = createStatement.executeQuery(format);
            if (resultSet.next()) {
                return montarUnico(resultSet, thisClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<T> getCustomListagem(String where) {
        String comando = ComandosSqlUtil.SQL_SELECT.getComando();
        String format = String.format(comando, ClassUtil.getNomeTabela(thisClass));
        format += " WHERE " + where;
        System.out.println(format);

        List<T> lista = new ArrayList<>();

        try {
            Statement createStatement = connection.createStatement();
            ResultSet resultSet = createStatement.executeQuery(format);
            while (resultSet.next()) {
                lista.add(montarUnico(resultSet, thisClass));
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
                T montarUnico = montarUnico(resultSet, thisClass);
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

        for (CampoValor campoValor : camposValores) {

            String valorcampo = campoValor.getValorString();
            if (valorcampo == null) {
                continue;
            }
            String set = campoValor.getCampo() + "=" + valorcampo;
            updateValores += set + ", ";
        }

        updateValores = updateValores.substring(0, updateValores.length() - 2);

        return updateValores;
    }

    public static String[] getInsertValores(List<CampoValor> campoValors) {

        String campos = "";
        String valores = "";

        for (CampoValor campoValor : campoValors) {
            if (!campoValor.isNull()) {
                campos += campoValor.getCampo() + ", ";
                valores += campoValor.getValorString() + ", ";
            }
        }

        if (campos.length() > 0) {
            int tamanho = campos.length();
            campos = campos.substring(0, tamanho - 2);
        }

        if (valores.length() > 0) {
            int tamanho = valores.length();
            valores = valores.substring(0, tamanho - 2);
        }

        return new String[]{campos, valores};
    }
}
