package com.learning.readinglist.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//TODO please check the logic in this entity
@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    },
            targetEntity = Book.class)
    @JoinTable(name = "users_books",
            joinColumns = @JoinColumn(name = "USER_ID",
                    nullable = false,
                    updatable = false),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
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

}
