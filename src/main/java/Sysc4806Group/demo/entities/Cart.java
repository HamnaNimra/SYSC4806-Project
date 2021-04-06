package Sysc4806Group.demo.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="carts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable( name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> items; //Needs to have a collection of items in the cart

    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
    private User user;

    public Cart(Long id){
        this.id = id;
        this.items = new ArrayList<Book>();
    }

    public Cart() {
        this.items = new ArrayList<Book>();
    }

    //Add a book to the cart feature
    public boolean addBook(Book book){
        if(book != null && book.getInventory() > 0) {
            items.add(book);
            return true;
        } else {
            return false;
        }
    }

    public List<Book> getItems() {
        return items;
    }

    public Book findBook(int index){
        return items.get(index);
    }
    public long getID(){
        return id;
    }
    public void setID(Long id){
        this.id = id;
    }

    public boolean empty() {
        return (getLength() == 0);
    }

    public int getLength(){
        return items.size();
    }

}
