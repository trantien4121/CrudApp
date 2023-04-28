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

    public HomePageServlet(){
        super();
    }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            BookBo bBo = new BookBo();
            BookTypeBo bTypeBo = new BookTypeBo();

            ArrayList<BookTypeBean> dsBookType = bTypeBo.getDsBookType();
            ArrayList<BookBean> dsBook = bBo.getDsBook();

            request.setAttribute("dsBook", dsBook);
            request.setAttribute("dsBookType", dsBookType);

            RequestDispatcher rd = request.getRequestDispatcher("homePage.jsp");
            rd.forward(request, response);
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
        }
}
