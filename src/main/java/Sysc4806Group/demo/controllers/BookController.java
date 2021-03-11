package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/editBook/{id}")
    public String editForm(@PathVariable String id, Model model) throws Exception {
        Book book = repository.getOne(id);
        model.addAttribute("template", book);
        return "edit-book";
    }

    @PostMapping("/uploadBook")
    public String uploadBook(@ModelAttribute Book book, Model model) {
        if (!repository.existsById(book.getIsbn())) {
            repository.save(book);
            model.addAttribute("book", book);
            return "book";
        } else {
            // Prompt user that book already exists
            return "upload-book";
        }
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
}
