package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static Sysc4806Group.demo.entities.User.Role.OWNER;

@Controller
public class UserController {
    @Autowired
    private BookRepository books;
    @Autowired
    UserRepository repository;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("template", new User());
        return "sign-up";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user, Model model,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("role") User.Role role){

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        repository.save(user);

        return "main-page";
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.GET)
    public String signInForm(@ModelAttribute User user, Model model) {
        model.addAttribute("template", user);
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
        return "main-page";
    }

    @GetMapping("/manageBooks")
    public String manageBooks(@ModelAttribute User user, Model model){
        return "bookstoreOwner";
    }

    @GetMapping("/logout")
    public String logout(Model model){
        //log user out and take user to index page
        //set session id to zero for response using cookie
        return "index";
    }

    @GetMapping("/profile")
    public String profile(@ModelAttribute User user,Model model){
        //user = repository.getOne(user.getUid());
        if(user.getRole() == null){
            user.setRole(User.Role.CUSTOMER); //testing
        }
        if (user.getRole().equals(OWNER)) {
            return "ownerProfile";
        } else {
            return "customerProfile";
        }
    }

}