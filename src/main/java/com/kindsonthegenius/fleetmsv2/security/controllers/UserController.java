package com.kindsonthegenius.fleetmsv2.security.controllers;

import com.kindsonthegenius.fleetmsv2.hr.models.Employee;
import com.kindsonthegenius.fleetmsv2.security.models.User;
import com.kindsonthegenius.fleetmsv2.security.services.RoleService;
import com.kindsonthegenius.fleetmsv2.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

//    @GetMapping("/security/users")
//    public String getAll(Model model) {
//        model.addAttribute("users", userService.findAll());
//        return "/security/users";
//    }
    //Get All Users
    @GetMapping("/security/users")
    public String findAll(Model model, String keyword){
//        addModelAttributes(model);
        List<User> users;
        try {
            if (keyword == null){
                users = userService.findAll();
            } else {
                users = userService.findByKeyword(keyword);
            }
            model.addAttribute("users",users);
            System.out.println(users);
        } catch (Exception e){
            // Handle the exception, for example by logging it and setting an empty list or error message
            System.err.println("An error occurred while fetching employees:" + e.getMessage());
            users = new ArrayList<>(); // or handle
            model.addAttribute("users", users);
            model.addAttribute("errorMessage", "An error occurred while fetching employees. Please try again later");
        }

        return "/security/users";
    }

    @GetMapping("/security/user/{op}/{id}")
    public String editUser(@PathVariable Integer id, @PathVariable String op, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
        return "/security/user" + op; //returns employeeEdit or employeeDetails
    }

    @PostMapping("/users/addNew")
    public RedirectView addNew(User user, RedirectAttributes redir) {
        userService.save(user);

        RedirectView redirectView = new RedirectView("/login", true);
        redir.addFlashAttribute("message", "You have successfully registered a new user!");
        return redirectView;
    }

}
