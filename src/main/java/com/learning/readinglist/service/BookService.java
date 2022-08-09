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

    //TODO: Remove @Autowired annotation to use injection by constructor or keep it and remove injection by constructor
    @Autowired
    private BookRepository bookRepository;

    //TODO: should create a new component annotated with @Mapper and inject it here instead of ModelMapper
    @Autowired
    private ModelMapper modelMapper;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //TODO: Remove this constructor: unit test SHOULD NOT create an instance of BookService but should inject it via @Autowired or @Mock or etc..
    public BookService() {

    }

    public BookDTO saveBook(Book book) {
        if (bookRepository.existsBookByIsbn(book.getIsbn())) {
            throw new ServiceException("ISBN " + book.getIsbn() + " taken");
        }
        // convert entity to DTO
        //TODO: should try/catch the mapper operation and ServiceException in case of catch
        return modelMapper.map(bookRepository.save(book), BookDTO.class);
    }

    public BookDTO getBookById(Long id) {
        Optional<Book> result = bookRepository.findById(id);
        if (result.isPresent()) {
            return modelMapper.map(result.get(), BookDTO.class);
        } else {
            throw new ServiceException("book with id " + id + " does not exists");
        }
    }

    public BookDTO getBookByTitle(String title) {
        Optional<Book> result = bookRepository.findByTitle(title);
        if (result.isPresent()) {
            return modelMapper.map(result.get(), BookDTO.class);
        } else {
            throw new ServiceException("book with title " + title + " does not exists");
        }
    }

    //TODO: this method should return boolean (true/false) or int (number of deleted books)
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
                return modelMapper.map(bookRepository.save(existingBook), BookDTO.class);
            } else {
                //TODO: should throw an exception
                return null;
            }
        }
        return null;
    }


    public List<BookDTO> getBooks() {
        //TODO: list should be named lstBooks for example or books not book
        List<Book> book = bookRepository.findAll();
        if (book != null) { //TODO: include apache commons dependency in maven
            /**
             * <dependency>
             *     <groupId>org.apache.commons</groupId>
             *     <artifactId>commons-collections4</artifactId>
             *     <version>4.4</version>
             * </dependency>
             *
             * and use CollectionUtils.isNotEmpty(lstBooks)
             */
            //TODO: use the new mapper component (BookMapper) that you will create
            return ObjectMapperUtils.mapAll(book, BookDTO.class);
        }
        else return null; //TODO: return an empty list not null
    }
}
