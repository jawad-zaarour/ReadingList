package com.learning.readinglist;

import com.learning.readinglist.mapper.BookMapper;
import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.repo.BookRepository;
import com.learning.readinglist.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    private BookDTO bookDTO = new BookDTO();
    private Book book = new Book();

    @BeforeEach
    void setUp() {

        book.setAuthor("Einstein");
        book.setDescription("Advanced");
        book.setId(1L);
        book.setIsbn("1111");
        book.setTitle("Atoms");

        bookDTO.setAuthor("Einstein");
        bookDTO.setDescription("Advanced");
        bookDTO.setId(1L);
        bookDTO.setIsbn("1111");
        bookDTO.setTitle("Atoms");

    }

    /**
     * This method is considered as an integration test because it is testing the behavior of the db
     */
    @Test
    void saveBookTest() {
        when(bookMapper.getBookDTO(any())).thenReturn(bookDTO);
        long bookId = book.getId();
        //when
        given(bookRepository.save(book)).willReturn(book);
        bookService.saveBook(book);
        //then
        ArgumentCaptor<Book> bookArgumentCaptor =
                ArgumentCaptor.forClass(Book.class);
        verify(bookRepository)
                .save(bookArgumentCaptor.capture());
        Book captureBook = bookArgumentCaptor.getValue();
        assertThat(captureBook).isEqualTo(book);

        given(bookRepository.findById(bookId)).willReturn(Optional.ofNullable(book));
        assertNotNull(bookService.getBookById(bookId));
    }


//    @Test
//    void saveBookExistedBookTest() {
//        given(bookRepository.existsBookByIsbn(book.getIsbn())).willReturn(true);
//        assertThatThrownBy(() -> bookService.saveBook(book))
//                .isInstanceOf(ServiceException.class)
//                .hasMessageContaining("ISBN " + book.getIsbn() + " taken");
//
//        // Verify that nothing has been saved in Database
//        verify(bookRepository, never()).save(any());
//    }


    @Test
    void getBookByIdTest() throws Exception {
        when(bookMapper.getBookDTO(any())).thenReturn(bookDTO);
        when(bookRepository.findById(book.getId()))
                .thenReturn(Optional.of(book));

        BookDTO book = bookService.getBookById(1L);

        assertThat("Einstein").isEqualTo(book.getAuthor());
    }

    //Todo not working (results variable not import the updated dto book)
    @Test
    void updateBookTest() {
        when(bookMapper.getBookDTO(any())).thenReturn(bookDTO);
        given(bookRepository.findById(anyLong())).willReturn(Optional.of(book));
        book.setDescription("new dec");
        book.setAuthor("Ram");
        given(bookRepository.save(book)).willReturn(book);

        BookDTO results = bookService.updateBook(book);

        assertNotNull(results);
        assertThat(results.getTitle()).isEqualTo("Atoms");
        assertThat(results.getAuthor()).isEqualTo("Ram");
        assertThat(results.getDescription()).isEqualTo("new dec");
    }
}