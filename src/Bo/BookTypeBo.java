package Bo;

import Bean.BookTypeBean;
import Dao.BookTypeDao;

import java.util.ArrayList;

public class BookTypeBo {
    BookTypeDao btDao = new BookTypeDao();
    ArrayList<BookTypeBean> dsBookType;

    public ArrayList<BookTypeBean> getDsBookType(){
        dsBookType = btDao.getBookType();
        return dsBookType;
    }
}
