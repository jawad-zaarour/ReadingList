package com.learning.readinglist.repo;

import com.learning.readinglist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
    //returns all users related to book with input bookId
    //List<User> findUsersByBooksId (Long bookId);
}
