package Sysc4806Group.demo;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private long id;
    private String book_name;
    private int ISBN;
    List<Integer> books = new ArrayList<>();

    public Book(long id, String book_name, int ISBN){
        this.id = id;
        this.book_name = book_name;
        this.ISBN = ISBN;
    }

/*    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }*/

    public void removeBook(String book_name, List<Integer> books) {
        if (!this.books.isEmpty()) {
            this.books.remove(book_name);

        }
    }

    public boolean isEqual(Object obj) {
        if (this == obj) return true;
        if (this == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Book book = (Book)obj;
        return this.book_name.equals(book.book_name);
    }
}
