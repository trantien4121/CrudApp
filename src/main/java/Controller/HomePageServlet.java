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


        //request.setAttribute("dsBook", dsBook);
        request.setAttribute("dsBookType", dsBookType);

        //Pagination
        int curPage = 1;
        int totalPage = dsBook.size()/5 + 1;
        int totalItems = dsBook.size();

        request.setAttribute("totalItems", totalItems);

        //Lần hiển hị đầu tiên, chưa set page và searchValue (-> set curPage =1)
        if(request.getParameter("page")==null && request.getParameter("searchValue")==null){
            curPage = 1;
        }

        //Nếu tìm được page trên url, set curPage = page đó
        if(request.getParameter("page")!=null){
            curPage = Integer.parseInt(request.getParameter("page"));
        }

        //set dsBook là hiển thị phân trang với curPage truyền vào
        dsBook = bBo.getBookPagination(curPage, 5);

        //set curPage và totalPage
        request.setAttribute("page", curPage);
        request.setAttribute("totalPage", totalPage);


        String key = request.getParameter("searchValue");
        String filterVal = request.getParameter("BookTypesFilter");

        //Filter and search
        if(key!= null || filterVal!=null) {
            request.setAttribute("filterValue", filterVal);

            if (!key.equals("")) {
                request.setAttribute("value", key);
                dsBook = bBo.filterAndSearch(filterVal, key);
            }
            else if(!filterVal.equals("")){
                dsBook = bBo.filterBook(filterVal);
            }
            else {
                dsBook = bBo.getBookPagination(curPage, 5);
            }
        }

        request.setAttribute("dsBook", dsBook);

        RequestDispatcher rd = request.getRequestDispatcher("homePage2.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
