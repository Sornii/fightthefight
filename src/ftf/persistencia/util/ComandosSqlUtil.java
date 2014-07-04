package ftf.persistencia.util;

public enum ComandosSqlUtil {

    SQL_INSERT("INSERT INTO %s "
            + "(%s) "
            + "VALUES (%s)"),
    SQL_INSERT_DIRECT("INSERT INTO %s "
            + "VALUES (%s)"),
    SQL_SELECT("SELECT * "
            + "FROM %s"),
    SQL_SELECT_ID("SELECT * "
            + "FROM %s "
            + "WHERE id = %s"),
    SQL_UPDATE("UPDATE %s "
            + "SET %s "
            + "WHERE id = %s");

    private final String comandoSql;

    private ComandosSqlUtil(String comandoSql) {
        this.comandoSql = comandoSql;
    }

    public String getComando() {
        return comandoSql;
    }
}
