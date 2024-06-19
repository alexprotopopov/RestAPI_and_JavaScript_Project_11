package spring.boot_security.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.boot_security.model.Person;
import spring.boot_security.model.Role;

import java.util.Collection;
import java.util.stream.Collectors;


@Data
public class PersonDetails implements UserDetails {

    private final Person person;

    //Нужно для получения данных аутентифицированного пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapRolesToAuthorities(person.getRoles());
    }

    public static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> role) {
        return role.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


