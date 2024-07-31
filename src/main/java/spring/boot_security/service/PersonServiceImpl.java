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
public class PersonServiceImpl  implements PersonService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> listUsers() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
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

    @Override
    @Transactional
    public void updatePerson(Person updatePerson, long id) {
        Person personBD = personRepository.findById(id).get();
        personBD.setFirstName(updatePerson.getFirstName());
        personBD.setLastName(updatePerson.getLastName());
        personBD.setAge(updatePerson.getAge());
        personBD.setUsername(updatePerson.getUsername());
        personBD.setRoles(updatePerson.getRoles());
        if (personBD.getPassword().equals(updatePerson.getPassword())) {
            personRepository.save(personBD);
        } else {
            personBD.setPassword(passwordEncoder.encode(updatePerson.getPassword()));
            personRepository.save(personBD);
        }
    }
}


