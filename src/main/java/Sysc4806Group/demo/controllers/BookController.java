package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.repositories.BookRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/uploadBook")
    public String uploadBook(@ModelAttribute Book book, Model model) {
        if (!repository.exists(book.getIsbn())) {
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
        return "";
    }
}
