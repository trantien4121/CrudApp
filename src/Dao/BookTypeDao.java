package Dao;

import Bean.BookTypeBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookTypeDao {
    public static ArrayList<BookTypeBean> getBookType(){
        ArrayList<BookTypeBean> dsBookType = new ArrayList<BookTypeBean>();
        try {

            //step1: connect to database
            ConnectDB cn = new ConnectDB();
            cn.connect();

            //step2: getData from database
            String sql = "select * from BookType";
            PreparedStatement cmd = cn.conn.prepareStatement(sql);
            ResultSet rs = cmd.executeQuery();

            //step3: save data to dsBookType
            while(rs.next()) {
                String bookType = rs.getString("bookType");
                String bookTypeName = rs.getString("bookTypeName");
                dsBookType.add(new BookTypeBean(bookType, bookTypeName));
            }

            //step4: Close connection and return dsBookType
            rs.close();
            cn.conn.close();

            return dsBookType;

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        ArrayList<BookTypeBean> ds = BookTypeDao.getBookType();
        for(BookTypeBean ele: ds){
            System.out.println(ele.getBookType() + "--" + ele.getBookTypeName());
        }
    }
}
