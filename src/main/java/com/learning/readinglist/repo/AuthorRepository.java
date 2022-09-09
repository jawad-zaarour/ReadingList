package com.learning.readinglist.repo;

import com.learning.readinglist.entity.Author;
import com.learning.readinglist.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long>,AuthorRepositoryCustom {
    Optional<Author> findByName(String name);
}
