package Sysc4806Group.demo;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long user_id;
    private String first_name;
    private String last_name;

    private List<Book> purchased_books;

    public User(long user_id, String first_name, String last_name, String email) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        purchased_books = new ArrayList<Book>();

    }

    public User(){

    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public String getFirst_name(){
        return first_name;
    }
    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public void setLast_name(String last_name){
        this.last_name = last_name;
    }
    public String getFullName(){
        return this.getFirst_name() + " " + this.getLast_name();
    }
    public boolean isEqual(Object obj) {
        if (this == obj) return true;
        if (this.getClass() != obj.getClass()) return false;
        User user = (User)obj;
        return this.user_id == user.user_id && this.first_name.equals(user.first_name) && this.last_name.equals(user.last_name);
    }

}
