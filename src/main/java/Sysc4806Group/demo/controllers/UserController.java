package Sysc4806Group.demo.controllers;

import Sysc4806Group.demo.entities.Role;
import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.RoleRepository;
import Sysc4806Group.demo.repositories.UserRepository;
import Sysc4806Group.demo.services.UserDetailsImpl;
import org.apache.commons.text.similarity.JaccardDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.entities.Cart;
import Sysc4806Group.demo.repositories.BookRepository;
import Sysc4806Group.demo.repositories.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

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

        if (user.getCart() == null) {
            cart = new Cart(counter.incrementAndGet());
            user.setCart(cart);
        } else {
            cart = user.getCart();
        }

        model.addAttribute("user", userDetails);
        model.addAttribute("cart", cart);
        List<Book> recBooks = getRecommendations();
        System.out.println(recBooks.size() + recBooks.toString());
        model.addAttribute("recBooks", recBooks);

        return "profile";
    }

    @PostMapping("/signup")
    public RedirectView createUser(@ModelAttribute User user, @RequestParam(required = false) boolean hasAdminRole, Model model) {

        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email is already in use");
//            return "error";
        }

        user.setUid(UUID.randomUUID().toString());
        user.setPassword(encoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(Role.Roles.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setUserRole("ROLE_USER");

        if (hasAdminRole) {
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
    public String checkout(Model model) {
        User user = userRepository.getOne(getCurrentUserUid());

        model.addAttribute("cart", user.getCart());
        return "checkout";
    }

    @GetMapping("/checkoutState")
    public String finishCheckout(Model model) {
        User user = userRepository.getOne(getCurrentUserUid());
        Cart cart = user.getCart();

        for (int i = 0; i < cart.getItems().size(); i++) {
            Book book = bookRepository.findById(cart.getItems().get(i).getIsbn()).get();
            if (book.getInventory() > 0) {
                book.sold();
                user.purchaseBook(book);
                bookRepository.save(book);
            }
        }
        userRepository.save(user);
        cart.clear();
        cartRepository.save(cart);

        model.addAttribute("user", user);

        List<Book> recBooks = getRecommendations();
        model.addAttribute("recBooks", recBooks);
        System.out.println(recBooks.size() + recBooks.toString());
        return "profile";
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

            System.out.println("User history exists.");
            if (scoredBooks.size() >= 5) {
                return scoredBooksList.subList(0, 9);
            } else {
                return scoredBooksList;
            }

        } else {
            return new ArrayList<>(scoredBooks.values());
        }
    }

    private String getCurrentUserUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return user.getUid();
    }
}
