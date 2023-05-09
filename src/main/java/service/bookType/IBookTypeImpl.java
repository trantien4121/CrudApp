package service.bookType;

import Bean.BookTypeBean;
import common.ConnectDb;
import model.Book;
import model.BookType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IBookTypeImpl implements IBookType{

    Connection connection = ConnectDb.connect();

    public ArrayList<BookType> getAllBookTypes() {
        ArrayList<BookType> dsBookType = new ArrayList<BookType>();
        try {
            String sql = "select * from BookType";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String bookType = rs.getString("bookType");
                String bookTypeName = rs.getString("bookTypeName");

                dsBookType.add(new BookType(bookType, bookTypeName));
            }

            rs.close();
            ps.close();

//            connection.close();

            return dsBookType;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        IBookType iBookType = new IBookTypeImpl();
        ArrayList<BookType> dsBookTypes = iBookType.getAllBookTypes();
        for(BookType bT: dsBookTypes){
            System.out.println(bT.getBookType() + " -- " + bT.getBookTypeName());
        }
    }
}
