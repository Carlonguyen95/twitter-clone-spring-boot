package no.oslomet.clientservice.controller;


import no.oslomet.clientservice.model.User;
import no.oslomet.clientservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        System.out.println("user to save: " + user);
        userService.registerUser(user);

        return "redirect:/";
    }
}
