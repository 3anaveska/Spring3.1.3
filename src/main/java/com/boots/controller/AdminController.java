package com.boots.controller;

import com.boots.model.User;
import com.boots.service.RoleService;
import com.boots.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;


    @GetMapping
    public String allUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", userService.getUserByEmail(user.getUsername()));
        model.addAttribute("userList", userService.listUsers());
        model.addAttribute("roleList", roleService.getAllRoles());
        return "admin";
    }


    @PostMapping
    public String createNewUser(@ModelAttribute("user") User user,
                                @RequestParam(value = "nameRoles") String[] roles) {
        userService.addUser(user, roles);
        return "redirect:/admin/";
    }


    @GetMapping("{id}/edit")
    public String editUserForm(@ModelAttribute("user") User user,
                               ModelMap model,
                               @PathVariable("id") long id,
                               @RequestParam(value = "editRoles") String[] roles) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.getUserById(id, user, roles));
        return "admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") long id,
                         @RequestParam(value = "editRoles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.updateUser(user, roles);
        return "redirect:/admin/";
    }


    @GetMapping("/{id}/remove")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin/";
    }
}