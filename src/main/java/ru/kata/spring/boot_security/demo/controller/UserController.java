package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "homePage";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "welcome";
    }

    @GetMapping("/userInfo/{username}")
    public String userInfo(@PathVariable("username") String username, Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(username));
        if (principal.getName().equals(username)) {
            return "userInfo";
        } else {
            return "redirect:/welcome";
        }
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("users") User user) {
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("users") @Validated User user
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
        } else {
            userService.addUser(user);
            return "redirect:/";
        }
    }
}
