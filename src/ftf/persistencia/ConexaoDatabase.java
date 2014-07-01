package ftf.persistencia;

import com.sun.media.jfxmediaimpl.MediaDisposer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDatabase implements MediaDisposer.Disposable {
    private static Connection connection;
    
    private ConexaoDatabase() {
        
    }
    
    public static Connection getConnection(){
        if(connection == null) {
            conectar();
        }
        return connection;
    }
    
    public static void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/fightthefight2", "root", "123456");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao abrir conexão");
        }
    }
    
    public static void desconectar() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar conexão");
        }
    }

    @Override
    public void dispose() {
        desconectar();
    }
}
