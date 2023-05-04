package Dao;

import Bean.BookBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookDao {
    public static ArrayList<BookBean> getBook() {
        ArrayList<BookBean> dsBook = new ArrayList<BookBean>();
        try {

            //step1: connect to database
            ConnectDB cn = new ConnectDB();
            cn.connect();

            //step2: get BookData from database
            String sql = "select * from Book";
            PreparedStatement cmd = cn.conn.prepareStatement(sql);
            ResultSet rs = cmd.executeQuery();

            //step3: save data to dsBook
            while (rs.next()) {

                String bookId = rs.getString("bookId");
                String bookName = rs.getString("bookName");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                String image = rs.getString("image");
                String bookType = rs.getString("bookType");

                dsBook.add(new BookBean(bookId, bookName, author, quantity, price, image, bookType));
            }

            //step4: close connection and return dsBook
            rs.close();
            cn.conn.close();

            return dsBook;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BookBean addBook(String bookId, String bookName, String author, int quantity, int price, String image
            , String bookType) {

        //step1: Connect to database
        ConnectDB cn = new ConnectDB();
        cn.connect();

        //step2:
        if (bookId.equals("")) {
            return null;
        } else {
            String sql = "insert into Book(bookId, bookName, author, quantity, price, image, bookType)\r\n" +
                    "values(?,?,?,?,?,?,?);";
            BookBean insertedBook = null;
            try {
                PreparedStatement ps = cn.conn.prepareStatement(sql);
                ps.setString(1, bookId);
                ps.setString(2, bookName);
                ps.setString(3, author);
                ps.setInt(4, quantity);
                ps.setInt(5, price);
                ps.setString(6, image);
                ps.setString(7, bookType);
                insertedBook = new BookBean(bookId, bookName, author, quantity, price, image, bookType);

                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return insertedBook;
        }
    }

    public static BookBean updateBook(String BookId, String bookName, String author, int quantity, int price, String image
            , String bookType) {
        //step1: Connect to database
        ConnectDB cn = new ConnectDB();
        cn.connect();

        //step2:
        if (BookId.equals("")) {
            return null;
        } else {
            String sql = "update Book\r\n"
                    + "set bookName=?, author=?, quantity=?, price=?, image=?, bookType=?\r\n"
                    + "where bookId=?";
            BookBean updatedBook = null;
            try {
                PreparedStatement ps = cn.conn.prepareStatement(sql);
                ps.setString(1, bookName);
                ps.setString(2, author);
                ps.setInt(3, quantity);
                ps.setInt(4, price);
                ps.setString(5, image);
                ps.setString(6, bookType);
                ps.setString(7, BookId);
                updatedBook = new BookBean(BookId, bookName, author, quantity, price, image, bookType);

                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return updatedBook;
        }
    }

    public static boolean deleteBook(String bookId) {
        //step1: Connect to database
        ConnectDB cn = new ConnectDB();
        cn.connect();

        //step2:
        String sql = "delete from Book where BookId=?";
        try {
            PreparedStatement ps = cn.conn.prepareStatement(sql);
            ps.setString(1, bookId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayList<BookBean> ds = new ArrayList<BookBean>();
        ds = BookDao.getBook();
        for (BookBean b : ds) {
            System.out.println(b.getBookId() + "--" + b.getBookName() + "--" + b.getAuthor() + "--" + b.getQuantity() +
                    "--" + b.getPrice() + "--" + b.getImage() + "--" + b.getBookType());
        }

//        //Test create function
//        BookBean iBook = BookDao.addBook("bgd02", "SGK Hóa học 12", "Trần Tiến", 12,
//                70000, "BookImages/b2.jpg", "GD");
//
//        System.out.println(iBook.getBookName());

//        //Test delete function
//        Boolean test = BookDao.deleteBook("btk02");
//        if(test==true){
//            System.out.println("delete Book with bookId = btk02 success!");
//        } else{
//            System.out.println("delete Book failed");
//        }

//        //Test update function
//        BookBean uBook = BookDao.updateBook("bds05","Đời sống hằng ngày update", "Trần B update"
//        , 22, 22000, "BookImages/b5.jpg", "LP");
//
//        System.out.println(uBook.getBookName());


    }
}
