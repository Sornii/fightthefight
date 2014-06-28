package ftf.persistencia;

import ftf.modelo.ModelBase;
import ftf.persistencia.util.ClassUtil;
import ftf.persistencia.util.ComandosSqlUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseService<T extends ModelBase> {

    private final ConexaoDatabase conexaoDatabase = new ConexaoDatabase();
    private final Class<T> thisClass;

    protected BaseService(Class<T> thisClass) {
        this.thisClass = thisClass;
    }

    private Connection abrirConexao() {
        try {
            return conexaoDatabase.getConnection();
        } catch (SQLException ex) {
            System.out.println("Não foi possível conectar ao banco de dados.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Não foi possível encontrar a classe para o banco de dados.");
        }
        return null;
    }
    
    private void fecharConexao(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    public void salvar(T value) {
        List<Field> fields = ClassUtil.getCampos(thisClass);

        String campos = getNomesCampos(fields);
        String valores = getValores(fields, value);

        String comando = ComandosSqlUtil.SQL_INSERT.getComando();
        String format = String.format(comando, getNomeTabela(), campos, valores);
        System.out.println(format);

        Connection conexao = abrirConexao();
        T auxValue = getUnico(value.getId());

        try {
            if (auxValue != null) {
                String comandoUpdate = ComandosSqlUtil.SQL_UPDATE.getComando();
                String formatUpdate = String.format(comandoUpdate, getNomeTabela(), getUpdateNomesCampos(fields, value), value.getId());
                System.out.println(formatUpdate);
                Statement stmt = conexao.createStatement();
                stmt.executeUpdate(formatUpdate);
            } else {
                Statement stmt = conexao.createStatement();
                ResultSet resultSet = stmt.executeQuery(format);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            fecharConexao(conexao);
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
                System.out.println("Reflexão falhou");
            }
        }
        return novaInstancia;
    }

    public T getUnico(Integer id) {
        Connection conexao = abrirConexao();

        if (conexao == null) {
            return null;
        }

        String comando = ComandosSqlUtil.SQL_SELECT_ID.getComando();
        String format = String.format(comando, getNomeTabela(), id);
        System.out.println(format);

        try {
            Statement createStatement = conexao.createStatement();
            ResultSet resultSet = createStatement.executeQuery(format);
            if (resultSet.next()) {
                return montarUnico(resultSet);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao realizar consulta");
        } finally {
            fecharConexao(conexao);
        }

        return null;
    }

    public List<T> getListagem() {
        Connection conexao = abrirConexao();

        if (conexao == null) {
            return null;
        }

        String comando = ComandosSqlUtil.SQL_SELECT.getComando();
        String format = String.format(comando, getNomeTabela());
        System.out.println(format);

        List<T> lista = new ArrayList<>();

        try {
            Statement createStatement = conexao.createStatement();
            ResultSet resultSet = createStatement.executeQuery(format);
            while (resultSet.next()) {
                T montarUnico = montarUnico(resultSet);
                lista.add(montarUnico);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println("Erro ao realizar consulta");
        } finally {
            fecharConexao(conexao);
        }

        return null;
    }

    private String getNomeTabela() {
        return ClassUtil.getNomeTabela(thisClass);
    }

    private String getNomesCampos(Field[] fields) {
        if (fields.length != 0) {
            String campos = "";
            for (Field field : fields) {
                campos += field.getName() + ",";
            }
            campos = campos.substring(0, campos.length() - 1);
            return campos;
        } else {
            return null;
        }
    }

    private String getUpdateNomesCampos(List<Field> fields, T value) {
        if (!fields.isEmpty()) {
            String valores = "";
            for (Field field : fields) {
                String valor = getValor(field, value);
                if (valor != null) {
                    valores += field.getName() + "=" + valor + ",";
                }
            }
            valores = valores.substring(0, valores.length() - 1);
            return valores;
        } else {
            return null;
        }
    }

    private String getNomesCampos(List<Field> fields) {
        return getNomesCampos(fields.toArray(new Field[fields.size()]));
    }

    private String getValores(Field[] fields, T value) {
        if (fields.length != 0) {
            String valores = "";
            for (Field field : fields) {
                String valor = getValor(field, value);
                if (valor != null) {
                    valores += valor + ",";
                }
            }
            valores = valores.substring(0, valores.length() - 1);
            return valores;
        } else {
            return null;
        }
    }

    private String getValores(List<Field> fields, T value) {
        return getValores(fields.toArray(new Field[fields.size()]), value);
    }

    private String getValor(Field field, T value) {
        String name = field.getName();
        Class<?> type = field.getType();

        Method method;

        try {
            method = thisClass.getMethod(ClassUtil.getMethodGet(name));

            if (type.equals(Integer.class)) {
                return method.invoke(value).toString();
            } else if (type.equals(String.class)) {
                return "'" + (String) method.invoke(value) + "'";
            }
        } catch (Exception ex) {
            System.out.println("Reflexão falhou");
            return null;
        }
        return null;
    }
}
