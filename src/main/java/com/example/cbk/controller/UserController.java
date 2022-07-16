package com.example.cbk.controller;


import com.example.cbk.entity.Role;
import com.example.cbk.entity.User;
import com.example.cbk.service.RoleService;
import com.example.cbk.service.UserServiceImpl;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String printUsers(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "admin/user/users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "admin/user/user";
    }

    @GetMapping("/add")
    public String addUserFrom(Model model) {
        model.addAttribute("user", new User());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "admin/user/add";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") @Valid User user, @ModelAttribute Role role, @NotNull BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "admin/user/add";
        }
        user.setRoles(Set.of(role));
        userService.createUser(user);
        return "redirect:/user/users";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/user/users";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/user/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        if(bindingResult.hasErrors()){
            return "admin/user/edit";
        }
        userService.update(user, id);
        return "redirect:/user/users";
    }

}
