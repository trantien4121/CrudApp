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
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SearchServlet() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        ArrayList<BookBean> dsBook = new ArrayList<BookBean>();
        BookBo bBo = new BookBo();
        String value = request.getParameter("keyword");
        if(value!=null){
            dsBook = bBo.searchBook(value);
            if(dsBook.size()!=0){
                for(BookBean b : dsBook){
                    out.println("<p>" + b.getBookName() + "----" + b.getAuthor() + "</p>");
                }

            }else{
                out.println("<p>Không tìm thấy kết quả nào!</p>");
            }

        }
//
//
//        RequestDispatcher rd = request.getRequestDispatcher("testAjax.jsp");
//        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
