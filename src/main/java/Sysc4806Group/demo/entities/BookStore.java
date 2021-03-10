package Sysc4806Group.demo.entities;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class BookStore {

    @Id
    @GeneratedValue
    private Long id = null;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> inventory;

    public BookStore(){
        this.inventory = new ArrayList<Book>();
    }
}
