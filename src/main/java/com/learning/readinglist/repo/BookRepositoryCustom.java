package com.learning.readinglist.repo;

import com.learning.readinglist.entity.Book;
import com.learning.readinglist.util.EnBookType;

import java.util.List;
import java.util.Map;

public interface BookRepositoryCustom {

    List<Book> getBooksByType(EnBookType type);

    Map<EnBookType, Long> getBooksStat();

    List<Book> findBooksByAuthorName(String authorName);
}
