package com.learning.readinglist.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//TODO please check the logic in this entity
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(targetEntity = Book.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Book> books = new HashSet<>();

    @Column(unique = true)
    private String userName;
    private String password;
    @Column(unique = true)
    private String email;
    private boolean active;
    private String roles;

    public User(Long id, Set<Book> books, String userName, String password,
                String email, boolean active, String roles) {
        this.id = id;
        this.books = books;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }


}
