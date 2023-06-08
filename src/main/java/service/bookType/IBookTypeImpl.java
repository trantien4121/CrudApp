package service.bookType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.RedisCacheManager;
import common.ConnectDb;
import model.Book;
import model.BookType;
import service.book.IBookImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class IBookTypeImpl implements IBookType{

    Connection connection = ConnectDb.connect();
    private String appCacheKey = "myAppCache";
    private String bookTypeCacheHash = appCacheKey + ":lstBookType";

    public ArrayList<BookType> getAllBookTypes() {
        ArrayList<BookType> dsBookType = new ArrayList<BookType>();
        try {
            String sql = "select * from BookType";
            if(Objects.nonNull(connection)){
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String bookType = rs.getString("bookType");
                    String bookTypeName = rs.getString("bookTypeName");

                    dsBookType.add(new BookType(bookType, bookTypeName));
                }
                rs.close();
                ps.close();
            }
            return dsBookType;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<BookType> checkCacheAndGetAllBookTypes(){

        ArrayList<BookType> lstBookType = new ArrayList<BookType>();
        Map<String, String> mapBookType = RedisCacheManager.getCacheAsHash(bookTypeCacheHash);

        if(mapBookType.size() != 0){
            for(Map.Entry<String, String> entry : mapBookType.entrySet()) {
                BookType bt = new BookType(entry.getKey(), entry.getValue());
                lstBookType.add(bt);
            }
            Collections.reverse(lstBookType);
        }else{
            saveBookTypeDataCache(bookTypeCacheHash);
        }
        return lstBookType;
    }

    public void saveBookTypeDataCache(String hashName){
        ArrayList<BookType> lstBookType = new IBookTypeImpl().getAllBookTypes();

        Map<String, String> mapBookType = new HashMap<>();

        for(BookType bt : lstBookType){
            mapBookType.put(bt.getBookType(), bt.getBookTypeName());
        }
        RedisCacheManager.setCacheAsHash(hashName, mapBookType);
    }

}
