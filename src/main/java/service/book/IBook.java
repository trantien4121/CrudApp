package service.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Book;

import java.util.ArrayList;

public interface IBook {

    ArrayList<Book> getAllBooks();

    ArrayList<Book> checkCacheAndGetAllBooks();

    void saveBookDataCacheString(String key);

    Book getByBookId(String bookId);

    Book addBook(String bookId, String bookName, String author, int quantity, int price, String image
            , String bookType);

    Book updateBook(String bookId, String bookName, String author, int quantity, int price, String image
            , String bookType);

    boolean deleteBook(String BookId);

    ArrayList<Book> getBooksWithPagination(int pageNo, int pageSize, String searchValue, String filterValue);

    ArrayList<Book> searchBook(String key);

    ArrayList<Book> filterBook(String filterVal);

    ArrayList<Book> filterAndSearchBook(String key, String filterVal);
}
