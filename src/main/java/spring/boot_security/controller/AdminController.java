package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.boot_security.model.Person;
import spring.boot_security.model.Role;
import spring.boot_security.service.UserService;
import spring.boot_security.repository.RoleRepository;

import java.util.List;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final RoleRepository roleRepository;
    private final UserService userService;


    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("person", userService.listUsers());
        return "admin/admin";
    }

    @GetMapping(value = "/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("person", new Person());
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/user-info";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("person") Person person) {
        userService.saveUser(person);
        return "redirect:/admin";
    }

    @GetMapping(value = "/updateUser")
    public String updateUser(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        Person person = userService.getUser(id);
        model.addAttribute("person", person);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/user-info";
    }

    @GetMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
