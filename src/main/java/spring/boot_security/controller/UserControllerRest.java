package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot_security.model.Person;

import spring.boot_security.security.SecurityUserService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserControllerRest {
private final SecurityUserService securityUserService;

    @Autowired
    public UserControllerRest(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @GetMapping
    public ResponseEntity<Optional<Person>> userInfo(Principal principal) {
        Optional<Person> person = securityUserService.findByUserName(principal.getName());
        if (person.isPresent()) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
        }
    }
}
