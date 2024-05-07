package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.boot_security.model.Person;
import spring.boot_security.service.PersonDetailsService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class UserController {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public UserController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @GetMapping(value = "/user")
    public String showAllUser(Model model, Principal principal) {
        Optional<Person> person = personDetailsService.findByUserName(principal.getName());
        model.addAttribute("person", person.get());
        return "user";
    }
}

