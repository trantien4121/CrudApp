package service.book;

import model.Book;

import java.util.ArrayList;

public interface IBook {

    public ArrayList<Book> getAllBooks();

    public Book getByBookId(String bookId);

    public Book addBook(String bookId, String bookName, String author, int quantity, int price, String image
            , String bookType);

    public Book updateBook(String bookId, String bookName, String author, int quantity, int price, String image
            , String bookType);

    public boolean deleteBook(String BookId);

    public ArrayList<Book> getAllBooksPagination(int pageNo, int pageSize);

    public ArrayList<Book> getListBooksPagination(int pageNo, int pageSize, ArrayList<Book> dsBooks);

    public ArrayList<Book> searchBook(String key);

    public ArrayList<Book> filterBook(String filterVal);

    public ArrayList<Book> filterAndSearchBook(String key, String filterVal);
}