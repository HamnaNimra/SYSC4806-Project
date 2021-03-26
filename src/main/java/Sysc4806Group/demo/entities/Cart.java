package Sysc4806Group.demo.entities;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Cart {
    @Id
    private String id; //Cart needs to have an id, so that it can be belong to a specific user

    private ArrayList<Book> cartItems; //Needs to have a collection of items in the cart
    private String testing;

    @OneToOne(cascade = CascadeType.ALL)
    private User user; //The cart is associated to a specific user


    public Cart(String id) {
        this.cartItems = new ArrayList<Book>();
        this.id = id;
    }
    public Cart(){
        this.cartItems = new ArrayList<>();
    }

    //Add a book to the cart feature
    public void addBook(Book book){
        if(book != null) {
            cartItems.add(book);
            System.out.println("Added book successfully");
        }
    }

    public Book findBook(int index){
        return cartItems.get(index);
    }
    public String getID(){
        return id;
    }
    public void setID(String id){
        this.id=id;
    }
    public void setTesting(String text){
        this.testing = text;
    }
    public String getTesting(){
        return testing;
    }
    public int getLength(){
        return cartItems.size();
    }

}
