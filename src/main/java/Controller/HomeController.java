package Controller;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "HomeController", urlPatterns = "/home")
public class HomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IBook bookImpl = new IBookImpl();

    private IBookType bookTypeImpl = new IBookTypeImpl();

    private ArrayList<Book> dsBook = new ArrayList<Book>();

    private ArrayList<BookType> dsBookType = new ArrayList<BookType>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //set UTF-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        //Khi chưa search và filter với action = SearchAndFilter (search có submit => POST)
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        //Khi có Search hoặc filter
        switch (action) {
            case "SearchAndFilter":
                showSearchAndFilter(request, response, "", "");
                break;
            default:
                showBook(request, response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dsBook = bookImpl.getAllBooks();
        dsBookType = bookTypeImpl.getAllBookTypes();

        // Chưa chuyển trang với action = SearchAndFilter => show tất cả
        if (request.getParameter("action") == null) {
            showSearchAndFilter(request, response, "", "");

        }
        // Khi có chuyển trang bằng cách nhấn button chuyển trang (GET)
        // Với action=searchAndFilter&page={} => check session, rồi hiển thị phân trang dựa trên tập dữ liệu search
        else {
            HttpSession session = request.getSession();
            String searchValueSession = (String) session.getAttribute("searchValueSession");
            String filterValueSession = (String) session.getAttribute("filterValueSession");
            showSearchAndFilter(request, response, searchValueSession, filterValueSession);
        }

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

    private void showPagination(HttpServletRequest request, HttpServletResponse response, String searchValue, String filterValue) throws ServletException, IOException {
        String page = request.getParameter("page");

        int totalPage = dsBook.size() / 5 + 1;
        int totalItems = dsBook.size();

        //Nếu lần đầu => mặc định curPage = 1, ngược lại thì lấy curPage = trang hiện tại
        int curPage = (page == null) ? 1 : Integer.parseInt(page);

        //Hiển thị phân trang với tập dữ liệu dsBooks truyền vào.
        dsBook = bookImpl.getBooksWithPagination(curPage, 5, searchValue, filterValue);

        //set curPage, totalPage, totalItems
        request.setAttribute("page", curPage);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalItems", totalItems);

        request.setAttribute("dsBook", dsBook);
        request.setAttribute("dsBookType", dsBookType);
    }

    private void showSearchAndFilter(HttpServletRequest request, HttpServletResponse response,
                                     String keySession, String filterValSession) throws ServletException, IOException {

        String key = "";
        String filterVal = "";

        if (keySession == null) {
            keySession = "";
        }
        if (keySession.equals("") && filterValSession.equals("")) {
            key = request.getParameter("searchValue");
            filterVal = request.getParameter("BookTypesFilter");
        }
        if (keySession.equals("") && !filterValSession.equals("")) {
            key = request.getParameter("searchValue");
            filterVal = filterValSession;
        }
        if (!keySession.equals("") && !filterValSession.equals("")) {
            key = keySession;
            filterVal = filterValSession;
        }
        if (!keySession.equals("") && filterValSession.equals("")) {
            key = keySession;
            filterVal = request.getParameter("BookTypesFilter");
        }

        if (key == null) {
            key = "";
        }
        if (filterVal == null) {
            filterVal = "";
        }


        if (key != null) {
            request.setAttribute("filterValue", filterVal);

            if (!key.equals("")) {  //Don't select filterValue and type searchValue => filterAndSearchBook(key, "")
                request.setAttribute("value", key);
                dsBook = bookImpl.filterAndSearchBook(key, filterVal);
            } else if (!filterVal.equals("") && key.equals("")) {   //Select filterValue and dont't type searchValue => filterBook(filterVal)
                dsBook = bookImpl.filterBook(filterVal);
            } else {  //Don't select filterValue and don't type searchValue
                dsBook = bookImpl.getAllBooks();
            }
        }
        int totalItems = dsBook.size();

        //Sau khi search và filter, hiển thị phân trang với key và filterVal đã set ở trên
        showPagination(request, response, key, filterVal);

        request.setAttribute("dsBook", dsBook);
        request.setAttribute("dsBookType", dsBookType);
        request.setAttribute("totalItems", totalItems);

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

}
