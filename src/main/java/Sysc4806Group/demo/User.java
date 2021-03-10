package Sysc4806Group.demo;

import javax.persistence.*;

import java.util.List;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String uid;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> purchasedBooks;

    public User(String uid, String firstName, String lastName, String email) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean purchaseBook(Book book) {
        if(book.getInventory() > 0) {
            purchasedBooks.add(book);
            book.sold();
            return true;
        } else {
            return false;
        }
    }
}
