package spring.boot_security.service;

import org.springframework.http.ResponseEntity;
import spring.boot_security.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> listUsers();

    void saveUser(Person person);

    Person getUser(long id);

    void deleteUser(long id);

    Optional<Person> findByUserName(String email);

    Person updatePerson(Person updatePerson, long id);
}
