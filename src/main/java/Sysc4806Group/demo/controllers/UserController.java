package Sysc4806Group.demo.controllers;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.UserRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;
import java.util.UUID;

@RestController
public class UserController {
    private final UserRepository repository;
    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("template", new User());
        return "sign-up";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user, Model model) {
        user.setUid(UUID.randomUUID().toString());
        repository.save(user);
        model.addAttribute("user", user);
        return "profile";
    }
}
