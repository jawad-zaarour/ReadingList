package com.learning.readinglist.repo;

import com.learning.readinglist.entity.Book;

import java.util.List;

public interface BookRepositoryCustom {

    public List<Book> getAllBooksByQuerDsl(String author);
}
