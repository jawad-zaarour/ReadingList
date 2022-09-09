package com.learning.readinglist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.readinglist.util.EnBookType;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "books")
    private Set<User> users = new HashSet<>();

    @NonNull
    @Column(unique = true)
    private String isbn;
    @NonNull
    private String title;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @NonNull
    private EnBookType type;
    private String description;

    public Book(Long id, Set<User> users, @NonNull String isbn, @NonNull String title, @NonNull Author author, @NonNull EnBookType type, String description) {
        this.id = id;
        this.users = users;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.type = type;
        this.description = description;
    }

    public Book() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @NonNull
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NonNull String isbn) {
        this.isbn = isbn;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull Author author) {
        this.author = author;
    }

    @NonNull
    public EnBookType getType() {
        return type;
    }

    public void setType(@NonNull EnBookType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
