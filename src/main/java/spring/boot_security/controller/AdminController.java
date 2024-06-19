package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.boot_security.model.Person;
import spring.boot_security.service.RoleService;
import spring.boot_security.service.PersonService;

import java.security.Principal;
import java.util.Optional;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final RoleService roleService;
    private final PersonService personService;


    @Autowired
    public AdminController(PersonService personService, RoleService roleService) {
        this.personService = personService;
        this.roleService = roleService;
    }

    @GetMapping
    public String admin(Model model, Principal principal) {
        Optional<Person> person = personService.findByUserName(principal.getName());
        model.addAttribute("people", personService.listUsers());
        model.addAttribute("currentPerson", person.get());
        model.addAttribute("roles", roleService.listRole());
        model.addAttribute("person", new Person());
        return "admin";
    }


    @GetMapping("/edit{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("person", personService.getUser(id));
        model.addAttribute("AllRoles", roleService.listRole());
        return "redirect:/admin";
    }

    @PatchMapping("/update{id}")
    public String updatePerson(@ModelAttribute("person") @Validated Person person, @PathVariable("id") long id) {
        personService.updatePerson(person, id);
        return "redirect:/admin";

    }

    @PostMapping("/save_person")
    public String saveUser(@ModelAttribute("person") @Validated Person person, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
//        //Для учета требований в аннотации к полям
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("person", person);
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//            return "redirect:/admin";
//        }
//        personValidator.validate(person, bindingResult);
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("person", person);
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//            return "redirect:/admin";
//        }
        Optional<Person> personBD = personService.findByUserName(person.getUsername());
        if (!personBD.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", "Пользователь с таким username существует");
            return "redirect:/admin";
        } else {
            personService.saveUser(person);
            return "redirect:/admin";
        }
    }

    @DeleteMapping(value = "/delete{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        personService.deleteUser(id);
        return "redirect:/admin";
    }
}
