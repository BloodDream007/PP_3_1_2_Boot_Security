package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String userList(Model model) {
//        List<User> list = userService.allUsers();
        model.addAttribute("users", userService.allUsers());
        return "admin"; //?
    }

    @GetMapping("/new")
    public String saveUser(Model model) {
        model.addAttribute("user", new User());
        return "new_user";
    }

    @PostMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return ("redirect:/admin");
    }

    @GetMapping("{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "update_user";
    }

    @PostMapping("admin/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.update(user);
        return ("redirect:/admin");
    }
//        @PostMapping("admin/edit")
//        public String userUpdate(@ModelAttribute("user") User user) {
//            userService.update(user);
//            return "redirect:/admin";
//        }

//    @DeleteMapping("{id}/delete")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
