package Controller;

import Bean.BookBean;
import model.Book;
import model.BookType;
import service.book.IBook;
import service.book.IBookImpl;
import service.bookType.IBookType;
import service.bookType.IBookTypeImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "HomeController", urlPatterns = "/home")
public class HomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IBook bookImpl = new IBookImpl();

    private IBookType bookTypeImpl = new IBookTypeImpl();

    private ArrayList<Book> dsBook = new ArrayList<Book>();

    private ArrayList<BookType> dsBookType = new ArrayList<BookType>();

    public HomeController(){}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //set UTF-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "SearchAndFilter":
                showSearchAndFilter(request, response);
                break;
            default:
                showBook(request, response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        dsBook = bookImpl.getAllBooks();
        dsBookType = bookTypeImpl.getAllBookTypes();

        String page = request.getParameter("page");
        int totalPage = dsBook.size()/5 + 1;
        int totalItems = dsBook.size();

        int curPage = (page == null) ? 1 : Integer.parseInt(page);

        //set dsBook là hiển thị phân trang với curPage truyền vào
        dsBook = bookImpl.getAllBooksPagination(curPage, 5);

        //set curPage và totalPage
        request.setAttribute("page", curPage);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalItems", totalItems);

        request.setAttribute("dsBook", dsBook);
        request.setAttribute("dsBookType", dsBookType);

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    private void showBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dsBook = bookImpl.getAllBooks();
        dsBookType = bookTypeImpl.getAllBookTypes();
        int totalItems = dsBook.size();

        request.setAttribute("dsBook", dsBook);
        request.setAttribute("dsBookType", dsBookType);
        request.setAttribute("totalItems", totalItems);

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

//    private void showPagination(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//        String page = request.getParameter("page");
//
//        int totalPage = dsBook.size()/5 + 1;
//        int totalItems = dsBook.size();
//
//        int curPage = (page == null) ? 1 : Integer.parseInt(page);
//
//        //set dsBook là hiển thị phân trang với curPage truyền vào
//        dsBook = bookImpl.getAllBooksPagination(curPage, 5);
//
//        //set curPage và totalPage
//        request.setAttribute("page", curPage);
//        request.setAttribute("totalPage", totalPage);
//        request.setAttribute("totalItems", totalItems);
//
//        request.setAttribute("dsBook", dsBook);
//        request.setAttribute("dsBookType", dsBookType);
//    }

    private void showSearchAndFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String key = request.getParameter("searchValue");
        String filterVal = request.getParameter("BookTypesFilter");

        // Select option(has value) => Always !=null
        if(key!=null || filterVal!=null) {
            request.setAttribute("filterValue", filterVal);

            if (!key.equals("")) {  //Don't select filterValue and type searchValue => filterAndSearchBook(key, "")
                request.setAttribute("value", key);
                dsBook = bookImpl.filterAndSearchBook(key, filterVal);
            }
            else if(!filterVal.equals("") && key.equals("")){   //Select filterValue and dont't type searchValue => filterBook(filterVal)
                dsBook = bookImpl.filterBook(filterVal);
            }
            else {  //Don't select filterValue and don't type searchValue
                dsBook = bookImpl.getAllBooks();
            }
        }
        int totalItems = dsBook.size();
//        showPagination(request, response, totalItems);

        request.setAttribute("dsBook", dsBook);
        request.setAttribute("dsBookType", dsBookType);
        request.setAttribute("totalItems", totalItems);

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

}
