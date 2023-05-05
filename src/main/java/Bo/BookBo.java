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

    public ArrayList<BookBean> getBookPagination(int pageNo, int PageSize){
        dsBook = bDao.getPagination(pageNo, PageSize);
        return dsBook;
    }

    public ArrayList<BookBean> searchBook(String val){
        ArrayList<BookBean> dsSearch = new ArrayList<BookBean>();
        dsBook = bDao.getBook();
        for(BookBean b: dsBook){
            if(b.getBookName().toLowerCase().contains(val.toLowerCase()) ||
            b.getAuthor().toLowerCase().contains(val.toLowerCase())){
                dsSearch.add(b);
            }
        }
        return dsSearch;
    }

    public ArrayList<BookBean> filterBook(String bookType){
        ArrayList<BookBean> dsFilter = new ArrayList<BookBean>();
        dsBook = bDao.getBook();
        for(BookBean b : dsBook){
            if(b.getBookType().equals(bookType))
                dsFilter.add(b);
        }
        return dsFilter;
    }

    public ArrayList<BookBean> filterAndSearch(String bookType, String val){
        ArrayList<BookBean> dsFilterSearch = new ArrayList<BookBean>();
        dsBook = bDao.getBook();

        for(BookBean b: dsBook){
            if(bookType.equals("")){
                dsFilterSearch = searchBook(val);
            }else{
                if(b.getBookType().equals(bookType) && (b.getBookName().toLowerCase().contains(val.toLowerCase()) ||
                        b.getAuthor().toLowerCase().contains(val.toLowerCase()))){
                    dsFilterSearch.add(b);
                }
            }
        }
        return dsFilterSearch;
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
