package service.book;

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

            return (result > 0) ? updatedBook : null;

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

    public ArrayList<Book> getBooksWithPagination(int pageNo, int pageSize, String searchValue, String filterValue){
        ArrayList<Book> dsBook = new ArrayList<Book>();
        int limit = pageSize;
        int offset = (pageNo - 1) * 5;
        String sql = "";

        try{
            ResultSet rs;
            PreparedStatement ps = null;

            if(searchValue.equals("") && filterValue.equals("")){
                sql = "select * from Book limit ? offset ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, limit);
                ps.setInt(2, offset);
                rs = ps.executeQuery();
            }
            else if(searchValue.equals("") && !filterValue.equals("")){
                sql = "select * from Book where bookType=? limit ? offset ?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, filterValue);
                ps.setInt(2, limit);
                ps.setInt(3, offset);
                rs = ps.executeQuery();
            }
            else if(!searchValue.equals("") && filterValue.equals("")){
                sql = "select * from Book where bookName like ? or author like ? limit ? offset ?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, "%" + searchValue + "%");
                ps.setString(2, "%" + searchValue + "%");
                ps.setInt(3, limit);
                ps.setInt(4, offset);
                rs = ps.executeQuery();
            }
            else{
                sql = "select * from Book  where bookType=? and (bookName like ? or author like ?) limit ? offset ?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, filterValue);
                ps.setString(2, "%" + searchValue + "%");
                ps.setString(3, "%" + searchValue + "%");
                ps.setInt(4, limit);
                ps.setInt(5, offset);
                rs = ps.executeQuery();
            }

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
        try {
            String sql = "select * from Book where bookName like ? or author like ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String bookId = rs.getString("bookId");
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                String image = rs.getString("image");
                String bookType = rs.getString("bookType");

                dsBookSearch.add(new Book(bookId, bookName, author, quantity, price, image, bookType));
            }

            rs.close();
            ps.close();

            return dsBookSearch;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Book> filterBook(String filterVal){
        ArrayList<Book> dsBookFilter = new ArrayList<Book>();
        try {
            String sql = "select * from Book where bookType=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, filterVal);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String bookId = rs.getString("bookId");
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                String image = rs.getString("image");
                String bookType = rs.getString("bookType");

                dsBookFilter.add(new Book(bookId, bookName, author, quantity, price, image, bookType));
            }

            rs.close();
            ps.close();

            return dsBookFilter;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

}
