package com.Kalium.model.userEntities;

import com.Kalium.model.BaseEntity;
import com.Kalium.model.productEntities.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.*;

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

    @ElementCollection
    @CollectionTable(name = "user_shopping_cart", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> shoppingCart = new HashMap<>();

    public User() {
    }

    public User(String username, String password, String email, List<UserRole> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public User(String username, String email, List<UserRole> roles) {
        super();
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

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

    public Map<Product, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(Map<Product, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
