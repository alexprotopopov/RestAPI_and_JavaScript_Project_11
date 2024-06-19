package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.boot_security.model.Person;
import spring.boot_security.service.PersonService;
import spring.boot_security.service.RoleService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class UserController {
    private final RoleService roleService;
    private final PersonService personService;


    @Autowired
    public UserController(PersonService personService, RoleService roleService) {
        this.personService = personService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/user")
    public String user(Model model, Principal principal) {
        Optional<Person> person = personService.findByUserName(principal.getName());
        model.addAttribute("people", personService.listUsers());
        model.addAttribute("currentPerson", person.get());
        model.addAttribute("roles", roleService.listRole());
        return "user";
    }
}

