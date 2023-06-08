package Controller;

import model.Book;
import service.book.IBook;
import service.book.IBookImpl;

import java.util.ArrayList;

public class TestController {
    private IBook bookImpl = new IBookImpl();
    private ArrayList<Book> dsBook = new ArrayList<Book>();

    protected void getData(){
        ArrayList<Book> lstBook = bookImpl.checkCacheAndGetAllBooks();
        for(Book b: lstBook){
            System.out.println(b.getBookId() + " -- " + b.getBookName() + " -- " + b.getAuthor());
        }
    }

    public static void main(String[] args) {
        TestController testController = new TestController();
        testController.getData();
    }
}
