package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.boot_security.model.Person;
import spring.boot_security.model.Role;
import spring.boot_security.service.PersonService;
import spring.boot_security.service.RoleService;
import spring.boot_security.util.PersonErrorResponse;
import spring.boot_security.util.PersonNotCreatedException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminControllerRest {
    private final RoleService roleService;
    private final PersonService personService;

    @Autowired
    public AdminControllerRest(RoleService roleService, PersonService personService) {
        this.roleService = roleService;
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> userInfo() {
        return ResponseEntity.ok(personService.listUsers());
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<Person> getUserId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(personService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updatePerson(@RequestBody @Valid Person person,
                                                   @PathVariable("id") long id) {
        personService.updatePerson(person, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/save_person")
    public ResponseEntity<HttpStatus> addUser(@RequestBody Person person) {
        personService.saveUser(person);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    @DeleteMapping(value = "delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        personService.getUser(id);
        personService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.listRole();
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // NOT_FOUND - 404
    }
}
