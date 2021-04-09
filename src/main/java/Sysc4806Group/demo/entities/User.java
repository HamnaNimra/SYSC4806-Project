package Sysc4806Group.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    private String uid;

    private String firstName;

    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 50)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable( name = "user_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> purchasedBooks;

    public User(String uid, String firstName, String lastName, String email, String password) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
