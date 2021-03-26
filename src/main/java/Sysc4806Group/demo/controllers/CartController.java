package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.Cart;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.CartRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {
    private final CartRepository repository;
    private final BookRepository bookRepo;
    private final UserRepository userRepository;

    CartController(CartRepository repository, BookRepository bookRepo, UserRepository userRepository) {
        this.repository = repository;
        this.bookRepo = bookRepo;
        this.userRepository = userRepository;
    }


    /*
    @PostMapping("/addItem")
    public String addItem(@ModelAttribute Book book, Model model){
        System.out.println(book.getAuthor());
        System.out.println(book.getIsbn());
        System.out.println(book.getInventory());
        return "view-book";
    }
     */

}
