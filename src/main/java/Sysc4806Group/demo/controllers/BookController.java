package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    BookRepository repository;

    @GetMapping("/editBook/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Book book = repository.getOne(id);
        model.addAttribute("template", book);
        return "edit-book";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(value = "title", required = false) String title, Model model) {
        List<Book> books = repository.findByTitleContainingIgnoreCase(title);
        model.addAttribute("books", books);
        return "search-result";
    }

    @GetMapping("/viewBook/{id}")
    public String viewBook(@PathVariable String id, Model model) {
        Book book = repository.getOne(id);
        model.addAttribute("template", book);
        return "view-book";
    }

    @PostMapping("/uploadBook")
    public String uploadBook(@ModelAttribute Book book, Model model) {
        if (!repository.existsById(book.getIsbn())) {
            repository.save(book);
            List<Book> books = repository.findAll();
            model.addAttribute("books", books);
            return "bookstore";
        } else {
            // Prompt user that book already exists
            return "upload-book";
        }
    }

    @GetMapping("/uploadBook")
    public String uploadBookForm(Model model) {
        model.addAttribute("template", new Book());
        return "upload-book";
    }

    @GetMapping("/bookstore")
    public String bookstore(@RequestParam(value = "sort", required = false) String sortBy, Model model) {
        List<Book> books = repository.findAll();
        // Sort through books based on given parameter
        if (sortBy != null) {
            switch (sortBy) {
                case "title":
                    books.sort(Comparator.comparing(Book::getTitle));
                    break;
                case "author":
                    books.sort(Comparator.comparing(Book::getAuthor));
                    break;
                case "publisher":
                    books.sort(Comparator.comparing(Book::getPublisher));
                    break;
            }
        } else {
            // If no parameter -> sort by title (when you first load up bookstore for example))
            books.sort(Comparator.comparing(Book::getTitle));
        }

        model.addAttribute("books", books);
        return "bookstore";
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

    @GetMapping("/addItem/{id}")
    public String addItemForm(@PathVariable String id, Model model) throws Exception {
        Book book = repository.getOne(id);
        model.addAttribute("template", book);
        return "add-item";
    }
}
