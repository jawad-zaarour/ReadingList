package com.learning.readinglist.service;

import com.learning.readinglist.ServiceException;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * TODO: all the methods should return DTO objects instead of the db entities
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        if (bookRepository.existsBookByIsbn(book.getIsbn())) {
            throw new ServiceException("ISBN " + book.getIsbn() + " taken");
        }
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {

        Optional<Book> result = bookRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ServiceException("book with id " + id + " does not exists");
        }
    }

    public Book getBookByTitle(String title) {

        Optional<Book> result = bookRepository.findByTitle(title);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ServiceException("book with title " + title + " does not exists");
        }
    }

    public String deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ServiceException("book with id " + id + " does not exists");
        }
        bookRepository.deleteById(id);
        return "Book removed !! " + id;
    }

    public Book updateBook(Book book) {
        if (book != null) {
            Book existingBook =
                    bookRepository.findById(book.getId()).orElse(null);
            if (existingBook != null) {
                existingBook.setTitle(book.getTitle());
                existingBook.setDescription(book.getDescription());
                existingBook.setIsbn(book.getIsbn());
                existingBook.setAuthor(book.getAuthor());
                return bookRepository.save(existingBook);
            } else {
                return null;
            }
        }
        return null;
    }


}
