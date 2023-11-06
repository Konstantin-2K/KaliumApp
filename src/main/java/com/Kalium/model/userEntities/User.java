package com.Kalium.model.userEntities;

import com.Kalium.model.BaseEntity;
import com.Kalium.model.productEntities.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Size(min = 3, max = 20)
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "addedBy")
    private Set<Product> addedProducts;

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<Product> getAddedProducts() {
        return addedProducts;
    }

    public void setAddedProducts(Set<Product> addedProducts) {
        this.addedProducts = addedProducts;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }
}
