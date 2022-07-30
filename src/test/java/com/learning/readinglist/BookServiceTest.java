package com.learning.readinglist;

import com.learning.readinglist.entity.Book;
import com.learning.readinglist.repo.BookRepository;
import com.learning.readinglist.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    //@Autowired
    private BookService bookService;

    Book book;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
        book = new Book(1L, "1111",
                "Atoms", "Einstein", "Advanced");
    }

    /**
     * This method is considered as an integration test because it is testing the behavior of the db
     */
    @Test
    void saveBookTest() {
        long bookId = book.getId();
        //when
        bookService.saveBook(book);
        //then
        ArgumentCaptor<Book> bookArgumentCaptor =
                ArgumentCaptor.forClass(Book.class);
        verify(bookRepository)
                .save(bookArgumentCaptor.capture());
        Book captureBook = bookArgumentCaptor.getValue();
        assertThat(captureBook).isEqualTo(book);

        assertNotNull(bookService.getBookById(bookId));
    }

    @Test
    void saveBookExistedTest() {
        given(bookRepository.existsBookByIsbn(book.getIsbn())).willReturn(true);

        // Writing Assertions for Exceptions
        // Verify that the method "readingListServiceTest.saveBook(book)"
        // under test of already book exciting throws the "ServiceException"
        // that contains the message: "ISBN " + book.getIsbn() + " taken"
        assertThatThrownBy(() -> bookService.saveBook(book))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("ISBN " + book.getIsbn() + " taken");

        // Verify that nothing has been saved in Database
        verify(bookRepository, never()).save(any());
    }

    @Test
    void getBooksTest() {
        //when
        bookService.getBooks();
        //then
        verify(bookRepository).findAll();
    }

    @Test
    void getBookByIdTest() throws Exception {


        when(bookRepository.existsById(book.getId()))
                .thenReturn(true);
        when(bookRepository.findById(book.getId()))
                .thenReturn(Optional.of(book));

        Book b = bookService.getBookById(1L);

       // assertThat("Jawad").isEqualTo(b.getReader());
    }




    @Test
    void updateBookTest() {

        // given - precondition or setup
        Optional<Book> optionalBook = Optional.of(book);
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(optionalBook);
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        //change description and author
        book.setDescription("new dec");
        book.setAuthor("Ram");
        // when -  action or the behaviour that we are going test
        Book updatedBook = bookService.updateBook(book);
        // then - verify the output
        assertNotNull(updatedBook);
        assertThat(updatedBook.getDescription()).isEqualTo("new dec");
        assertThat(updatedBook.getAuthor()).isEqualTo("Ram");
    }
}