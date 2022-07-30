package com.learning.readinglist.dto;

import com.learning.readinglist.entity.Book;

public class OldSchoolBookMapper {

    /**
     * Old school way: bide2iye
     * @param book
     * @return
     */
    public BookDTO getBookDTO(Book book) {
        return new BookDTO(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getDescription());
    }



}
