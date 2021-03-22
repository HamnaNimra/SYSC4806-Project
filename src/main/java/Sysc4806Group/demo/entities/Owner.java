package Sysc4806Group.demo.entities;
import Sysc4806Group.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Owner {

    @Autowired
    private BookRepository repository;
    private Book book;

    public Owner(BookRepository repository){
        this.repository = repository;
    }
    public void uploadBook(Book book){
        repository.save(book);
    }
    public void editBook(Book editBook, String id) {
        book = repository.getOne(id);
        // edit book attributes
        repository.save(book);
    }

}
