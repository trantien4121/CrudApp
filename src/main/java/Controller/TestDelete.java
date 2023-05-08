package Controller;

import Bean.BookBean;
import Bean.BookTypeBean;
import Bo.BookBo;
import Bo.BookTypeBo;
import Dao.BookDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/Test")
public class TestDelete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TestDelete() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        //set UTF-8
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        BookBo bBo = new BookBo();
        BookTypeBo bTypeBo = new BookTypeBo();

        ArrayList<BookTypeBean> dsBookType = bTypeBo.getDsBookType();
        ArrayList<BookBean> dsBook = bBo.getDsBook();

        request.setAttribute("dsBookType", dsBookType);
        request.setAttribute("dsBook", dsBook);

//        if(request.getParameter("btnDeleteCheck")!=null){
//            if(request.getParameterValues("habits")!=null){
//                String[] values = request.getParameterValues("habits");
//                pw.println("Selected Values...");
//                for(int i=0;i<values.length;i++)
//                {
//                    pw.println("<li>"+values[i]+"</li>");
//                }
//                pw.close();
//            }
//        }

//        //Delete List book checked
//        if(request.getParameter("btnDeleteListBook") != null){
//            if(request.getParameterValues("bookItems") != null){
//                String[] values = request.getParameterValues("bookItems");
//                pw.println("Selected Values...");
//                for(int i=0;i<values.length;i++)
//                {
//                    pw.println("<li>"+values[i]+"</li>");
//                }
//                pw.close();
//            }
//        }

        if(request.getParameter("btnDeleteCheck")!=null){
            if(request.getParameterValues("bookItems")!=null){
                String[] values = request.getParameterValues("bookItems");
                pw.println("Selected Values...");
                for(int i=0;i<values.length;i++)
                {
                    pw.println("<li>"+values[i]+"</li>");
                }
                pw.close();
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("testDelete.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
