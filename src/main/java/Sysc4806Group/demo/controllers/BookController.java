package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(value = "title", required = false) String title,Model model) throws Exception{
        List<Book> books = repository.findByTitleContainingIgnoreCase(title);
        model.addAttribute("books", books);
        return "search-result";
    }

    @GetMapping("/viewBook/{id}")
    public String viewBook(@PathVariable String id,Model model) throws Exception{
        Book book = repository.findById(id).get();
        model.addAttribute("books", book);
        return "view-book";
    }

    @GetMapping("/uploadBook")
    public String uploadBookForm(Model model){
        model.addAttribute("template", new Book());
        return "uploadBook";
    }

    @PostMapping("/uploadBook")
    public String uploadBook(@ModelAttribute("createBook") Book book, Model model) {
        if (!repository.existsById(book.getIsbn())) {
            repository.save(book);
        }
        // else Prompt user that book already exists
        return bookstore(model);
    }

    @GetMapping("/updateBook")
    public String updateBookForm(@ModelAttribute("updateBook") Book book, Model model){
        model.addAttribute("template", book);
        return "updateBook";
    }

    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute Book book, Model model) {
        Book bookFromDB = repository.getOne(book.getIsbn());
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setInventory(book.getInventory());
        bookFromDB.setPublisher(book.getPublisher());
        bookFromDB.setTitle(book.getTitle());
        bookFromDB.setPictureUrl(book.getPictureUrl());
        bookFromDB.setIsbn(book.getIsbn());
        repository.save(bookFromDB);
        return bookstore(model);
    }

    @GetMapping("/bookstore")
    public String bookstore(Model model){
        List<Book> books = repository.findAll();
        model.addAttribute("books", books);
        return "bookstore";
    }
}