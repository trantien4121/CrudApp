package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {

    public static Connection conn;
    static String url = "jdbc:mysql://localhost:3306/crudbook";
    static String user = "root";
    static String password = "123456";

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

//    public static void main(String[] args) {
//        ConnectDb cn = new ConnectDb();
//        cn.connect();
//    }
}
