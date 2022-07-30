package com.learning.readinglist.service;

import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.dto.BookMapper;
import com.learning.readinglist.ServiceException;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.repo.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO: all the methods should return DTO objects instead of the db entities
 */
@AllArgsConstructor
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public Book saveBook(Book book) {
        if (bookRepository.existsBookByIsbn(book.getIsbn())) {
            throw new ServiceException("ISBN " + book.getIsbn() + " taken");
        }
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    // this should not throw an exception, it should return the existing object or null
    public BookDTO getBookById(Long id) {
        /*if (!bookRepository.existsById(id)) {
            throw new ServiceException("book with id " + id + " does not exists");
        }*/
        return bookRepository.findById(id)
                .map(bookMapper::getBookDTO).orElse(null);
    }

    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public String deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ServiceException("book with id " + id + " does not exists");
        }
        bookRepository.deleteById(id);
        return "Book removed !! " + id;
    }

    public Book updateBook(Book book) {
        Book existingBook =
                bookRepository.findById(book.getId()).orElse(null);

        // Book existingBook  = getBookById(book.getId());

        //TODO: you should check the book is null or not
        //TODO: you should use the above method getBookById()

        if(existingBook != null) {
            existingBook.setTitle(book.getTitle());
            existingBook.setDescription(book.getDescription());
            existingBook.setIsbn(book.getIsbn());
            existingBook.setAuthor(book.getAuthor());
            return bookRepository.save(existingBook);
        } else {
            return null;
        }
    }


}
