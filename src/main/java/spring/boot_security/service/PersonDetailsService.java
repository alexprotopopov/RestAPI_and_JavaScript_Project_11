package spring.boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.boot_security.model.Person;
import spring.boot_security.repository.PersonRepository;
import spring.boot_security.security.PersonDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService, UserService {

    private BCryptPasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Transactional(readOnly = true)
    @Override
    public List<Person> listUsers() {
        return personRepository.findAll();
    }

    @Transactional
    @Override
    public Person getUser(long id) {
        return personRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveUser(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }
}


