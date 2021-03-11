package Sysc4806Group.demo.entities;

import javax.persistence.*;

import java.util.List;

@Entity
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

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getPurchasedBooks() {
        return purchasedBooks;
    }

    public void setPurchasedBooks(List<Book> purchasedBooks) {
        this.purchasedBooks = purchasedBooks;
    }

    public boolean purchaseBook(Book book) {
        if (book.getInventory() > 0) {
            purchasedBooks.add(book);
            book.sold();
            return true;
        } else {
            return false;
        }
    }
}
