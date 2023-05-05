package Controller;

import Bean.BookBean;
import Bean.BookTypeBean;
import Bo.BookBo;
import Bo.BookTypeBo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/Home")
public class HomePageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public HomePageServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //set UTF-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        BookBo bBo = new BookBo();
        BookTypeBo bTypeBo = new BookTypeBo();

        ArrayList<BookTypeBean> dsBookType = bTypeBo.getDsBookType();
        ArrayList<BookBean> dsBook = bBo.getDsBook();


        request.setAttribute("dsBook", dsBook);
        request.setAttribute("dsBookType", dsBookType);

        //Pagination
        int curPage = 1;
        int totalPage = dsBook.size()/5 + 1;
        int totalItems = dsBook.size();

        request.setAttribute("totalItems", totalItems);

        if(request.getParameter("searchValue")!=null){
            curPage = 1;
            dsBook = bBo.getDsBook();
        }
        if(request.getParameter("page")==null){
            curPage = 1;
        }else{
            curPage = Integer.parseInt(request.getParameter("page"));
        }
        dsBook = bBo.getBookPagination(curPage, 5);

        request.setAttribute("page", curPage);
        request.setAttribute("totalPage", totalPage);


        //Search by bookName of Author
        String key = request.getParameter("searchValue");
        if (key != null) {
            request.setAttribute("value", key);
            dsBook = bBo.searchBook(key);
        }
        request.setAttribute("dsBook", dsBook);

        RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
