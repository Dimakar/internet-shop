package ru.dimakar.internetshop.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.dimakar.internetshop.model.UserModel;

import java.util.List;

public class User implements UserDetails {
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private List<GrantedAuthority> authorities;

    public User(UserModel userModel) {
        this.username = userModel.getEmail();
        this.password = userModel.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority(userModel.getRole().getName()));
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
