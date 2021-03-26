package Sysc4806Group.demo.entities;

import javax.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id
    private String uid;
    private String firstName;
    private String lastName;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart userCart;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> purchasedBooks;

    public User(String uid, String firstName, String lastName, String email) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userCart = new Cart(uid);
    }

    public User(String uid) {
        this.uid = uid;
        this.userCart = new Cart(uid);
    }

    public User() {
        this.userCart = new Cart();
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

    public Cart getCart(){return userCart;}
    public void setUserCart(Cart aCart){
        this.userCart = aCart;
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
