package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private BookRepository books;
    @Autowired
    UserRepository repository;

    @RequestMapping("/signup")
    public String index(Model model) {
        return "signup";
    }

    @RequestMapping(value="/createUser", method=RequestMethod.GET)
    public String createUser(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("role") User.Role role, Model model){

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        repository.save(user);

        model.addAttribute("role",user.getRole().toString());
        model.addAttribute("userID", user.getUid());
        model.addAttribute("books", books.findAll());

        return "profile";
    }
}
