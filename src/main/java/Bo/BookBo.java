package Bo;

import Bean.BookBean;
import Dao.BookDao;

import java.util.ArrayList;

public class BookBo {
    BookDao bDao = new BookDao();
    ArrayList<BookBean> dsBook = new ArrayList<BookBean>();

    public ArrayList<BookBean> getDsBook(){
        dsBook = bDao.getBook();
        return dsBook;
    }

    public BookBean getBook(String bookId){
        BookBean b = new BookBean();
        dsBook = bDao.getBook();
        for(BookBean i : dsBook){
            if (i.getBookId().equals(bookId))
                b = i;
        }
        return b;
    }

//    public static void main(String[] args) {
//        BookBo b = new BookBo();
//        BookBean bGet = b.getBook("bkt03");
//        System.out.println(bGet.getBookName());
//    }
}
