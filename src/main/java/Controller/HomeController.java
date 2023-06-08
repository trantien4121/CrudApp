package Controller;

import common.RedisCacheManager;
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
import java.util.List;
import java.util.Set;

@WebServlet(name = "HomeController", urlPatterns = "/home")
public class HomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IBook iBook = new IBookImpl();

    private IBookType iBookType = new IBookTypeImpl();

    private ArrayList<Book> lstBook = new ArrayList<Book>();

    private ArrayList<BookType> lstBookType = new ArrayList<BookType>();


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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        // Chưa chuyển trang với action = SearchAndFilter => show tất cả
        if (request.getParameter("action") == null) {
            lstBook = iBook.checkCacheAndGetAllBooks();
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
    }

    private void showBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        lstBook = iBook.getAllBooks();
        lstBookType = iBookType.getAllBookTypes();

        int totalItems = lstBook.size();

        request.setAttribute("dsBook", lstBook);
        request.setAttribute("dsBookType", lstBookType);
        request.setAttribute("totalItems", totalItems);

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    private void showPagination(HttpServletRequest request, String searchValue, String filterValue) {
        String page = request.getParameter("page");

        //Lấy tổng số trang có được sau khi search/filter
        int totalPage = lstBook.size() / 5 + 1;

        //Lấy số item có được sau search/filter
        int totalItems = lstBook.size();

        //Nếu lần đầu => mặc định curPage = 1, ngược lại thì lấy curPage = trang hiện tại
        int curPage = (page == null) ? 1 : Integer.parseInt(page);

        //Hiển thị phân trang với tập dữ liệu lstBooks truyền vào.
        lstBook = iBook.getBooksWithPagination(curPage, 5, searchValue, filterValue);
        lstBookType = iBookType.checkCacheAndGetAllBookTypes();

        //set curPage, totalPage, totalItems
        request.setAttribute("page", curPage);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("totalItems", totalItems);

        //Set lại dsBook là danh sách trong từng trang sẽ hiển thị => Showing ${dsBook.size()} out of ${totalItems} entries
        request.setAttribute("dsBook", lstBook);
        request.setAttribute("dsBookType", lstBookType);
    }

    private void showSearchAndFilter(HttpServletRequest request, HttpServletResponse response,
                                     String keySession, String filterValSession) throws ServletException, IOException {

        String key = "";
        String filterVal = "";

        // Kiểm tra khi chuyển trang đã search/value chưa => check session keySession, filterVal session
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

            if (!key.equals("")) {  // filterAndSearchBook(key, "")

                request.setAttribute("value", key);
                lstBook = iBook.filterAndSearchBook(key, filterVal);

            } else if (!filterVal.equals("") && key.equals("")) {   // filterBook(filterVal)

                lstBook = iBook.filterBook(filterVal);

            } else {  //Don't select filterValue and don't type searchValue

                //check Book on cache and get Book data
                lstBook = iBook.checkCacheAndGetAllBooks();

            }
        }

        RedisCacheManager.updateCacheAsSortedSet("searchHistory", key);

        //Sau khi search và filter, hiển thị phân trang với key và filterVal đã set ở trên cùng với dsBook tương ứng
        showPagination(request, key, filterVal);

        List<String> searchHistory = RedisCacheManager.getPopularCacheAsSortedSet("searchHistory", 5);
        request.setAttribute("searchHistory", searchHistory);

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }


}
