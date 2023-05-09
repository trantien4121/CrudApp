package service.book;

import Bean.BookBean;
import Dao.ConnectDB;
import common.ConnectDb;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IBookImpl implements IBook {

    Connection connection = ConnectDb.connect();

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> dsBook = new ArrayList<Book>();
        try {
            String sql = "select * from Book";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String bookId = rs.getString("bookId");
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                String image = rs.getString("image");
                String bookType = rs.getString("bookType");

                dsBook.add(new Book(bookId, bookName, author, quantity, price, image, bookType));
            }

            rs.close();
            ps.close();
//            connection.close();

            return dsBook;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book getByBookId(String bookId) {
        Book book = null;
        try {
            String sql = "select * from Book where bookId=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                String image = rs.getString("image");
                String bookType = rs.getString("bookType");

                book = new Book(bookId, bookName, author, quantity, price, image, bookType);
            }

            rs.close();
            ps.close();
//            connection.close();

            return book;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book addBook(String bookId, String bookName, String author, int quantity, int price, String image
            , String bookType) {
        Book addedBook = null;
        try{
            String sql = "insert into Book(bookId, bookName, author, quantity, price, image, bookType)\r\n" +
                    "values(?,?,?,?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bookId);
            ps.setString(2, bookName);
            ps.setString(3, author);
            ps.setInt(4, quantity);
            ps.setInt(5, price);
            ps.setString(6, image);
            ps.setString(7, bookType);

            addedBook = new Book(bookId, bookName, author, quantity, price, image, bookType);

            int result = ps.executeUpdate();
            ps.close();
//            connection.close();

            return (result > 0) ? addedBook : null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Book updateBook(String bookId, String bookName, String author, int quantity, int price, String image
            , String bookType) {
        Book updatedBook = null;
        try{
            String sql = "update Book\r\n"
                    + "set bookName=?, author=?, quantity=?, price=?, image=?, bookType=?\r\n"
                    + "where bookId=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bookName);
            ps.setString(2, author);
            ps.setInt(3, quantity);
            ps.setInt(4, price);
            ps.setString(5, image);
            ps.setString(6, bookType);
            ps.setString(7, bookId);
            updatedBook = new Book(bookId, bookName, author, quantity, price, image, bookType);

            int result = ps.executeUpdate();
            ps.close();
//            connection.close();

            if(result > 0){
                System.out.println("Update new book success!");
                return updatedBook;
            }
            else{
                System.out.println("Update book Failed!");
                return null;
            }

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteBook(String bookId) {
        String sql = "delete from Book where BookId=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bookId);

            int result = ps.executeUpdate();
            ps.close();
//            connection.close();

            if(result > 0){
                System.out.println("delete Book with bookId = " + bookId + " success!");
                return true;
            }
            else{
                System.out.println("delete Book Failed!");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<Book> getAllBooksPagination(int pageNo, int pageSize){
        ArrayList<Book> dsBook = new ArrayList<Book>();
        int limit = pageSize;
        int offset = (pageNo - 1) * 5;
        try{
            String sql = "select * from Book limit ? offset ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();

            //step3: save Data to dsBooks
            while(rs.next()){
                String bookId = rs.getString("bookId");
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                String image = rs.getString("image");
                String bookType = rs.getString("bookType");

                dsBook.add(new Book(bookId, bookName, author, quantity, price, image, bookType));
            }
            rs.close();
            ps.close();

            return dsBook;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Book> searchBook(String key){
        ArrayList<Book> dsBookSearch = new ArrayList<Book>();
        IBook bookImpl = new IBookImpl();
        ArrayList<Book> dsBook = new ArrayList<Book>();
        dsBook = bookImpl.getAllBooks();

        for(Book b: dsBook){
            if(b.getBookName().toLowerCase().contains(key.toLowerCase()) ||
                    b.getAuthor().toLowerCase().contains(key.toLowerCase()))
                dsBookSearch.add(b);
        }
        return dsBookSearch;
    }
    public ArrayList<Book> filterBook(String filterVal){
        ArrayList<Book> dsBookFilter = new ArrayList<Book>();
        IBook bookImpl = new IBookImpl();
        ArrayList<Book> dsBook = new ArrayList<Book>();
        dsBook = bookImpl.getAllBooks();

        for(Book b: dsBook){
            if(b.getBookType().equals(filterVal))
                dsBookFilter.add(b);
        }
        return dsBookFilter;
    }
    public ArrayList<Book> filterAndSearchBook(String key, String filerVal){
        ArrayList<Book> dsFilterSearch = new ArrayList<Book>();
        IBook bookImpl = new IBookImpl();
        ArrayList<Book> dsBook = new ArrayList<Book>();
        dsBook = bookImpl.getAllBooks();

        for(Book b: dsBook){
            if(filerVal.equals("")){
                dsFilterSearch = searchBook(key);
            }else{
                if(b.getBookType().equals(filerVal) && (b.getBookName().toLowerCase().contains(key.toLowerCase()) ||
                        b.getAuthor().toLowerCase().contains(key.toLowerCase()))){
                    dsFilterSearch.add(b);
                }
            }
        }
        return dsFilterSearch;
    }

    public static void main(String[] args) {
        IBook iBook = new IBookImpl();
//        ArrayList<Book> dsBook = iBook.getAllBooks();
//        for(Book b: dsBook){
//            System.out.println(b.getBookName());
//        }

//        Book b = iBook.getByBookId("bds01");
//        System.out.println(b.getBookName());

//        Book bAdd = iBook.addBook("bds08", "Cuộc sống ý nghĩa", "Nguyễn An", 12, 12000, "BookImages/b2.jpg", "DS");
//        System.out.println(bAdd.getBookName());

//        Book bUpdate = iBook.updateBook("bds08", "Thông điệp cuộc sông", "Nguyễn An", 12, 12000, "BookImages/b2.jpg", "DS");
//        System.out.println(bUpdate.getBookName());

//        iBook.deleteBook("qa");
        ArrayList<Book> dsBook = iBook.filterAndSearchBook("Cuộc", "DS");
                for(Book b: dsBook){
            System.out.println(b.getBookName() + " -- " + b.getAuthor() + " -- " + b.getBookType());
        }

    }
}
