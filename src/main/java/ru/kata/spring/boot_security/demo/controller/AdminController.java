package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String allUser(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("users", userService.getUserId(id));
        model.addAttribute("roleList", userService.allRoles());
        return "edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("users")
                         @Validated User user, BindingResult bindingResult,
                         @RequestParam(value = "role", required = false) String[] roles,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            Set<Role> roleSet = new HashSet<>();
            for (String role : roles) {
                roleSet.add(userService.findRoleByName(role));
            }
            user.setRoles(roleSet);
            userService.editUser(id, user);
            return "redirect:/admin";
        }
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
