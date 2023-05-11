package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {

    public static Connection conn;

    static String url = (String) ReadPropertiesLibrary.readFileProperties().get("url");
    static String user = (String) ReadPropertiesLibrary.readFileProperties().get("user");
    static String password = (String) ReadPropertiesLibrary.readFileProperties().get("password");

    public static Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connect database success!");
            return conn;

        } catch (SQLException e) {
            System.out.println("error to connect Database!");
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
            e.printStackTrace();

        }
        return null;
    }

}
