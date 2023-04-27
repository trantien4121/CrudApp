package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
    public Connection conn;
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/crudbook";
            String user = "root";
            String password = "123456";

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connect database success!");

        } catch (SQLException e) {
            System.out.println("error to connect Database!");
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ConnectDB cn = new ConnectDB();
        cn.connect();
    }
}
