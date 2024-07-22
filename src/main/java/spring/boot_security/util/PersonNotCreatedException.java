package spring.boot_security.util;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String mgs) {
        super(mgs);
    }
}
