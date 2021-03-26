package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Role;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.RoleRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import Sysc4806Group.demo.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.entities.Cart;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import Sysc4806Group.demo.repositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("template", new User());
        model.addAttribute("hasAdminRole", false);
        return "signup";
    }

    @GetMapping("/signin")
    public String signinForm(Model model) {
        model.addAttribute("template", new User());
        return "signin";
    }

    @PostMapping("/signin")
    public Object signin(@ModelAttribute User user, Model model) {
        //AUTH FLOW
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        //GET CURRENT USER OF SESSION
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        model.addAttribute("user", userDetails);

        return "profile";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user, @RequestParam(required = false) boolean hasAdminRole, Model model) {

        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email is already in use");
            return "error";
        };

        user.setUid(UUID.randomUUID().toString());
        user.setPassword(encoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(Role.Roles.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        if(hasAdminRole) {
            Role adminRole = roleRepository.findByName(Role.Roles.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
        }

        user.setRoles(roles);

        userRepository.save(user);

        System.out.println("has admin role " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN")));

        model.addAttribute("success", true);
        Cart temp = user.getCart();
        temp.setID(user.getUid());
        user.setUserCart(temp);
        repository.save(user);
        model.addAttribute("user", user);

        return "profile";
    }
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

    @GetMapping("/cart-home/{uid}")
    public String testUserCart(@PathVariable String uid, Model model){
        User user = repository.getOne(uid);
        model.addAttribute("template", user);
        return "cart-home";
    }
}
