package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private BookRepository books;
    @Autowired
    UserRepository repository;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        return "sign-up";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user, Model model) {
        repository.save(user);
        model.addAttribute("user", user);

        repository.save(user);

        model.addAttribute("userID", user.getUid());
        model.addAttribute("books", books.findAll());

        return "bookstore";
    }

    @GetMapping("/sign-in")
    public String signInForm(Model model) {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute User user, Model model) {
    /*public String verifyUser(@RequestParam("email") String email,
                @RequestParam("password") String password, HttpServletResponse response,
                Model model)
        List<User> users = repository.findByEmail(email);
        //if email not in db or db empty
        //prompt user to sign up or enter correct email
        //take user to signIn-error page

        // else if compare associated password for email correct verify password
        // (user.existsById(user.getPassword()))
        //if password correct
        return "bookstore";

        //else take user to signIn-error page
        */
        return "bookstore";
    }

    @GetMapping("/manageBooks")
    public String manageBooks(@ModelAttribute User user, Model model){
        if (user.getRole().equals(User.Role.OWNER)) {
            return "bookstoreOwner";
        } else {
            return "bookstore";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model){
        //log user out and take user to index page
        //set session id to zero for response using cookie
        return "index";
    }

    @GetMapping("/profile")
    public String profile(@ModelAttribute User user, Model model){
        if (user.getRole().equals(User.Role.OWNER)) {
            return "ownerProfile";
        } else {
            return "customerProfile";
        }
    }

}
