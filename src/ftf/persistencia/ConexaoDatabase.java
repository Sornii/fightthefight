package ftf.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDatabase {
    private Connection connection;
    
    public ConexaoDatabase() {
        
    }
    
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if(connection == null) {
            conectar();
        }
        return connection;
    }
    
    public void conectar() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/fightthefight2", "root", "123456");
    }
}
