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
}
