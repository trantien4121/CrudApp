package Dao;

import Bean.BookBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookDao {
    public static ArrayList<BookBean> getBook(){
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
            while(rs.next()){

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

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BookBean addBook(String bookId, String bookName, String author, int quantity, int price, String image
    , String bookType){

        //step1: Connect to database
        ConnectDB cn = new ConnectDB();
        cn.connect();

        //step2:
        if(bookId.equals("")){
            return null;
        }
        else{
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

    public static void main(String[] args) {
        ArrayList<BookBean> ds = new ArrayList<BookBean>();
        ds = BookDao.getBook();
        for(BookBean b : ds){
            System.out.println(b.getBookId() + "--" + b.getBookName() + "--" + b.getAuthor() + "--" + b.getQuantity() +
                    "--" + b.getPrice() + "--" + b.getImage() + "--" + b.getBookType());
        }

        BookBean iBook = BookDao.addBook("btk01", "Sách tham khảo", "Trần Tiến", 12,
                50000, "BookImages/b7.jpg", "TK");
        System.out.println(iBook.getBookName());
    }
}
