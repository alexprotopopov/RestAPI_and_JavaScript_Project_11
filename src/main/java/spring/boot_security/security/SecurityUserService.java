package spring.boot_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.boot_security.model.Person;
import spring.boot_security.repository.PersonRepository;

import java.util.Optional;

@Service
public class SecurityUserService {
    private final PersonRepository personRepository;

    @Autowired
    public SecurityUserService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findByUserName(String username) {
        return personRepository.findByUsername(username);
    }
}

