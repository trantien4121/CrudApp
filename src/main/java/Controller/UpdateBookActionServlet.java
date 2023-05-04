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

@WebServlet("/UpdateBookActionServlet")
public class UpdateBookActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateBookActionServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String bookId = "";
        String bookName = "";
        String author = "";
        int quantity = 0;
        int price = 0;
        String imageName = new String();
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
                    imageName = fileItem.getName();
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


//        String bookIdUpdate = request.getParameter("btnUpdateBook");
//
//        String bookNameUpdate = request.getParameter("txtBookNameUpdate");
//        String authorUpdate = request.getParameter("txtAuthorUpdate");
//        int quantityUpdate = Integer.parseInt(request.getParameter("txtQuantityUpdate"));
//        int priceUpdate = Integer.parseInt(request.getParameter("txtPriceUpdate"));
//        String bookTypeUpdate = request.getParameter("txtBookTypeUpdate");
//
//        System.out.println(bookIdUpdate);

        System.out.println("Tên ảnh là: "+ imageName);

        //Nếu đã update ảnh
        if(!imageName.equals("")){
            BookBean updatedBook = BookDao.updateBook(bookId, bookName, author, quantity, price, imagePath, bookType);
            System.out.println("update book success!!");
        }
        //Nếu chưa update ảnh
        else{
            BookBo bBo = new BookBo();
            BookBean bGet = bBo.getBook(bookId);
            String curImagePath = bGet.getImage();

            BookBean updatedBook = BookDao.updateBook(bookId, bookName, author, quantity, price, curImagePath, bookType);
        }
        response.sendRedirect("Home");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

