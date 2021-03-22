package Sysc4806Group.demo.entities;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class User {
    public enum Role { OWNER, CUSTOMER,}
    @Id
    private String uid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> purchasedBooks;

    public User(String uid, String firstName, String lastName, String email, String password, Role role) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(uid, user.uid) &&
                getUid().equals(user.getUid()) &&
                getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword()) &&
                getRole().equals(user.getRole());
    }
    @Override
    public int hashCode() {
        return Objects.hash(uid, getFirstName(), getLastName(), getEmail(), getPassword(), getRole());
    }
}
