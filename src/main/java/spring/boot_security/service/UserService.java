package spring.boot_security.service;



import spring.boot_security.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    List<User> listUsers();

    void saveUser(User user);

    User getUser(long id);

    void deleteUser(long id);
}
