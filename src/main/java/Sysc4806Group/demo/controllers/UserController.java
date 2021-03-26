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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

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
        model.addAttribute("user", user);
        return "profile";
    }
}
