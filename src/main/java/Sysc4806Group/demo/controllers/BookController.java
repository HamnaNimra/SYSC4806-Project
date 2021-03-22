package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookRepository repository;
    @Autowired
    private UserRepository users;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(value = "title", required = false) String title, @ModelAttribute("userID") String uid,
                             Model model) throws Exception{
        List<Book> books = repository.findByTitleContainingIgnoreCase(title);
        User user =users.findById(uid).get();
        model.addAttribute("userID", uid);
        model.addAttribute("role",user.getRole());
        model.addAttribute("books", books);
        return "search-result";
    }

    @GetMapping("/viewBook/{id}")
    public String viewBook(@PathVariable String id,@ModelAttribute("userID") String uid ,Model model) throws Exception{
        Book book = repository.getOne(id);
        User user =users.findById(uid).get();
        model.addAttribute("template", book);
        model.addAttribute("userID", uid);
        model.addAttribute("role",user.getRole());
        return "view-book";
    }

    @GetMapping("/uploadBook")
    public String uploadBookForm(@ModelAttribute("userID") String uid,Model model){
        model.addAttribute("template", new Book());
        model.addAttribute("userID",uid);
        return "upload-book";
    }

    @PostMapping("/uploadBook")
    public String uploadBook(@ModelAttribute Book book, @ModelAttribute("userID") String uid, Model model) {
        if (!repository.existsById(book.getIsbn())) {
            repository.save(book);
            model.addAttribute("book", book);
            model.addAttribute("userID",uid);
            return "book";
        } else {
            // Prompt user that book already exists
            repository.save(book);
            return bookstore(uid, model);
        }
    }

    @GetMapping("/editBook/{id}")
    public String editForm(@PathVariable String id, @ModelAttribute("userID") String uid, Model model) throws Exception {
        Book book = repository.getOne(id);
        model.addAttribute("template", book);
        model.addAttribute("userID",uid);
        return "edit-book";
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
        return "view-book";
    }


    @GetMapping("/bookstore")
    public String bookstore(@ModelAttribute("userID") String uid,Model model){
        List<Book> books = repository.findAll();
        User user = users.findById(uid).get();
        model.addAttribute("books", books);
        model.addAttribute("userID",uid);
        model.addAttribute("role", user.getRole().toString());
        return "bookstore";
    }
}
