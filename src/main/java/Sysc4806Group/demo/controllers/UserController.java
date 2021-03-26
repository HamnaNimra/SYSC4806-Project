package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.entities.Cart;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import Sysc4806Group.demo.repositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class UserController {
    private final UserRepository repository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

    UserController(UserRepository repository, BookRepository bookRepository, CartRepository cartRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("template", new User());
        return "sign-up";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user, Model model) {
        user.setUid(UUID.randomUUID().toString());
        Cart temp = user.getCart();
        temp.setID(user.getUid());
        user.setUserCart(temp);
        repository.save(user);
        model.addAttribute("user", user);
        System.out.println(user.getUid());
        System.out.println("hi"+ temp.getID());
        return "profile";
    }
    @PostMapping("/addCart/")
    public String addCart(@ModelAttribute User user, @ModelAttribute Book book, Model model){
        Cart userCart = user.getCart();
        userCart.addBook(book);
        book.sold();
        System.out.println("cart size is" + userCart.getLength());
        user.setUserCart(userCart);
        repository.save(user);
        bookRepository.save(book);
        return "profile";
    }

    /*
    //I want to set the cart controller up, such that when I add an item to the cart
    //It's added and then we go to the user's cart to show that the item has been added
    @GetMapping("/addItem/{id}/{uid}")
    public String addItemToCart(@PathVariable String id, @PathVariable String uid, Model model){
        Book book = bookRepository.getOne(id);
        User currentUser = repository.getOne(uid);
        Cart userCart = currentUser.getCart();
        userCart.addBook(book);
        currentUser.setUserCart(userCart);
        book.sold();
        System.out.println(book.getAuthor());
        System.out.println("cart id is" + userCart.getID());
        System.out.println("cart length is now" + userCart.getLength());
        return "cart-home";
    }
    */


    @GetMapping("/cart-home/{uid}")
    public String testUserCart(@PathVariable String uid, Model model){
        User user = repository.getOne(uid);
        model.addAttribute("template", user);
        return "cart-home";
    }
}
