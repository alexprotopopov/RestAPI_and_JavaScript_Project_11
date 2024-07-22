package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot_security.model.Person;
import spring.boot_security.service.PersonService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserControllerRest {

    private final PersonService personService;

    @Autowired
    public UserControllerRest(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<Optional<Person>> userInfo(Principal principal) {
        return new ResponseEntity<>(personService.findByUserName(principal.getName()), HttpStatus.OK);
    }
}
