package Controller;

import Bean.BookBean;
import Bean.BookTypeBean;
import Bo.BookBo;
import Bo.BookTypeBo;
import Dao.BookDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.jws.WebService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CrudBookActionServlet")
public class CrudBookActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CrudBookActionServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String bookId = "";
        String bookName = "";
        String author = "";
        int quantity = 0;
        int price = 0;
        String imagePath = "";
        String bookType = "";

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        try {
            // Lấy về các đối tượng gửi lên
            List<FileItem> fileItems = upload.parseRequest(request);

            // duyệt qua các đối tượng gửi lên từ client gồm file và các control
            for (FileItem fileItem : fileItems) {

                // Nếu ko phải các control=>upfile lên
                if (!fileItem.isFormField()) {

                    // xử lý file
                    String imageName = fileItem.getName();
                    if (!imageName.equals("")) {

                        // Lấy đường dẫn hiện tại
//					String dirUrl = request.getServletContext().getRealPath("") +  File.separator + "files";

                        String dirUrl = "C:\\TienTran\\demo (1)\\Crud\\web\\BookImages";
                        imagePath = "BookImages/" + imageName;
                        File dir = new File(dirUrl);

                        if (!dir.exists()) {    // nếu ko có thư mục thì tạo ra
                            dir.mkdir();
                        }

                        String fileImg = dirUrl + File.separator + imageName;
                        File file = new File(fileImg);// tạo file

                        try {
                            fileItem.write(file);// lưu file
                            System.out.println("Upload image success!");
                            System.out.println("Images path is: " + dirUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {    // Neu la control

                    String field = fileItem.getFieldName();
                    if (field.equals("txtBookId")) {
                        bookId = fileItem.getString("UTF-8");
                    }
                    if (field.equals("txtBookName")) {
                        bookName = fileItem.getString("UTF-8");
                    }
                    if (field.equals("txtAuthor")) {
                        author = fileItem.getString("UTF-8");
                    }
                    if (field.equals("txtQuantity")) {
                        String quantityStr = fileItem.getString("UTF-8");
                        quantity = Integer.parseInt(quantityStr);
                    }
                    if (field.equals("txtPrice")) {
                        String priceStr = fileItem.getString("UTF-8");
                        price = Integer.parseInt(priceStr);
                    }
                    if (field.equals("txtBookType")) {
                        bookType = fileItem.getString("UTF-8");
                    }
                }

            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }


        //Add Book
        BookDao.addBook(bookId, bookName, author, quantity, price, imagePath, bookType);

        //Delete Book
        if (request.getParameter("btnDeleteBook") != null) {
            String bookIdDelete = request.getParameter("btnDeleteBook");
            BookDao.deleteBook(bookIdDelete);
        }

        //Delete List book checked
        if(request.getParameter("btnDeleteListBook") != null){
            System.out.println("Begin delete list book...");
            if(request.getParameterValues("bookItems") != null){
                String[] dsDeletedBookId = request.getParameterValues("bookItems");

                for(String dB : dsDeletedBookId){
                    BookDao.deleteBook(dB);
                }
                System.out.println("Delete list book success!");
                for(String b: dsDeletedBookId){
                    System.out.println(b);
                }
            }else{
                System.out.println("Delete list book failed!");
            }

        }

        response.sendRedirect("Home");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
