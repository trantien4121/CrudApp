package Controller;

import model.Book;
import model.BookType;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.book.IBook;
import service.book.IBookImpl;
import service.bookType.IBookType;
import service.bookType.IBookTypeImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookController", urlPatterns = "/CrudBook")
public class BookController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IBook bookImpl = new IBookImpl();

    private IBookType bookTypeImpl = new IBookTypeImpl();

    private ArrayList<Book> dsBook = new ArrayList<Book>();

    private ArrayList<BookType> dsBookType = new ArrayList<BookType>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        // Add, update use form with enctype="multipart/form-data" -> Can't use request.getParameter (-> return null)
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            FileItem btnItem = fileItems.get(fileItems.size()-1);   //lastItem
            String action = btnItem.getString("UTF-8");    //Get value of lastItem (button)

            switch (action){
                case "Add":
                    addNewBook(request, response, fileItems);
                    break;
                case "Update":
                    updateBook(request, response, fileItems);
                    break;
                default:
                    break;
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        // Delete, deleteList use normal form => Can use request.getParameter()
        String actionDelete = (request.getParameter("btnActionDelete")==null ? "" : request.getParameter("btnActionDelete"));

        switch(actionDelete){
            case "deleteListBook":
                deleteListBook(request, response);
                break;
            default:
                deleteBookById(request, response);
                break;
        }

        response.sendRedirect(" home");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doPost(request, response);
    }

    private void addNewBook(HttpServletRequest request, HttpServletResponse response, List<FileItem> fileItems) throws IOException{
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

        // duyệt qua các đối tượng gửi lên từ client gồm file và các control
        for (FileItem fileItem : fileItems) {

            // Nếu ko phải các control=>upfile lên
            if (!fileItem.isFormField()) {

                // xử lý file
                String imageName = fileItem.getName();
                if (!imageName.equals("")) {

                    // Lấy đường dẫn
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {    // Neu la control

                String field = fileItem.getFieldName();

                switch(field){
                    case "txtBookId":
                        bookId = fileItem.getString("UTF-8");
                        break;
                    case "txtBookName":
                        bookName = fileItem.getString("UTF-8");
                        break;
                    case "txtAuthor":
                        author = fileItem.getString("UTF-8");
                        break;
                    case "txtQuantity":
                        String quantityStr = fileItem.getString("UTF-8");
                        quantity = Integer.parseInt(quantityStr);
                        break;
                    case "txtPrice":
                        String priceStr = fileItem.getString("UTF-8");
                        price = Integer.parseInt(priceStr);
                        break;
                    case "txtBookType":
                        bookType = fileItem.getString("UTF-8");
                        break;
                    default:
                        break;
                }

            }

        }

        //Add Book
        bookImpl.addBook(bookId, bookName, author, quantity, price, imagePath, bookType);
    }
    private void updateBook(HttpServletRequest request, HttpServletResponse response, List<FileItem> fileItems) throws IOException{
        String bookId = "";
        String bookName = "";
        String author = "";
        int quantity = 0;
        int price = 0;
        String imagePath = "";
        String imageName = "";
        String bookType = "";

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        // duyệt qua các đối tượng gửi lên từ client gồm file và các control
        for (FileItem fileItem : fileItems) {

            // Nếu ko phải các control=>upfile lên
            if (!fileItem.isFormField()) {

                // xử lý file
                imageName = fileItem.getName();
                if (!imageName.equals("")) {

                    // Lấy đường dẫn

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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {    // Neu la control

                String field = fileItem.getFieldName();

                switch(field){
                    case "txtBookId":
                        bookId = fileItem.getString("UTF-8");
                        break;
                    case "txtBookName":
                        bookName = fileItem.getString("UTF-8");
                        break;
                    case "txtAuthor":
                        author = fileItem.getString("UTF-8");
                        break;
                    case "txtQuantity":
                        String quantityStr = fileItem.getString("UTF-8");
                        quantity = Integer.parseInt(quantityStr);
                        break;
                    case "txtPrice":
                        String priceStr = fileItem.getString("UTF-8");
                        price = Integer.parseInt(priceStr);
                        break;
                    case "txtBookType":
                        bookType = fileItem.getString("UTF-8");
                        break;
                    default:
                        break;
                }

            }

        }

        //Nếu có update ảnh
        if(!imageName.equals("")){
            Book updatedBook = bookImpl.updateBook(bookId, bookName, author, quantity, price, imagePath, bookType);
        }
        //Nếu không update ảnh
        else{
            Book bGet = bookImpl.getByBookId(bookId);
            String curImagePath = bGet.getImage();
            Book updatedBook = bookImpl.updateBook(bookId, bookName, author, quantity, price, curImagePath, bookType);
        }
    }
    private void deleteListBook(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameterValues("bookItems") != null){
            String[] dsDeletedBookId = request.getParameterValues("bookItems");
            for(String dB : dsDeletedBookId){
                bookImpl.deleteBook(dB);
            }
        }
    }
    public void deleteBookById(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameter("btnActionDelete")!=null){
            String value = request.getParameter("btnActionDelete");
            if(!value.equals("deleteListBook")){
                bookImpl.deleteBook(value);
            }
        }
    }
}
