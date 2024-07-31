package spring.boot_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.boot_security.model.Person;
import spring.boot_security.repository.PersonRepository;

import java.util.Optional;

@Service
public class SecurityUserService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Autowired
    public SecurityUserService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findByUserName(String username) {
        return personRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);
        if (person.isEmpty()) throw new UsernameNotFoundException("User not found!");
        return new User(person.get().getUsername(), person.get().getPassword(), PersonDetails.mapRolesToAuthorities(person.get().getRoles()));
    }
}

