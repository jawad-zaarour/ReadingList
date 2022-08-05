package com.learning.readinglist.service;

import com.learning.readinglist.ServiceException;
import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.repo.BookRepository;
import com.learning.readinglist.util.ObjectMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * TODO: all the methods should return DTO objects instead of the db entities
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookService() {

    }

    public BookDTO saveBook(Book book) {

        if (bookRepository.existsBookByIsbn(book.getIsbn())) {
            throw new ServiceException("ISBN " + book.getIsbn() + " taken");
        }
        // convert entity to DTO
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    public BookDTO getBookById(Long id) {

        Optional<Book> result = bookRepository.findById(id);
        if (result.isPresent()) {
            BookDTO bookResponse = modelMapper.map(result.get(), BookDTO.class);
            return bookResponse;
        } else {
            throw new ServiceException("book with id " + id + " does not exists");
        }
    }

    public BookDTO getBookByTitle(String title) {

        Optional<Book> result = bookRepository.findByTitle(title);
        if (result.isPresent()) {
            BookDTO bookResponse = modelMapper.map(result.get(), BookDTO.class);
            return bookResponse;
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

    public BookDTO updateBook(Book book) {
        if (book != null) {
            Book existingBook =
                    bookRepository.findById(book.getId()).orElse(null);
            if (existingBook != null) {
                existingBook.setTitle(book.getTitle());
                existingBook.setDescription(book.getDescription());
                existingBook.setIsbn(book.getIsbn());
                existingBook.setAuthor(book.getAuthor());
                BookDTO bookResponse = modelMapper.map(bookRepository.save(existingBook), BookDTO.class);
                return bookResponse;
            } else {
                return null;
            }
        }
        return null;
    }


    public List<BookDTO> getBooks() {
        List<Book> book = bookRepository.findAll();
        if (book != null) {
            return ObjectMapperUtils.mapAll(book, BookDTO.class);
        }
        else return null;
    }
}
