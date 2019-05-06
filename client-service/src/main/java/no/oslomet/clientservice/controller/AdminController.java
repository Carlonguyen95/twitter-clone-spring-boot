package no.oslomet.clientservice.controller;

import no.oslomet.clientservice.model.User;
import no.oslomet.clientservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public String admin(Model model){
        List<User> userList;
        userList = userService.getAllUsers();

        model.addAttribute("userList", userList);
        return "/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id){
        userService.deleteUserById(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/update/{id}")
    public String updateUserPassword(@PathVariable("id") long id, User user){
        User newUser = user;
        newUser.setId(id);
        userService.updateUserPasswordById(user);
        return "redirect:/admin/user";
    }
}
