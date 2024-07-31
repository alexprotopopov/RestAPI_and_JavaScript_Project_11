package spring.boot_security.service;

import spring.boot_security.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> listUsers();

    void saveUser(Person person);

    Person getUser(long id);

    void deleteUser(long id);

    //Optional<Person> findByUserName(String email);

    void updatePerson(Person updatePerson, long id);
}
