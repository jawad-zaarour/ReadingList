package com.learning.readinglist.repo;

import com.learning.readinglist.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);

    List<Book> findByAuthor(String author);

    boolean existsBookByIsbn(String isbn);

    //returns all books related to user with input userId
    List<Book> findBooksByUsersId(Long userId);

}
