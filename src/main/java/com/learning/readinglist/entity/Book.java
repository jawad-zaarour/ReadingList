package com.learning.readinglist.entity;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//TODO please check the logic in this entity
@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST})
    @JoinTable(name = "users_books",
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "USER_ID",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<User> users = new HashSet<>();

    @NonNull
    @Column(unique = true)
    private String isbn;
    @NonNull
    private String title;
    @NonNull
    private String author;
    private String description;

    public Book(Long id, String isbn, String title, String author, String description) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public Book() {

    }

}
