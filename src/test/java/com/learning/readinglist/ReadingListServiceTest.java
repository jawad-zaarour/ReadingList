package com.learning.readinglist;

import com.learning.exception.ReadingListServiceException;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadingListServiceTest {
    private BookService bookServiceTest;
    Book book;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookServiceTest = new BookService(bookRepository);
        book = new Book(1L, "1111",
                "Atoms", "Einstein", "Advanced");
    }


    @Test
    void saveBookTest() {
        //when
        bookServiceTest.saveBook(book);
        //then
        ArgumentCaptor<Book> bookArgumentCaptor =
                ArgumentCaptor.forClass(Book.class);
        verify(bookRepository)
                .save(bookArgumentCaptor.capture());
        Book captureBook = bookArgumentCaptor.getValue();
        assertThat(captureBook).isEqualTo(book);
    }

    @Test
    void saveBookExistedTest() {

        given(bookRepository.existsBookByIsbn(book.getIsbn())).willReturn(true);

        // Writing Assertions for Exceptions
        // Verify that the method "readingListServiceTest.saveBook(book)"
        // under test of already book exciting throws the "ReadingListServiceException"
        // that contains the message: "ISBN " + book.getIsbn() + " taken"
        assertThatThrownBy(() -> bookServiceTest.saveBook(book))
                .isInstanceOf(ReadingListServiceException.class)
                .hasMessageContaining("ISBN " + book.getIsbn() + " taken");

        // Verify that nothing has been saved in Database
        verify(bookRepository, never()).save(any());

    }

    @Test
    void getBooksTest() {
        //when
        bookServiceTest.getBooks();
        //then
        verify(bookRepository).findAll();
    }

    @Test
    void getBookByIdTest() throws Exception {


        when(bookRepository.existsById(book.getId()))
                .thenReturn(true);
        when(bookRepository.findById(book.getId()))
                .thenReturn(Optional.of(book));

        Book b = bookServiceTest.getBookById(1L);

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
        Book updatedBook = bookServiceTest.updateBook(book);
        // then - verify the output
        assertNotNull(updatedBook);
        assertThat(updatedBook.getDescription()).isEqualTo("new dec");
        assertThat(updatedBook.getAuthor()).isEqualTo("Ram");
    }
}