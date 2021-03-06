package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    @NotBlank(message = "Поле \"Имя пользователя\" не должно быть пустым")
    @Size(min = 2, message = "Имя пользователя должено быть не меньше 4 знаков")
    private String username;
    @Column(name = "name")
    @NotNull(message = "Поле \"Имя\" не должно быть пустым")
    @Size(min = 2, max = 45, message = "Имя должно быть не менее 2 символов и не должно превышать 45 символов")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "Поле \"Фамилия\" не должно быть пустым")
    @Size(min = 2, max = 45, message = "Фамилия должна быть не менее 2 символов и не должна превышать 45 символов")
    private String lastName;
    @Column(name = "email")
    @NotEmpty(message = "Поле email не должно быть пустым")
    @Email(message = "Некорректный email")
    private String email;
    @Column(name = "password")
    @Size(min = 2, message = "Пароль должен быть не меньше 5 знаков")
    @NotBlank
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name=" + name +
                ", username=" + username +
                ", lastName=" + lastName +
                ", email=" + email +
                ", roles=" + roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
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
