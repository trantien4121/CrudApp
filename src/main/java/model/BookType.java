package model;

public class BookType {
    private String bookType;
    private String bookTypeName;

    public BookType(){}

    public BookType(String bookType, String bookTypeName) {
        this.bookType = bookType;
        this.bookTypeName = bookTypeName;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }
}
