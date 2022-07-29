package com.learning.readinglist.service;

import com.learning.exception.ServiceException;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.repo.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        if (bookRepository.existsBookByIsbn(book.getIsbn())) {
            throw new ServiceException("ISBN " + book.getIsbn() + " taken");
        }
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ServiceException("book with id " + id + " does not exists");
        }
        return bookRepository.findById(id).orElse(null);
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
        existingBook.setTitle(book.getTitle());
        existingBook.setDescription(book.getDescription());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setAuthor(book.getAuthor());
        return bookRepository.save(existingBook);
    }


}
