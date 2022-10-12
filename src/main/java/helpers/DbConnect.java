package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    private static String url = "jdbc:postgresql://localhost:5432/school";
    private  static String usuario = "postgres";
    private static  String senha = "123";
    private static Connection connection;


    public static  Connection getConnect(){
        try {
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection(url,usuario,senha);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  connection;
    }
}
