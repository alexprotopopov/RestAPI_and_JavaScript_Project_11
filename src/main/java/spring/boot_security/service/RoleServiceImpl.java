package spring.boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.boot_security.model.Person;
import spring.boot_security.model.Role;
import spring.boot_security.repository.PersonRepository;
import spring.boot_security.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PersonRepository personRepository) {
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<Role> listRole() {
        return roleRepository.findAll();

    }
    @Transactional
    public List<Role> getRole(long id) {
        Optional<Person> person = personRepository.findById(id);
        return (List<Role>) person.get().getRoles();
    }
}
