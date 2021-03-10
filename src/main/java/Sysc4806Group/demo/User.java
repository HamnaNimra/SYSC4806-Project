package Sysc4806Group.demo;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String firstName;
    private String lastName;
    private String email;

    private List<Book> purchasedBooks;

    public User(String uid, String firstName, String lastName, String email) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        purchasedBooks = new ArrayList<>();
    }

    public User(String uid) {
        this.uid = uid;
    }

    public String getUser_id() {
        return uid;
    }

    public void setUser_id(String user_id) {
        this.uid = user_id;
    }

    public String getFirst_name() {
        return firstName;
    }

    public void setFirst_name(String first_name) {
        this.firstName = first_name;
    }

    public String getLast_name() {
        return lastName;
    }

    public void setLast_name(String last_name) {
        this.lastName = last_name;
    }

    public String getFullName() {
        return this.getFirst_name() + " " + this.getLast_name();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEqual(Object obj) {
        if (this == obj) return true;
        if (this.getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return this.uid.equals(user.uid) && this.firstName.equals(user.firstName) && this.lastName.equals(user.lastName);
    }
}
