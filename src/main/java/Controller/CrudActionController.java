package Controller;

import model.Book;
import model.BookType;
import service.book.IBook;
import service.book.IBookImpl;
import service.bookType.IBookType;
import service.bookType.IBookTypeImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CrudActionController", urlPatterns = "/Crud")
public class CrudActionController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IBook bookImpl = new IBookImpl();

    private IBookType bookTypeImpl = new IBookTypeImpl();

    private ArrayList<Book> dsBook = new ArrayList<Book>();

    private ArrayList<BookType> dsBookType = new ArrayList<BookType>();

    public CrudActionController(){}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.sendRedirect("/home");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request, response);
    }
}
