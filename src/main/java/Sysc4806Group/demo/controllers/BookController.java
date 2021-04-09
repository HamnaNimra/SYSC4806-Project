package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import Sysc4806Group.demo.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.text.similarity.*;

import java.util.*;

@Controller
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/editBook/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Book book = bookRepository.getOne(id);
        model.addAttribute("template", book);
        return "edit-book";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(value = "title", required = false) String title, Model model) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        model.addAttribute("books", books);
        return "search-result";
    }

    @GetMapping("/viewBook/{id}")
    public String viewBook(@PathVariable String id, Model model) {
        Book book = bookRepository.getOne(id);
        model.addAttribute("template", book);
        return "view-book";
    }

    @PostMapping("/uploadBook")
    public String uploadBook(@ModelAttribute Book book, Model model) {
        if (!bookRepository.existsById(book.getIsbn())) {
            bookRepository.save(book);
            List<Book> books = bookRepository.findAll();
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
        List<Book> books = bookRepository.findAll();
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

        getRecommendations();

        model.addAttribute("books", books);
        return "bookstore";
    }

    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute Book book, Model model) {
        Book bookFromDB = bookRepository.getOne(book.getIsbn());
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setInventory(book.getInventory());
        bookFromDB.setPublisher(book.getPublisher());
        bookFromDB.setTitle(book.getTitle());
        bookFromDB.setPictureUrl(book.getPictureUrl());
        bookFromDB.setIsbn(book.getIsbn());
        bookRepository.save(bookFromDB);
        return "view-book";
    }

    private List<Book> getRecommendations() {
        List<Book> books = bookRepository.findAll();
        User user = userRepository.getOne(getCurrentUserUid());
        JaccardDistance obj = new JaccardDistance();

        Map<String, Book> scoredBooks = new HashMap<>();

        if (!user.getPurchasedBooks().isEmpty()) {
            user.getPurchasedBooks().forEach(book -> books.forEach(book1 -> {
                if (!user.getPurchasedBooks().contains(book1)) {
                    double titleScore = obj.apply(book.getTitle(), book1.getTitle());
                    double authorScore = obj.apply(book.getAuthor(), book1.getAuthor());
                    double publisherScore = obj.apply(book.getPublisher(), book1.getPublisher());
                    double finalScore = (titleScore * 0.7 + authorScore * 0.2 + publisherScore * 0.1);

                    if (book1.getScore() != 0.0) {
                        if (book1.getScore() > finalScore) {
                            book1.setScore(finalScore);
                        }
                    } else {
                        book1.setScore(finalScore);
                    }
                    scoredBooks.put(book1.getTitle(), book1);
                }
            }));

            List<Book> scoredBooksList = new ArrayList<>(scoredBooks.values());
            scoredBooksList.sort(Comparator.comparingDouble(Book::getScore));

            for (int i = 0; i < scoredBooksList.size(); i++) {
                System.out.println("Book" + i);
                System.out.println(scoredBooksList.get(i).getTitle());
                System.out.println(scoredBooksList.get(i).getAuthor());
                System.out.println(scoredBooksList.get(i).getPublisher());
                System.out.println(scoredBooksList.get(i).getScore());
            }
            if (scoredBooks.size() >= 5) {
                return scoredBooksList.subList(0, 4);
            } else {
                return scoredBooksList;
            }

        } else if (books.size() <= 5) {
            Collections.shuffle(books);
            return books;
        } else {
            Collections.shuffle(books);
            return books.subList(0, 4);
        }
    }

    private String getCurrentUserUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return user.getUid();
    }
}
