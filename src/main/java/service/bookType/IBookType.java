package service.bookType;

import model.BookType;

import java.util.ArrayList;

public interface IBookType {
    ArrayList<BookType> getAllBookTypes();
    ArrayList<BookType> checkCacheAndGetAllBookTypes();
    void saveBookTypeDataCache(String key);
}
