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
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class UserController {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    BookRepository bookRepository;

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
    public String signin(@ModelAttribute User user, Model model) {
        //AUTH FLOW
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        //GET CURRENT USER OF SESSION
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Cart cart;

        if(user.getCart() == null) {
            cart = new Cart(counter.incrementAndGet());
            user.setCart(cart);
        } else {
            cart = user.getCart();
        }

        model.addAttribute("user", userDetails);
        model.addAttribute("cart", cart);

        return "profile";
    }

    @PostMapping("/signup")
    public RedirectView createUser(@ModelAttribute User user, @RequestParam(required = false) boolean hasAdminRole, Model model) {

        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email is already in use");
//            return "error";
        };

        user.setUid(UUID.randomUUID().toString());
        user.setPassword(encoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(Role.Roles.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setUserRole("ROLE_USER");

        if(hasAdminRole) {
            Role adminRole = roleRepository.findByName(Role.Roles.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            user.setUserRole("ROLE_ADMIN");
        }

        user.setRoles(roles);


        model.addAttribute("success", true);
        Cart cart = new Cart();
        cart.setID(counter.incrementAndGet());

        user.setCart(cart);

        cartRepository.save(cart);
        userRepository.save(user);

        model.addAttribute("user", user);
        model.addAttribute("cart", cart);

        return new RedirectView("signin");
    }



    @GetMapping("/checkout")
    public String checkout(Model model){
        User user = userRepository.getOne(getCurrentUserUid()); //.get();//.findByEmail(getCurrentUsername());

        model.addAttribute("cart", user.getCart());
        return "checkout";
    }

    @GetMapping("/checkoutState")
    public String finishCheckout(Model model) {
        User user = userRepository.getOne(getCurrentUserUid());
        Cart cart = user.getCart();

        for(int i = 0; i < cart.getItems().size(); i++) {
            Book book = bookRepository.findById(cart.getItems().get(i).getIsbn()).get();
            if(book.getInventory() > 0) {
                book.sold();
                user.purchaseBook(book);
                bookRepository.save(book);
            }
        }
        userRepository.save(user);
        cart.clear();
        cartRepository.save(cart);

        model.addAttribute("user", user);

        return "profile";
    }


    private String getCurrentUserUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        return user.getUid();

    }

}
