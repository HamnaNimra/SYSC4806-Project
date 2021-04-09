package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.Cart;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.CartRepository;
import Sysc4806Group.demo.repositories.RoleRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import Sysc4806Group.demo.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CartController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/addBook/{isbn}")
    public Cart addBookToCart(@PathVariable("isbn") String isbn) {
        User user = userRepository.getOne(getCurrentUserUid());
        Cart cart = user.getCart();
        Book book = bookRepository.findById(isbn).get();

        if(!cart.has(book)) {
            if(cart.addBook(book)) {
                System.out.println("book added to cart successfully");
            } else {
                System.out.println("error adding book");
            }
            cartRepository.save(cart);
        }

        return cart;
    }

    @GetMapping("/removeBook/{isbn}")
    public Cart removeBookFromCart(@PathVariable("isbn") String isbn) {
        User user = userRepository.getOne(getCurrentUserUid());
        Cart cart = user.getCart();
        Book book = bookRepository.findById(isbn).get();

        if(cart.has(book)) {
            cart.remove(book);
            cartRepository.save(cart);
        }

        return cart;
    }

    private String getCurrentUserUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        return user.getUid();

    }
}
