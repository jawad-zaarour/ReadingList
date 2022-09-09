package com.learning.readinglist.service;


import com.learning.readinglist.ServiceException;
import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.mapper.BookMapper;
import com.learning.readinglist.repo.BookRepository;
import com.learning.readinglist.util.EnBookType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public BookDTO saveBook(Book book) {
        try {
            return bookMapper.getBookDTO(bookRepository.save(book));
        } catch (ServiceException serviceException) {
            throw new ServiceException("Error in converting btw DTO and Entity" + serviceException);
        }
    }

    public BookDTO getBookDTOById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::getBookDTO).orElse(null);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElse(null);
    }

    public BookDTO getBookDTOByTitle(String title) {
        return bookRepository.findByTitle(title)
                .map(bookMapper::getBookDTO).orElse(null);
    }

    public Boolean deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            //throw new ServiceException("book with id " + id + " does not exists");
            return false;
        }
        bookRepository.deleteById(id);
        return true;
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
                existingBook.setType(book.getType());
                return bookMapper.getBookDTO(bookRepository.save(existingBook));
            } else {
                throw new ServiceException("Syntax Error In The Json Body");
            }
        }
        return null;
    }


    public List<BookDTO> getBooksDTO() {
        List<Book> lstBooks = bookRepository.findAll();
        if (CollectionUtils.isNotEmpty(lstBooks)) {
            return bookMapper.toBookDTOs(lstBooks);
        } else return Collections.emptyList();
    }

    public List<BookDTO> getBooksDTOByType(EnBookType type) {
        List<Book> lstBooks = bookRepository.getBooksByType(type);
        if (CollectionUtils.isNotEmpty(lstBooks)) {
            return bookMapper.toBookDTOs(lstBooks);
        } else return Collections.emptyList();
    }

    public Map<EnBookType, Long> getBooksStat() {
        return bookRepository.getBooksStat();
    }

    public List<BookDTO> getBooksDTOByAuthorName(String name) {

        //List<Book> lstBooks = bookRepository.findBooksByAuthorName(name);
        List<Book> lstBooks = bookRepository.getBooksByAuthorName(name);
        if (CollectionUtils.isNotEmpty(lstBooks)) {
            return bookMapper.toBookDTOs(lstBooks);
        } else return Collections.emptyList();

    }
}
